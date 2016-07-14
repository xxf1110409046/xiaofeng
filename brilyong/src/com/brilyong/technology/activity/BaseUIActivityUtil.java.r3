package com.blyang.technology.activity;

import com.blyang.technology.app.MyApplication;

import android.app.Activity;
import android.os.Bundle;

public class BaseUIActivityUtil extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		MyApplication.getInstance().removeActivity(this);
		super.onDestroy();
	}

}
