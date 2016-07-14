package com.brilyong.technology.activity;

import cn.jpush.android.api.JPushInterface;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.service.GetMessageService;

public class BaseUIActivityUtil extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
	}

	public void back(View v) {
		finish();
	}

	@Override
	protected void onDestroy() {
		MyApplication.getInstance().removeActivity(this);
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		JPushInterface.onResume(this);
		super.onResume();	
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		JPushInterface.onPause(this);
		super.onPause();
	}
	
	public void quitToLogin() {
		Toast.makeText(this, "您的登录状态已过期，请重新登录", Toast.LENGTH_LONG).show();
		Intent exitNoticeToHome = new Intent(this, LoginActivity.class);
		exitNoticeToHome.setAction(Intent.ACTION_MAIN);
		exitNoticeToHome.addCategory(Intent.CATEGORY_HOME);
		exitNoticeToHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(exitNoticeToHome);
		Acount.setCurrentDevice(null);
		Acount.setDevices(null);
		MyApplication.getInstance().exit();
		Intent intent = new Intent(this, GetMessageService.class);
		stopService(intent);
	}
	
	public void equipmentOffLine(){
		Toast.makeText(this, "设备已离线", Toast.LENGTH_LONG).show();
	}
}
