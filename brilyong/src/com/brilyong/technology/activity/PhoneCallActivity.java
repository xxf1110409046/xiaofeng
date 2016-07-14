package com.brilyong.technology.activity;


import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.DevDingtongConfig;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class PhoneCallActivity extends BaseUIActivityUtil{
	private Device aDevice;
	private EditText editText;
	private String listenerNumber; 
	private Button phone_call;
	private DevDingtongConfig list_phone;
  @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.phone_call);
	editText = (EditText) findViewById(R.id.phone_number_call);
	phone_call = (Button) findViewById(R.id.phone_call);
	aDevice = Acount.getCurrentDevice();
	list_phone = aDevice.getDevDingtongConfig();
	String phone = list_phone.getListenerPhone();
	if(!TextUtils.isEmpty(phone)){
		editText.setText(phone);
	}
	phone_call.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			  listenerNumber = editText.getText().toString();
			  ListenerAsynTask listener = new ListenerAsynTask();
			  listener.execute();
			
		}
	});
	
}
  private class ListenerAsynTask extends AsyncTask<Void, Void, Integer>{

	@Override
	protected Integer doInBackground(Void... arg0) {
		try {
			if(Acount.getCurrentDevice().getTypeId() == 1){
				return HttpHelperUtils.Dev_Dingtong_SetListen(aDevice.getId(),listenerNumber);
			}else{
				return HttpHelperUtils.DevHunterListenSetting(aDevice.getId(),listenerNumber);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		switch (result) {
		case 1:
			Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
			Acount.getCurrentDevice().getDevDingtongConfig().setListenerPhone(listenerNumber);
			Intent intent = new Intent(PhoneCallActivity.this,HomeBaiduActivity.class);
			startActivity(intent);
			break;
		case 0:
			Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
			break;
		case -1:
			Toast.makeText(getApplicationContext(), getString(R.string.equipment_is_not_exist), 0).show();
			break;
		case -10:
			quitToLogin();
			break;
		case -11:
			equipmentOffLine();
			break;
		default:
			break;
		}
	}
  }
	public void back(View v) {
		finish();
	}
  
}
