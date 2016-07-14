package com.brilyong.technology.activity;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.httputils.MutualUtil;
import com.brilyong.technology.service.GetMessageService;
import com.brilyong.technology.utils.NetworkUtils;
import com.brilyong.technology.R;


/**
 * 登录系统
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends BaseUIActivityUtil implements
		OnClickListener {
	private static final int LOGINOK = 1;
	private static final int GETOK = 2;
	private static final int LOGINERROE = 0;
	private EditText login_username;
	private EditText login_password;
	private Button action_login; // 确认登录
	private Button login_forget_psw; // 忘记密码
	private Button login_register; // 立即注册
	private String loginName; // 用户名
	private String password; // 用户密码

	private SharedPreferences sp;
	private CheckBox reget_password;
	private boolean isEnter;
	private boolean reget;
	private String login_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		home = sp.getBoolean("HOME", false);
		reget = sp.getBoolean("reget_password", false);
		loginName = sp.getString("LoginName", null);
	    login_text = sp.getString("LoginName", null);
		password = sp.getString("Password", null);
	
//		if (TextUtils.isEmpty(loginName) && TextUtils.isEmpty(password)) {
			setContentView(R.layout.login_activity);
			// 初始化视图
			initViews();
			// 等候对话框
			View view = View.inflate(this, R.layout.progress_daogin, null);
			progress_dialog_message = (TextView) view
					.findViewById(R.id.progress_dialog_message);
			dialog = new Dialog(this, R.style.dw_dialog);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setContentView(view, new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT));// 设置布局
			isEnter = true;
//		} else {
//			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//					WindowManager.LayoutParams.FLAG_FULLSCREEN);
//			setContentView(R.layout.splash_activity);
//			isEnter = false;
//			LoginTask loginTask = new LoginTask();
//			loginTask.execute();
//		}
			if(reget == true){
				login_username.setText(loginName);
				login_password.setText(password);
			}	
		
		
	}
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean selected) {
			// TODO Auto-generated method stub
			Editor ed = sp.edit();
			if(selected){
				ed.putBoolean("reget_password", true);
			}else{
				ed.putBoolean("reget_password", false);
			}
			ed.commit();
		}
	};

	// 获取viewid
	private void initViews() {
		login_username = (EditText) findViewById(R.id.login_username);
		login_password = (EditText) findViewById(R.id.login_password);
		action_login = (Button) findViewById(R.id.action_login);
		login_forget_psw = (Button) findViewById(R.id.login_forget_psw);
		login_register = (Button) findViewById(R.id.login_register);
		reget_password = (CheckBox) findViewById(R.id.reget_password);
		reget_password.setOnCheckedChangeListener(listener);
		reget_password.setChecked(reget);
		action_login.setOnClickListener(this);
		login_register.setOnClickListener(this);
		login_forget_psw.setOnClickListener(this);
	}

	/**
	 * 登录 进入主界面
	 */
	@Override
	public void onClick(View v) {
		
		loginName = login_username.getText().toString().trim();
		password = login_password.getText().toString().trim();
		switch (v.getId()) {
		// 登录
		case R.id.action_login:
			if(!login_username.getText().toString().trim().equals(login_text)){
				Editor ed = sp.edit();
				ed.putInt("id", 0);
				ed.commit();
			}
			if (TextUtils.isEmpty(loginName)) {
				Toast.makeText(this, getString(R.string.please_input_user_name), 0).show();
				return;
			} else if (TextUtils.isEmpty(password)) {
				Toast.makeText(this, getString(R.string.please_input_password), 0).show();
				return;
			}else {
				LoginTask loginTask = new LoginTask();
				if (NetworkUtils.isNetworkConnected(this)) {
					loginTask.execute();
					progress_dialog_message.setText(getString(R.string.is_loging));
					dialog.show();
				} else {
					Toast.makeText(this, getString(R.string.please_check_internet), 0).show();
				}
			}
			break;
		// 忘记密码
		case R.id.login_forget_psw:
			if (TextUtils.isEmpty(loginName)) {
				Toast.makeText(this, R.string.please_input_user_name, 0).show();
				return;
			}
			Intent changeIntent = new Intent(this, ChangePasswordActivity.class);
			changeIntent.putExtra("username", loginName);
			startActivity(changeIntent);
			break;
		// 立即注册
		case R.id.login_register:
			Intent registerintent = new Intent(this, RegisterActivity.class);
			startActivity(registerintent);
			finish();
			break;
		}

	}

	/**
	 * 异步登录
	 * 
	 * @author Administrator
	 * 
	 */
	private class LoginTask extends AsyncTask<String, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			try {
				return HttpHelperUtils.Login(loginName, password);
			} catch (ConnectTimeoutException e) {

			} catch (SocketTimeoutException e) {

			} catch (ConnectException e) {

				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				Message msg = hand.obtainMessage();
				progress_dialog_message.setText(getString(R.string.login_false));
				hand.sendMessageDelayed(msg, 1000);
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			if (isEnter) {
				progress_dialog_message.setText(getString(R.string.is_loging));
				MutualUtil.DialogDismiss(dialog);
			}
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				setTag(loginName);
				Message msg = hand.obtainMessage();
				msg.what = LOGINOK;
				hand.sendMessage(msg);
				if (isEnter) {
					progress_dialog_message.setText(R.string.load_data);
				}
				Intent intent1 = new Intent(LoginActivity.this, GetMessageService.class);
				startService(intent1);
			} else {
				if (isEnter) {
					Message msg = hand.obtainMessage();
					progress_dialog_message.setText(getString(R.string.account_or_password_false));
					hand.sendMessageDelayed(msg, 1000);
				} else {
					Message msg = hand.obtainMessage();
					msg.what = LOGINERROE;
					hand.sendMessage(msg);
				}

			}
			super.onPostExecute(result);
		}

	}

	/**
	 * 异步获取用户下所有数据
	 * 
	 * @author Administrator
	 * 
	 */
	private class AllTask extends AsyncTask<String, Void, ArrayList<Device>> {
		@Override
		protected ArrayList<Device> doInBackground(String... params) {
			try {
				ArrayList<Device> listDevice = HttpHelperUtils
						.GetDeviceListByLoginName(loginName);
				return listDevice;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Device> result) {
			Acount.setDevices(result);
			Message msg = hand.obtainMessage();
			msg.what = GETOK;
			hand.sendMessage(msg);
			super.onPostExecute(result);
		}
	}

	private boolean home;// 判断哪个是主界面
	private TextView progress_dialog_message;
	private Dialog dialog;

	private Handler hand = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOGINOK:
				Editor ed = sp.edit();
				ed.putString("LoginName", loginName);
				ed.putString("Password", password);
				ed.commit();
				AllTask alltask = new AllTask();
				alltask.execute();
				new LoginSavetask().execute(); 
				break;
			case GETOK:
				if (isEnter) {
					dialog.dismiss();
				}
				if (home) {
//					Intent intent = new Intent(LoginActivity.this,
//							FunctionListActivity.class);
//					startActivity(intent);
//					finish();
				} else {
					Intent intent = new Intent(LoginActivity.this,
							HomeBaiduActivity.class);
					startActivity(intent);
					finish();
				}
				break;
			case LOGINERROE:
				if (isEnter) {
					dialog.dismiss();
				}
				break;
			default:
				break;
			}

		};
	};

	public void setTag(String phone) {
		String tag = phone;
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!isValidTagAndAlias(sTagItme)) {
				Toast.makeText(this, "kong", Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
			JPushInterface.setAliasAndTags(getApplicationContext(), null,
					tagSet, null);
		}
	}

	public static boolean isValidTagAndAlias(String s) {
		Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
		Matcher m = p.matcher(s);
		return m.matches();
	}
	
	private class LoginSavetask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.SaveToken(loginName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
}
