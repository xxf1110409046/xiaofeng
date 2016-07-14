package com.brilyong.technology.activity;


import com.brilyong.technology.R;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.httputils.HttpHelperUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;



public class AvoidDropActivity extends Activity{
	private CheckBox fall_out;
	private boolean get_fall_state;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avoid_drop_activity);
		fall_out = (CheckBox) findViewById(R.id.fall_out);
		new GetFalltask().execute();
//		fall_out.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				// TODO Auto-generated method stub
//				if(isChecked){
//					get_fall_state = true;
//					new SetFallTask().execute();
//				}else{
//					get_fall_state = false;
//					new SetFallTask().execute();
//				}
//			}
//		});
	}
	@SuppressWarnings("unused")
	public void back(View v){
		finish();
	}
	
	//»ñÈ¡ÍÑÂä¾¯±¨µÄ×´Ì¬
	private class GetFalltask extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				 return HttpHelperUtils.GetXDingtongFallStat(Acount.getCurrentDevice().getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				fall_out.setChecked(result);
				fall_out.setOnCheckedChangeListener(listener);
			} else {
//				if (!NetworkUtils
//						.isNetworkConnected(ApplicationSettingActivity.this)) {
//					Toast.makeText(ApplicationSettingActivity.this,
//							R.string.check_internet, 0).show();
//				}
			}

		}
	}
	
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			if (isChecked) {
				get_fall_state = true;
				new SetFallTask().execute();
			} else {
				get_fall_state = false;
				new SetFallTask().execute();
			}
		}
	};
	
	//±£´æÍÑÂä¾¯±¨µÄ×´Ì¬
	private class SetFallTask extends AsyncTask<Void, Void, Integer>{
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.UpdateXDingtongFallStat(Acount.getCurrentDevice().getId(),get_fall_state);
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
			}else {

			}
		}
	}
}
