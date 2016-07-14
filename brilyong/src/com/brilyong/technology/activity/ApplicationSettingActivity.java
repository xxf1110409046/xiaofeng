package com.brilyong.technology.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brilyong.technology.R;
import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.CheckUpdateResult;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.service.GetMessageService;
import com.brilyong.technology.utils.DialogUtils;

/**
 * 应用设置
 * 
 * @author Administrator
 * 
 */
public class ApplicationSettingActivity extends BaseUIActivityUtil implements
		OnClickListener {

	private RelativeLayout action_yijian;
	private RelativeLayout action_baojin;
	private RelativeLayout check_update;
	private RelativeLayout azxkxy;
	private TextView account_number;
	private TextView version_name;
	private SharedPreferences sp;
	private CheckBox cb_guiji;
	private Boolean home;
	private boolean orbit;
	private Dialog dialog;
	private CheckUpdateResult aCheckUpdateResult;

	private ProgressDialog mpDialog;
	private int progress;
	private int lastRate = 0;
	private boolean canceled;
	private String packge_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_setting_activity);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		orbit = sp.getBoolean("orbit", false);
		initID();
		cb_guiji.setChecked(orbit);
		String loginName = sp.getString("LoginName", null);
		account_number.setText(loginName);
		home = sp.getBoolean("HOME", false);
	}

	private void initID() {
		action_yijian = (RelativeLayout) findViewById(R.id.action_yijian);
		check_update = (RelativeLayout) findViewById(R.id.check_update);
		account_number = (TextView) findViewById(R.id.account_number);
		version_name = (TextView) findViewById(R.id.version_name);
		cb_guiji = (CheckBox) findViewById(R.id.cb_guiji);
		check_update.setOnClickListener(this);
		action_yijian.setOnClickListener(this);
		cb_guiji.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Editor ed = sp.edit();
				if (isChecked) {
					ed.putBoolean("orbit", isChecked);
				} else {
					ed.putBoolean("orbit", isChecked);
				}
				ed.commit();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_yijian:
			Intent yijianIntent = new Intent(this, FeedBackActivity.class);
			startActivity(yijianIntent);
			break;
		case R.id.check_update:
			Log.v("TAG", "我来过了...." + Acount.getaCheckUpdateResult().isUpdate());
			if (Acount.getaCheckUpdateResult().isUpdate() == false) {
				Toast.makeText(this, getString(R.string.present_is_newest_verssion), Toast.LENGTH_SHORT).show();
				return;
			}
			Log.v("TAG", "我来过了");
			dialog = DialogUtils.getDialog(this, getString(R.string.is_checking));
			dialog.show();
			CheckTask a = new CheckTask();
			a.execute();
			break;
		default:
			break;
		}

	}

	// 检查更新
	private class CheckTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				PackageManager pcg = getPackageManager();
				PackageInfo info = pcg.getPackageInfo(
						ApplicationSettingActivity.this.getPackageName(), 0);
				packge_name = info.packageName;
				aCheckUpdateResult = HttpHelperUtils.CheckUpdate(packge_name,
						MyApplication.versionName);
			} catch (ConnectException e) {
				Message msg = Message.obtain();
				msg.what = 2;
				mHandler.sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Acount.setaCheckUpdateResult(aCheckUpdateResult);
			Message msg = Message.obtain();
			msg.what = 0;
			mHandler.sendMessage(msg);
			dialog.dismiss();
			super.onPostExecute(result);
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if (aCheckUpdateResult.isUpdate()) {
					showUpdateDialog();
				} else {
					Toast.makeText(getApplicationContext(), getString(R.string.is_newest_verssion),
							Toast.LENGTH_SHORT).show();
				}
				break;
			case 1:
				installApk();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), getString(R.string.network_unusual), 0).show();
				break;
			}

		}
	};

	private void showUpdateDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(getString(R.string.update_soft_versiion));
		builder.setMessage(getString(R.string.new_verssion_to_update));
		builder.setPositiveButton(getString(R.string.loading_and_update),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						canceled = false;
						showDownloadDialog();
					}

				});
		builder.setNegativeButton(getString(R.string.later_to_talk),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.create().show();

	};

	private void showDownloadDialog() {
		mpDialog = new ProgressDialog(this);
		mpDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置风格为圆形进度条
		mpDialog.setTitle(getString(R.string.update_soft_versiion));// 设置标题
		mpDialog.setMessage(getString(R.string.loading));
		mpDialog.setIndeterminate(false);// 设置进度条是否为不明确
		mpDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
		mpDialog.setButton(getString(R.string.cancel_loading), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				canceled = true;
			}
		});
		mpDialog.show();
		downloadApk();
	}

	// 启动下载apk
	private void downloadApk() {

		Thread downLoadThread = new Thread(downLoadApkRunnable);
		downLoadThread.start();

	}

	// 下载apk
	private Runnable downLoadApkRunnable = new Runnable() {

		private FileOutputStream fos;
		private InputStream is;
		private File apkFile;

		@Override
		public void run() {
			try {
				String ur = Acount.getaCheckUpdateResult().getUrl();
				URL url = new URL(ur);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(40000);
				conn.setReadTimeout(40000);
				conn.connect();
				int length = conn.getContentLength();
				is = conn.getInputStream();
				File file = new File(MyApplication.savePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				apkFile = new File(MyApplication.savePath
						+ MyApplication.downSaveFileName);
				fos = new FileOutputStream(apkFile);
				int count = 0;
				byte[] buf = new byte[1024];
				do {
					int numRead = is.read(buf);
					count += numRead;
					progress = (int) (((float) count / length) * 100);
					if (progress >= lastRate + 1) {
						lastRate = progress;
						if (mpDialog != null) {
							mpDialog.setProgress(progress);
						}
					}
					if (numRead <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(1);
						// 下载完了，cancelled也要设置
						canceled = true;
						break;
					}
					fos.write(buf, 0, numRead);
				} // do 语句块结束
				while (!canceled);// 点击取消就停止下载
			} catch (ConnectException e) {
				mHandler.sendEmptyMessage(2);
				// TODO: handle exception
			} catch (MalformedURLException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				// apkFile.delete();
				progress = 0;
				lastRate = 0;
				try {
					fos.close();
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mpDialog.dismiss();
			}

		}

	};

	/*
	 * 安装apk
	 */
	private void installApk() {
		File apkfile = new File(MyApplication.savePath
				+ MyApplication.downSaveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		startActivity(i);
	}

	public void back(View v) {
		finish();
	}

	// 退出登录
	public void Quit(View v) {
		Intent exitNoticeToHome = new Intent(this, LoginActivity.class);
		exitNoticeToHome.setAction(Intent.ACTION_MAIN);
		exitNoticeToHome.addCategory(Intent.CATEGORY_HOME);
		exitNoticeToHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(exitNoticeToHome);
		Acount.setCurrentDevice(null);
		Acount.setDevices(null);

		Editor ed = sp.edit();
		ed.putString("LoginName", null);
		ed.putString("Password", null);
		ed.putInt("id", 0);
		ed.putBoolean("reget_password", false);
		ed.commit();

		MyApplication.getInstance().exit();
		Intent intent = new Intent(this, GetMessageService.class);
		stopService(intent);

	}
}
