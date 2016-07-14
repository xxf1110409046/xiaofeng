package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.entity.Message;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.DialogUtils;
import com.brilyong.technology.utils.NetworkUtils;
import com.brilyong.technology.R;


/**
 * 完成设备绑定
 * 
 * @author Administrator
 * 
 */
public class DeviceVerificationActivity extends BaseUIActivityUtil {

	private EditText de_code;
	private String deviceId;
	private String phoneNumber;
	private String checkCode;
	private SharedPreferences sp;
	private String loginName;
	private Dialog dialog;
	private TextView progress_dialog_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_verify_activity);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		Intent intent = getIntent();
		deviceId = intent.getExtras().getString("DeviceId");
		phoneNumber = intent.getExtras().getString("PhoneNumber");
		loginName = sp.getString("LoginName", null);
		de_code = (EditText) findViewById(R.id.de_code);
		dialog = DialogUtils.getDialog(this, getString(R.string.is_submit));
	}

	// 提交数据完成绑定
	public void Determine(View v) {
		checkCode = de_code.getText().toString().trim();
		if (TextUtils.isEmpty(checkCode)) {
			Toast.makeText(this, getString(R.string.please_input_code), Toast.LENGTH_SHORT).show();
			return;
		} else if (!NetworkUtils.isNetworkConnected(this)) {
			Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
			return;
		}
		DetermineTask task = new DetermineTask();
		task.execute();
		dialog.show();
	}

	// 异步完成绑定
	private class DetermineTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				return HttpHelperUtils.FinishBindDevice(checkCode, phoneNumber,
						deviceId);
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
					dialog.dismiss();
					Toast.makeText(DeviceVerificationActivity.this, getString(R.string.equipment_exist),
							Toast.LENGTH_SHORT).show();
				} else if (result == 0) {
					dialog.dismiss();
					Toast.makeText(DeviceVerificationActivity.this, getString(R.string.telephone_illegal),
							Toast.LENGTH_SHORT).show();
				} else if (result == 1) {
					Toast.makeText(DeviceVerificationActivity.this,
							getString(R.string.bind_true), Toast.LENGTH_SHORT).show();
					AllTask task = new AllTask();
					task.execute();
				} else if (result == 2) {
					dialog.dismiss();
					Toast.makeText(DeviceVerificationActivity.this,
							getString(R.string.code_false_input_agian), Toast.LENGTH_SHORT).show();
				} else if (result == 3) {
					dialog.dismiss();
					Toast.makeText(DeviceVerificationActivity.this, getString(R.string.operate_fasle),
							Toast.LENGTH_SHORT).show();
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
			} catch (ConnectException e) {
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Device> result) {
			if (result != null) {
				Acount.setDevices(result);
			}
			dialog.dismiss();
			finish();
			AddEquipmentActivity.mActivity.finish();
		}
	}

	// 返回
	public void back(View v) {
		finish();
	}
}
