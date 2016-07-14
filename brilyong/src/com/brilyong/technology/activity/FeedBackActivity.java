package com.brilyong.technology.activity;

import java.net.ConnectException;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.DialogUtils;
import com.brilyong.technology.utils.NetworkUtils;
import com.brilyong.technology.R;


/**
 * 意见反馈
 * 
 * @author Administrator
 * 
 */
public class FeedBackActivity extends BaseUIActivityUtil {

	private EditText fancontent;
	private EditText fanphone;
	private EditText fanemain;
	private String phone;
	private String emain;
	private String content;
	private SharedPreferences sp;
	private String loginName;
	private TextView progress_dialog_message;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_activity);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		loginName = sp.getString("LoginName", null);
		fancontent = (EditText) findViewById(R.id.fan_content);
		fanphone = (EditText) findViewById(R.id.fan_phone);
		fanemain = (EditText) findViewById(R.id.fang_emain);
		dialog = DialogUtils.getDialog(this, getString(R.string.is_submit));
	}

	// 提交数据
	public void submit(View v) {
		content = fancontent.getText().toString().trim();
		emain = fanemain.getText().toString().trim();
		phone = fanphone.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			Toast.makeText(this, getString(R.string.please_input_feedback_content), Toast.LENGTH_SHORT).show();
			return;
		} else if (TextUtils.isEmpty(phone)) {
			Toast.makeText(this, getString(R.string.please_input_telephone), Toast.LENGTH_SHORT).show();
			return;
		} else if (TextUtils.isEmpty(emain)) {
			Toast.makeText(this, getString(R.string.please_input_email_adress), Toast.LENGTH_SHORT).show();
			return;
		}
		if (!NetworkUtils.isNetworkConnected(this)) {
			Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
			return;
		}
		FanTask fan = new FanTask();
		fan.execute();
		dialog.show();
	}

	// 提交意见反馈
	private class FanTask extends AsyncTask<Void, Void, Integer> {
		@Override
		protected Integer doInBackground(Void... params) {
			try {
				return HttpHelperUtils.Feedback(loginName, phone, emain,
						content);
			} catch (ConnectException e) {
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
					Toast.makeText(getApplicationContext(), getString(R.string.operate_fasle),
							Toast.LENGTH_SHORT).show();
				} else if (result == 1) {
					Toast.makeText(getApplicationContext(), getString(R.string.submit_true),
							Toast.LENGTH_SHORT).show();
					finish();
				}
			}
			dialog.dismiss();
			super.onPostExecute(result);
		}

	}

	public void back(View v) {
		finish();
	}
}
