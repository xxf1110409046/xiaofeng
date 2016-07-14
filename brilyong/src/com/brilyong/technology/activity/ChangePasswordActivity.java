package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.httputils.AppConfig;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.R;


/**
 * 修改密码
 * 
 * @author Administrator
 * 
 */
public class ChangePasswordActivity extends BaseUIActivityUtil {
	private static final int CODEERROR = 0;
	private static final int NOUSER = 2;

	private TextView user_phone;
	private EditText verification_code;
	private Button getverification_code;
	private EditText new_password;
	private EditText confirm_password;
	private TimeCount time;
	private String username;
	private ForTask fortask;
	private String checkCode;// 验证码
	private String password;// 新密码
	private String newPassword;// 新密码
	private Dialog dialog;
	private TextView progress_dialog_message;
	private FindTask find;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password_activity);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		home = sp.getBoolean("HOME", false);
		user_phone = (TextView) findViewById(R.id.updatePhoen);
		verification_code = (EditText) findViewById(R.id.verification);
		getverification_code = (Button) findViewById(R.id.getverification);
		new_password = (EditText) findViewById(R.id.new_password);
		confirm_password = (EditText) findViewById(R.id.confirm_password);
		// 初始化对话框
		View view = View.inflate(this, R.layout.progress_daogin, null);
		progress_dialog_message = (TextView) view
				.findViewById(R.id.progress_dialog_message);
		dialog = new Dialog(this, R.style.dw_dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 设置布局

		username = getIntent().getStringExtra("username");
		user_phone.setText(username);
		
		time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
		time.start();
		
		fortask = new ForTask();
		fortask.execute();
	}

	public void getDate() {
		checkCode = verification_code.getText().toString().trim();
		password = new_password.getText().toString().trim();
		newPassword = confirm_password.getText().toString().trim();
	}

	// 获取验证码
	public void getCodes(View v) {
		if(time == null){
			time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
			time.start();
			fortask = new ForTask();
			fortask.execute();
		}
	}

	/**
	 * 提交更新的数据
	 * 
	 * @param v
	 */
	public void submit(View v) {
		getDate();
		if (TextUtils.isEmpty(checkCode)) {
			Toast.makeText(this, getString(R.string.please_input_code), 0).show();
			return;
		} else if (TextUtils.isEmpty(password)) {
			Toast.makeText(this, getString(R.string.please_input_password), 0).show();
			return;
		} else if (TextUtils.isEmpty(newPassword)) {
			Toast.makeText(this, getString(R.string.please_input_password_ok), 0).show();
			return;
		} else if (!(password.equals(newPassword))) {
			Toast.makeText(this, getString(R.string.password_different), 0).show();
			return;
		}
		find = new FindTask();
		find.execute();
	}

	/**
	 * 忘记密码
	 * 
	 * @author Administrator
	 * 
	 */
	private class FindTask extends AsyncTask<Void, Void, Integer> {

		Message msg = Message.obtain();

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				HttpPost request = new HttpPost(AppConfig.URL
						+ "OpenAPI/MobileService.asmx" + AppConfig.FINDPASSWORD);
				request.addHeader("Content-Type",
						"application/json; charset=utf-8");
				JSONObject obj = new JSONObject();
				obj.put("LoginName", username);
				obj.put("Password", password);
				obj.put("CheckCode", checkCode);
				obj.put("AppKey", AppConfig.APPKEY);
				HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
				request.setEntity(bodyEntity);
				DefaultHttpClient client = new DefaultHttpClient();
				client.setCookieStore(HttpHelperUtils.cook);
				HttpResponse httpResponse = client.execute(request);
				if (httpResponse.getStatusLine().getStatusCode() != 404) {
					String result;
					result = EntityUtils.toString(httpResponse.getEntity());
					return new JSONObject(result).getInt("d");
				}
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 1) {
				new LoginTask().execute();
				progress_dialog_message.setText(getString(R.string.is_loging));
			} else if (result == 0) {
				msg.what = CODEERROR;
				progress_dialog_message.setText(getString(R.string.code_fasle));
				hand.sendMessageDelayed(msg, 1000);
			} else if (result == -1) {
				msg.what = CODEERROR;
				progress_dialog_message.setText(getString(R.string.operate_fasle));
				hand.sendMessageDelayed(msg, 1000);
			}
			super.onPostExecute(result);
		}

	}

	/**
	 * 获取验证码
	 * 
	 * @author Administrator
	 * 
	 */
	private class ForTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			progress_dialog_message.setText(getString(R.string.is_getting_code));
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				return HttpHelperUtils.GetUserForgotPassword(username);
			} catch (Exception e) {
				Message msg = Message.obtain();
				msg.what = CODEERROR;
				progress_dialog_message.setText(getString(R.string.request_fasle));
				hand.sendMessageDelayed(msg, 500);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 0) {
				Message msg = Message.obtain();
				msg.what = CODEERROR;
				progress_dialog_message.setText(getString(R.string.get_code_false));
				hand.sendMessageDelayed(msg, 500);
			} else if (result == 1) {
				
				dialog.dismiss();
			}else if(result == -1){
				Toast.makeText(ChangePasswordActivity.this, getString(R.string.user_not_regist), 0).show();
				dialog.dismiss();
			}
			
			super.onPostExecute(result);
		}
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		// 计时完毕时触发
		@Override
		public void onFinish() {
			getverification_code.setText(getString(R.string.get_again));
			getverification_code.setClickable(true);
			time = null;
			find = null;
		}

		// 计时过程显示
		@Override
		public void onTick(long millisUntilFinished) {
			getverification_code.setClickable(false);
			getverification_code.setText(millisUntilFinished / 1000 + getString(R.string.send_nimute_again));
		}
	}

	private Handler hand = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CODEERROR:
				dialog.dismiss();
				break;
			case NOUSER:
				dialog.dismiss();
				break;
			}
			
				
		};
	};

	private SharedPreferences sp;

	private boolean home;

	/**
	 * 异步登录
	 * 
	 * @author Administrator
	 * 
	 */
	private class LoginTask extends AsyncTask<String, Void, Boolean> {
		@Override
		protected Boolean doInBackground(String... arg0) {
			try {
				return HttpHelperUtils.Login(username, password);
			} catch (ConnectException e) {
				e.printStackTrace();
			} catch (Exception e) {
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				Editor ed = sp.edit();
				ed.putString("LoginName", username);
				ed.putString("Password", password);
				ed.commit();
				new AllTask().execute();
				progress_dialog_message.setText(getString(R.string.load_data));
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
						.GetDeviceListByLoginName(username);
				return listDevice;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Device> result) {
			dialog.dismiss();
			Acount.setDevices(result);
			if (home) {
//				Intent intent = new Intent(ChangePasswordActivity.this,
//						FunctionListActivity.class);
//				startActivity(intent);
//				finish();
			} else {
				Intent intent = new Intent(ChangePasswordActivity.this,
						HomeBaiduActivity.class);
				startActivity(intent);
				finish();
			}
			super.onPostExecute(result);
		}
	}
	public void back(View v){
		finish();
	}
}
