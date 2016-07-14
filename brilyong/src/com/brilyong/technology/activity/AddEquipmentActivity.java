package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brilyong.technology.R;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.BindDeviceResult;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.DialogUtils;

import com.zxing.activity.CaptureActivity;

/**
 * Ìí¼ÓÉè±¸
 * 
 * @author Administrator
 * 
 */
public class AddEquipmentActivity extends BaseUIActivityUtil implements
		OnClickListener {
	private Button scanning;
	private EditText device_code;
	private EditText device_phone;
	private String code;
	private String phone;
	public static Activity mActivity;
	private Dialog dialog;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mActivity = this;
		setContentView(R.layout.add_device_info_activity);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		device_code = (EditText) findViewById(R.id.device_code);
		device_phone = (EditText) findViewById(R.id.device_phone);
		scanning = (Button) findViewById(R.id.Scanning);
		scanning.setOnClickListener(this);
		dialog = DialogUtils.getDialog(this, getString(R.string.is_loading));
	}

	public void back(View v) {
		finish();
	}

	public void Submit(View v) {
		code = device_code.getText().toString().trim();
		phone = device_phone.getText().toString().trim();
		if (TextUtils.isEmpty(code)) {
			Toast.makeText(this, getString(R.string.please_input_equipment_number), Toast.LENGTH_SHORT).show();
			return;
		} else if (TextUtils.isEmpty(phone)) {
			Toast.makeText(this, getString(R.string.please_input_equipment_phoneid), Toast.LENGTH_SHORT).show();
			return;
		}
		BindTask bindtask = new BindTask();
		bindtask.execute();
		dialog.show();
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		};
		new Thread(runnable).start();
	}

	private class BindTask extends AsyncTask<Void, Void, BindDeviceResult> {

		@Override
		protected BindDeviceResult doInBackground(Void... params) {
			try {
				return HttpHelperUtils.BindDevice(phone, code);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(BindDeviceResult result) {
			if(result.getMessage_command() != null){
				sendMsm(phone,result.getMessage_command());
				GotoActivity();
			}else{
				switch (result.getCode()) {
				case -1:
					Toast.makeText(AddEquipmentActivity.this, getString(R.string.no_equipment_please_input),
							Toast.LENGTH_SHORT).show();
					break;
				case 0:
					Toast.makeText(AddEquipmentActivity.this, getString(R.string.phone_is_illegal),
							Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(AddEquipmentActivity.this, getString(R.string.equipment_is_binding),
							Toast.LENGTH_SHORT).show();
					break;
				case 2:
					GotoActivity();
					break;
				case 3:
					Toast.makeText(AddEquipmentActivity.this, getString(R.string.ilegal_please),
							Toast.LENGTH_SHORT).show();
					break;
				case 4:
					Toast.makeText(AddEquipmentActivity.this, getString(R.string.equipment_is_fasle),
							Toast.LENGTH_SHORT).show();
					break;
				case 5:
					Toast.makeText(AddEquipmentActivity.this, getString(R.string.wait_admin_agree),
							Toast.LENGTH_SHORT).show();
					AllTasks task = new AllTasks();
					task.execute();
					break;

				default:
					break;
				}
			}
			dialog.dismiss();
			super.onPostExecute(result);
		}
	}
	
	//发�?短信给手�?
	public void sendMsm(String phone,String text){
		SmsManager manager = SmsManager.getDefault();
		manager.sendTextMessage(phone, null, text, null, null);
	}
		public void GotoActivity(){
		Intent intent = new Intent(AddEquipmentActivity.this,
				DeviceVerificationActivity.class);
		intent.putExtra("DeviceId", code);
		intent.putExtra("PhoneNumber", phone);
		startActivity(intent);
		Toast.makeText(AddEquipmentActivity.this, getString(R.string.please_wait_server_response),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			device_code.setText(scanResult);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Scanning:
			Intent openCameraIntent = new Intent(this, CaptureActivity.class);
			startActivityForResult(openCameraIntent, 0);
			break;
		default:
			break;
		}

	}
	
	/**
	 * Òì²½»ñÈ¡ÓÃ»§ÏÂËùÓÐÊý¾Ý
	 * @author Administrator
	 * 
	 */
	private class AllTasks extends AsyncTask<String, Void, ArrayList<Device>> {
		@Override
		protected ArrayList<Device> doInBackground(String... params) {
			String loginName = sp.getString("LoginName", null);
			try {
				ArrayList<Device> listDevice = HttpHelperUtils
						.GetDeviceListByLoginName(loginName);
				return listDevice;
			} catch (ConnectException e) {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Device> result) {
			if (result != null) {
				Acount.setDevices(result);
			}
			Intent intent = new Intent(AddEquipmentActivity.this,HomeBaiduActivity.class);
			startActivity(intent);
		}
	}
}
