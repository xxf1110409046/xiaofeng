package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.google.zxing.WriterException;
import com.brilyong.technology.R;
import com.zxing.encoding.EncodingHandler;

public class CreateQrCodeActivity extends BaseUIActivityUtil{
	private ImageView image_code;
	private Button btn_delete;
	private SharedPreferences sp;
	private String loginName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createcode);
		image_code = (ImageView) findViewById(R.id.image_code);	
		btn_delete = (Button) findViewById(R.id.btn_delete);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		loginName = sp.getString("LoginName", null);
		try {
			image_code.setImageBitmap(EncodingHandler.createQRCode(Acount.getCurrentDevice().getId(), 500));
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btn_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				UnBindTask task = new UnBindTask();
//				task.execute();
			}
		});
	}
	
//	private class UnBindTask extends AsyncTask<Void, Void, Integer> {
//
//		@Override
//		protected Integer doInBackground(Void... params) {
//			try {
//				return HttpHelperUtils.UnBindDevice(loginName, Acount
//						.getCurrentDevice().getId());
//			} catch (ConnectException e) {
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Integer result) {
//			if (result != null) {
//				if (result == 0) {
//					Toast.makeText(getApplicationContext(), "操作失败", 0).show();
//				} else if (result == -1) {
//					Toast.makeText(getApplicationContext(), "设备不存在", 0).show();
//				} else if (result == 1) {
//					Toast.makeText(getApplicationContext(), "keyi", 0).show();
//					new AllTask().execute();
//					Editor ed = sp.edit();
//					ed.putInt("id", 0);
//					ed.commit();
//				}
//			}
//			super.onPostExecute(result);
//		}
//
//	} 
//	/**
//	 * 异步获取用户下所有数据
//	 * @author Administrator
//	 * 
//	 */
//	private class AllTask extends AsyncTask<String, Void, ArrayList<Device>> {
//		@Override
//		protected ArrayList<Device> doInBackground(String... params) {
//			try {
//				ArrayList<Device> listDevice = HttpHelperUtils
//						.GetDeviceListByLoginName(loginName);
//				return listDevice;
//			} catch (ConnectException e) {
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<Device> result) {
//			if (result != null) {
//				Acount.setDevices(result);
//			}
//			finish();
//		}
//	}
//
//	// 进入设备信息
//	public void deviceInfo(View v) {
//		Intent infoIntent = new Intent(this, BasicInfoActivity.class);
//		startActivity(infoIntent);
//	}

	public void back(View v) {
		finish();
	}
}
