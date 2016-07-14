package com.brilyong.technology.activity;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.R;


/**
 * 注册模块 完成新用户的注册
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends BaseUIActivityUtil implements
		OnClickListener {
	private static final int GTECODEERROR = 0;
	private static final int ERROR = 1;
	private static final int GETOK = 2;
	private static final int ZCOK = 3;
	private static final int CODEERROR = 4;
	private static final int CZERROR = 5;
	private static final int UNCESSECE = 6;


	private EditText user_phone; // 要注册的手机号
	private EditText ver_code; // 手机验证码
	private Button get_code; // 获取手机验证码
	private EditText user_psw; // 用户密码
	private Button psw_display;
	private Button rsok;
	private CheckBox cb_xieyi;
	private TextView xieyiTextView;
	private String userPhone;
	private String verCodeString;
	private String userPsw;
	private TimeCount time;
	private EditText queuser_psw;
	private Button que_psw_display;
	private String quserPsw;
	private boolean home;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		findid();
		psw_display.setOnClickListener(this);
		que_psw_display.setOnClickListener(this);
		cb_xieyi.setOnCheckedChangeListener(listener);
		xieyiTextView.setOnClickListener(listeners);
		time = new TimeCount(60000, 1000);
		home = sp.getBoolean("HOME", false);
		initDialog();
	}

	private void initDialog() {
		View view = View.inflate(this, R.layout.progress_daogin, null);
		progress_dialog_message = (TextView) view
				.findViewById(R.id.progress_dialog_message);
		dialog = new Dialog(this, R.style.dw_dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 设置布局
	}

	private void findid() {
		user_phone = (EditText) findViewById(R.id.rs_user_phone);
		ver_code = (EditText) findViewById(R.id.ver_code);
		xieyiTextView = (TextView) findViewById(R.id.xieyi);
		user_psw = (EditText) findViewById(R.id.user_psw);
		psw_display = (Button) findViewById(R.id.psw_display);
		get_code = (Button) findViewById(R.id.get_code);
		rsok = (Button) findViewById(R.id.rsok);
		cb_xieyi = (CheckBox) findViewById(R.id.cb_xieyi);
		queuser_psw = (EditText) findViewById(R.id.queuser_psw);
		que_psw_display = (Button) findViewById(R.id.que_psw_display);
	}

	OnClickListener listeners = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(RegisterActivity.this,
					UserProtocolActivity.class);
			startActivity(intent);
		}
	};
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				rsok.setClickable(true);
				rsok.setTextColor(Color.WHITE);
			} else {
				rsok.setTextColor(getResources().getColor(R.color.rs_button));
				rsok.setClickable(false);
			}

		}
	};

	private void getData() {
		userPhone = user_phone.getText().toString().trim();
		verCodeString = ver_code.getText().toString().trim();
		userPsw = user_psw.getText().toString().trim();
		quserPsw = queuser_psw.getText().toString().trim();
	}

	// 获取验证码
	public void getCode(View v) {
		getData();
		if (TextUtils.isEmpty(userPhone)) {
			Toast.makeText(this, getString(R.string.please_input_user_name), 0).show();
			return;
		}
		dialog.show();
		RegisterTask task = new RegisterTask();
		task.execute();
		time.start();
	}

	private Handler hand = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GTECODEERROR:
				dialog.dismiss();
				break;
			case ERROR:
				dialog.dismiss();
				break;
			case GETOK:
				dialog.dismiss();
				break;
			case ZCOK:
				dialog.dismiss();
				break;
			case CODEERROR:
				dialog.dismiss();
				break;
			case CZERROR:
				dialog.dismiss();
				break;			
			case UNCESSECE:
				dialog.dismiss();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 异步获取验证码
	 * 
	 * @author Administrator
	 * 
	 */
	private class RegisterTask extends AsyncTask<Void, Void, Integer> {
		Message msg = Message.obtain();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress_dialog_message.setText(getString(R.string.is_getting_code));
		}

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				return HttpHelperUtils.GetUserRegCheckCode(userPhone);
			} catch (Exception e) {
				msg.what = ERROR;
				progress_dialog_message.setText(getString(R.string.request_false));
				hand.sendMessageDelayed(msg, 1000);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 0) {
				msg.what = GTECODEERROR;
				progress_dialog_message.setText(getString(R.string.get_code_false));
				hand.sendMessageDelayed(msg, 1000);
			} else if (result == 1) {
				msg.what = GETOK;
				hand.sendMessage(msg);
			}else if(result == 2){
				msg.what = UNCESSECE;
				progress_dialog_message.setText(getString(R.string.user_account_is_regist));
				hand.sendMessageDelayed(msg, 1000);
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * 异步注册
	 * 
	 * @author Administrator
	 * 
	 * 
	 * 
	 */
	private class LoginTask extends AsyncTask<String, Void, Boolean> {
		@Override
		protected Boolean doInBackground(String... arg0) {
			try {
				return HttpHelperUtils.Login(userPhone, userPsw);
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
				ed.putString("LoginName", userPhone);
				ed.putString("Password", userPsw);
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
						.GetDeviceListByLoginName(userPhone);
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
//				Intent intent = new Intent(RegisterActivity.this,
//						FunctionListActivity.class);
//				startActivity(intent);
//				finish();
			} else {
				Intent intent = new Intent(RegisterActivity.this,
						HomeBaiduActivity.class);
				startActivity(intent);
				finish();
			}
			super.onPostExecute(result);
		}
	}
	
	private class RegTask extends AsyncTask<Void, Void, Integer> {

		Message msg = Message.obtain();

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				return HttpHelperUtils.UserReg(userPhone, verCodeString,
						userPsw);
			} catch (Exception e) {
				msg.what = ERROR;
				hand.sendMessage(msg);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 1) {
				msg.what = ZCOK;
				hand.sendMessage(msg);
				setTag(userPhone);
				Toast.makeText(RegisterActivity.this, getString(R.string.regist_true), 0).show();
				new LoginTask().execute();
			} else if (result == 0) {
				msg.what = CODEERROR;
				progress_dialog_message.setText(getString(R.string.code_fasle));
				hand.sendMessageDelayed(msg, 1000);
			} else if (result == -1) {
				msg.what = CZERROR;
				progress_dialog_message.setText(getString(R.string.operate_fasle));
				hand.sendMessageDelayed(msg, 1000);
			} else if(result == 2){
				msg.what = GETOK;
				progress_dialog_message.setText(getString(R.string.user_is_regist));
				hand.sendMessageDelayed(msg, 1000);
			}
			super.onPostExecute(result);
		}
	}
	
	

	public void setTag(String phone) {
		String tag =  phone;
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

	// 确认注册
	public void registerok(View v) {
		getData();
		if (TextUtils.isEmpty(userPhone)) {
			Toast.makeText(this, getString(R.string.please_input_user_name), 0).show();
			return;
		} else if (TextUtils.isEmpty(verCodeString)) {
			Toast.makeText(this, getString(R.string.please_input_code), 0).show();
			return;
		} else if (TextUtils.isEmpty(userPsw)) {
			Toast.makeText(this, getString(R.string.please_input_password), 0).show();
			return;
		} else if (TextUtils.isEmpty(quserPsw)) {
			Toast.makeText(this, getString(R.string.please_input_password_ok), 0).show();
			return;
		} else if (!(userPsw.equals(quserPsw))) {
			Toast.makeText(this, getString(R.string.password_different), 0).show();
			return;
		}
		progress_dialog_message.setText(getString(R.string.is_regist));
		dialog.show();
		RegTask reg = new RegTask();
		reg.execute();
	}

	boolean isShows = true;
	boolean isShow = true;
	private Dialog dialog;
	private TextView progress_dialog_message;
	private SharedPreferences sp;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.psw_display:
			if (isShow) {
				psw_display.setText(getString(R.string.hide));
				user_psw.setInputType(InputType.TYPE_CLASS_TEXT);
				isShow = false;
			} else {
				psw_display.setText(getString(R.string.show));
				int inputType = InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD;
				user_psw.setInputType(inputType);
				isShow = true;
			}
			break;
		case R.id.que_psw_display:
			if (isShows) {
				que_psw_display.setText(R.string.hide);
				queuser_psw.setInputType(InputType.TYPE_CLASS_TEXT);
				isShows = false;
			} else {
				que_psw_display.setText(R.string.show);
				int inputType = InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD;
				queuser_psw.setInputType(inputType);
				isShows = true;
			}
			break;

		default:
			break;
		}

	}

	// 计时器
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		// 计时完毕时触发
		@Override
		public void onFinish() {
			get_code.setText(getString(R.string.again_text));
			get_code.setClickable(true);
			time.cancel();
		}

		// 计时过程显示
		@Override
		public void onTick(long millisUntilFinished) {
			get_code.setClickable(false);
			get_code.setText(millisUntilFinished / 1000 + getString(R.string.minute_again_send));
		}
	}

	/**
	 * 已有账号直接登录
	 * 
	 * @param v
	 */
	public void login(View v) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	public void back(View v) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
