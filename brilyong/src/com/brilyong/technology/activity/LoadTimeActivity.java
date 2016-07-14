package com.brilyong.technology.activity;

import com.brilyong.technology.R;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.httputils.HttpHelperUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoadTimeActivity extends BaseUIActivityUtil{
	private EditText tv_load_time;
    private String load_text;
	private int text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_time);
		tv_load_time = (EditText) findViewById(R.id.tv_load_time);
	}
	
	public void Send(View view){
		if(TextUtils.isEmpty(tv_load_time.getText())){
			Toast.makeText(this, "请输入时间", Toast.LENGTH_SHORT).show();
		}else{
			load_text = tv_load_time.getText().toString();
			text = Integer.parseInt(load_text);
			if(text < 15){
				Toast.makeText(this, "输入时间需要大于15秒", Toast.LENGTH_SHORT).show();
			}else{
				new LoadTimeTask().execute();
			}
		}
		
	}
	
	private class LoadTimeTask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.Dev_dingtong_SettingInterval(Acount.getCurrentDevice().getId(), text);
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
			if(result != null){
				switch (result.intValue()) {
				case -1:
					Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
					break;
				case 0:
					Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();			
					break;
				case 1:
					Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
					break;
				default:
					break;
				}
			}
		}
	
		
	}
	
	
	
}
