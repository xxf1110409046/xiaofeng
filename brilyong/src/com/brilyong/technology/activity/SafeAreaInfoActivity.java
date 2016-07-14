package com.brilyong.technology.activity;

import java.net.ConnectException;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.DialogUtils;
import com.brilyong.technology.R;


/**
 * 安全区详情
 * 
 * @author Administrator
 * 
 */
public class SafeAreaInfoActivity extends BaseUIActivityUtil {
	private int radius;
	private EditText safeName;
	private String date;
	private String name;
	private Dialog dialog;
	private int type = 0;// 默认围栏类型
	private RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.safearea_info_activity);
		SafeAreaActivity.instance.finish();
		safeName = (EditText) findViewById(R.id.safe_name);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		Bundle bundle = this.getIntent().getExtras();
		double latitude = bundle.getDouble("latitude");
		double longitude = bundle.getDouble("longitude");
		date = latitude + "," + longitude;
		radius = bundle.getInt("Radius");

		dialog = DialogUtils.getDialog(this, getString(R.string.is_adding));
		radioGroup.setOnCheckedChangeListener(listener);

	}

	// 选择围栏类型
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (group.getCheckedRadioButtonId()) {
			case R.id.into:
				type = 0;
				break;
			case R.id.out:
				type = 1;
				break;
			case R.id.intoout:
				type = 2;
				break;
			}
		}
	};

	// 完成围栏添加
	public void safeOk(View v) {
		name = safeName.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.please_input_area_name),
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
		SafeTask safe = new SafeTask();
		safe.execute();
		dialog.show();
	}

	// 提交围栏数据
	private class SafeTask extends AsyncTask<Void, Void, Integer> {

		Message msg = Message.obtain();

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				return HttpHelperUtils.AddGeofence(Acount.getCurrentDevice()
						.getId(), name, radius, date, type);
			} catch (ConnectException e) {
				msg.what = 1;
				hand.sendMessage(msg);
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result != null) {
				if (result == 0) {
					msg.what = 0;
					hand.sendMessageDelayed(msg, 500);
				} else if (result == 1) {
					dialog.dismiss();
					Intent intent = new Intent(SafeAreaInfoActivity.this,SafeAreaActivity.class);
					startActivity(intent);
//					intent.setAction("com.safe.add");
//					sendBroadcast(intent);
					Toast.makeText(getApplicationContext(), getString(R.string.add_true),
							Toast.LENGTH_SHORT).show();
//					AddSafeActivity.sActivity.finish();
//					finish();
				}
			}

			super.onPostExecute(result);
		}
	}

	private Handler hand = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				dialog.dismiss();
				break;
			case 1:// 添加成功
//				AddSafeActivity.sActivity.finish();
				Intent intent = new Intent(SafeAreaInfoActivity.this,SafeAreaActivity.class);
				startActivity(intent);
				finish();
				dialog.dismiss();
				break;

			default:
				break;
			}

		};
	};

	public void back(View v) {
		finish();
	}
}
