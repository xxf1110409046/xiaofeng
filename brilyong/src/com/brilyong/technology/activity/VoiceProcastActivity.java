package com.brilyong.technology.activity;

import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VoiceProcastActivity extends BaseUIActivityUtil{
	private EditText voice_prodcast;
	String voice_message;
	private final static int ZERO = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_broadcast);
		voice_prodcast = (EditText) findViewById(R.id.voice_prodcast);
	}
	
	public void back(View v){
		finish();
	}
	
	public void voice(View v){
		voice_message = voice_prodcast.getText().toString();
		VoiceProdcastTask task = new VoiceProdcastTask();
		task.execute();	
	}
	private class VoiceProdcastTask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.Dev_dingtong_send_text(Acount.getCurrentDevice().getId(), voice_message);
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
			case -1:
				Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
				break;
			case 0:
				Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();			
				break;
			case 1:
				Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
				voice_prodcast.setText("");
				hand.sendEmptyMessage(ZERO);
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
	private Handler hand = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ZERO:
			default:
				break;
			}
		}
		
	};
	
}
