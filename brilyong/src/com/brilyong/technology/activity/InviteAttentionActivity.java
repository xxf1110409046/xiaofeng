package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.NetworkUtils;
import com.brilyong.technology.R;


/**
 * 邀请关注
 * 
 * @author Administrator
 * 
 */
public class InviteAttentionActivity extends BaseUIActivityUtil {

	private EditText username;
	private String name;
	private TextView progress_dialog_message;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invite_attention_activity);
		username = (EditText) findViewById(R.id.tv_username);
		View view = View.inflate(this, R.layout.progress_daogin, null);
		progress_dialog_message = (TextView) view
				.findViewById(R.id.progress_dialog_message);
		dialog = new Dialog(this, R.style.dw_dialog);
		dialog.setCancelable(false);
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 设置布局
	}

	public void Send(View v) {
		name = username.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			Toast.makeText(this, getString(R.string.please_input_telephone), Toast.LENGTH_SHORT).show();
			return;
		}
		if (!NetworkUtils.isNetworkConnected(this)) {
			Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
			return;
		}
		SendTask send = new SendTask();
		send.execute();
		progress_dialog_message.setText(getString(R.string.is_loading));
		dialog.show();
	}

	private class SendTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				return HttpHelperUtils.InviteAttention(name, Acount
						.getCurrentDevice().getId());
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
				if (result == -1) {
					Toast.makeText(getApplicationContext(), getString(R.string.user_not_exist),
							Toast.LENGTH_SHORT).show();
				} else if (result == 0) {
					Toast.makeText(getApplicationContext(), getString(R.string.set_false),
							Toast.LENGTH_SHORT).show();
				} else if (result == 1) {
					Toast.makeText(getApplicationContext(), getString(R.string.set_true),
							Toast.LENGTH_SHORT).show();
					hand.sendEmptyMessage(ZERO);
				} else if(result == -10){
					quitToLogin();
				}
			}
			dialog.dismiss();
			super.onPostExecute(result);
		}
	}

	public void back(View v) {
		finish();
	}
	private static final int ZERO = 1;
	private Handler hand = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ZERO:
				AllTask task = new AllTask();
				task.execute();
				break;

			default:
				break;
			}
		}
		
	};
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
						.GetDeviceListByLoginName(Acount.getCurrentDevice().toString());
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
			super.onPostExecute(result);
		}
	}
}
