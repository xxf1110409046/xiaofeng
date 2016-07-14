package com.brilyong.technology.activity;

import java.util.Calendar;
import java.util.Date;

import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.DataWa;
import com.brilyong.technology.entity.TimeSpan;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.NetworkUtils;
import com.brilyong.technology.view.NumericWheelAdapter;
import com.brilyong.technology.view.WheelView;
import com.brilyong.technology.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NoVoiceActivity extends BaseUIActivityUtil implements OnClickListener{
	private LinearLayout text_add;
	private LinearLayout text_add2;
	private LinearLayout add_noice;
	private TextView text_1;
	private TextView text_2;
	private CheckBox voice_check1;
	private CheckBox voice_check2;
	private CheckBox zhou_1;
	private CheckBox zhou_2;
	private CheckBox zhou_3;
	private CheckBox zhou_4;
	private CheckBox zhou_5;
	private CheckBox zhou_6;
	private CheckBox zhou_7;
	private boolean open_1;
	private boolean open_2;
	private boolean open_3;
	private boolean open_4;
	private boolean open_5;
	private boolean open_6;
	private boolean open_7;
	private boolean open = false;
	private boolean open2 = false;
	int date_1;
	int date_2;
	int date_3;
	int date_4;
	int date_5;
	int date_6;
	int date_7;
	private int count;
	private int number;
	private TimeSpan time;
	private TimeSpan time1;
	private int count_all;
	private DataWa data_time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.novoice_set);
	    initView();
	    new GetTimeTask().execute();
	    sp = getSharedPreferences("config", MODE_PRIVATE);
	    open_1 = sp.getBoolean("listener_zhou_1", false);
	    open_2 = sp.getBoolean("listener_zhou_2", false);
	    open_3 = sp.getBoolean("listener_zhou_3", false);
	    open_4 = sp.getBoolean("listener_zhou_4", false);
	    open_5 = sp.getBoolean("listener_zhou_5", false);
	    open_6 = sp.getBoolean("listener_zhou_6", false);
	    open_7 = sp.getBoolean("listener_zhou_7", false);  
	    zhou_1.setChecked(open_1);
	    zhou_2.setChecked(open_2);
	    zhou_3.setChecked(open_3);
	    zhou_4.setChecked(open_4);
	    zhou_5.setChecked(open_5);
	    zhou_6.setChecked(open_6);
	    zhou_7.setChecked(open_7);
	    time = new TimeSpan();
	    time1 = new TimeSpan();
	}
	private void initView(){
		add_noice = (LinearLayout) findViewById(R.id.add_noice);
		text_add = (LinearLayout) findViewById(R.id.text_add);
		text_add2 = (LinearLayout) findViewById(R.id.text_add2);
		voice_check1 =(CheckBox) findViewById(R.id.voice_check1);
		voice_check2 =(CheckBox) findViewById(R.id.voice_check2);
		zhou_1 = (CheckBox) findViewById(R.id.zhou_1);
		zhou_2 = (CheckBox) findViewById(R.id.zhou_2);
		zhou_3 = (CheckBox) findViewById(R.id.zhou_3);
		zhou_4 = (CheckBox) findViewById(R.id.zhou_4);
		zhou_5 = (CheckBox) findViewById(R.id.zhou_5);
		zhou_6 = (CheckBox) findViewById(R.id.zhou_6);
		zhou_7 = (CheckBox) findViewById(R.id.zhou_7);
		zhou_1.setOnCheckedChangeListener((listener_zhou_1));
		zhou_2.setOnCheckedChangeListener((listener_zhou_2));
		zhou_3.setOnCheckedChangeListener((listener_zhou_3));
		zhou_4.setOnCheckedChangeListener((listener_zhou_4));
		zhou_5.setOnCheckedChangeListener((listener_zhou_5));
		zhou_6.setOnCheckedChangeListener((listener_zhou_6));
		zhou_7.setOnCheckedChangeListener((listener_zhou_7));
		text_1 = (TextView) findViewById(R.id.text_1);
		text_2 = (TextView) findViewById(R.id.text_2);
		add_noice.setOnClickListener(this);
		text_add.setOnClickListener(this);
		text_add2.setOnClickListener(this);
	}
	//设置初始化数�?
	public void initTime(){
		
	}
			//周一
			OnCheckedChangeListener listener_zhou_1 = new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean selected) {
					// TODO Auto-generated method stub
					Editor edt = sp.edit();
					if(selected){	
						edt.putInt("date_1", 64);
						edt.putBoolean("listener_zhou_1", true);
						open_1 = true;
						
					}else{
						edt.putInt("date_1", 0);
						edt.putBoolean("listener_zhou_1", false);
						open_1 = false;
						
					}
					edt.commit();
				}
			};
		//周二
				OnCheckedChangeListener listener_zhou_2 = new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean selected) {
							// TODO Auto-generated method stub
							Editor edt = sp.edit();
							if(selected){	
								edt.putInt("date_2", 32);
								edt.putBoolean("listener_zhou_2", true);
								open_2 = true;
								
							}else{
								edt.putInt("date_1", 0);
								edt.putBoolean("listener_zhou_2", false);
								open_2 = false;
								
							}
							edt.commit();
						}
					};
					//周三
				OnCheckedChangeListener listener_zhou_3 = new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean selected) {
							// TODO Auto-generated method stub
							Editor edt = sp.edit();
							if(selected){	
								edt.putInt("date_3", 16);
								edt.putBoolean("listener_zhou_3", true);
								open_3 = true;
								
							}else{
								edt.putInt("date_3", 0);
								edt.putBoolean("listener_zhou_3", false);
								open_3 = false;
								
							}
							edt.commit();
						}
					};
					//周四
				OnCheckedChangeListener listener_zhou_4 = new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean selected) {
							// TODO Auto-generated method stub
							Editor edt = sp.edit();
							if(selected){	
								edt.putInt("date_4", 8);
								edt.putBoolean("listener_zhou_4", true);
								open_4 = true;
								
							}else{
								edt.putInt("date_4", 0);
								edt.putBoolean("listener_zhou_4", false);
								open_4 = false;
								
							}
							edt.commit();
						}
					};
					//周五
				OnCheckedChangeListener listener_zhou_5 = new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean selected) {
							// TODO Auto-generated method stub
							Editor edt = sp.edit();
							if(selected){	
								edt.putInt("date_5", 4);
								edt.putBoolean("listener_zhou_5", true);
								open_5 = true;
								
							}else{
								edt.putInt("date_5", 0);
								edt.putBoolean("listener_zhou_5", false);
								open_5 = false;
								
							}
							edt.commit();
						}
					};
					//周六
				OnCheckedChangeListener listener_zhou_6 = new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean selected) {
							// TODO Auto-generated method stub
							Editor edt = sp.edit();
							if(selected){	
								edt.putInt("date_6", 2);
								edt.putBoolean("listener_zhou_6", true);
								open_6 = true;
							
							}else{
								edt.putInt("date_6", 0);
								edt.putBoolean("listener_zhou_6", false);
								open_6 = false;
								
							}
							edt.commit();
						}
					};
					//周日
				OnCheckedChangeListener listener_zhou_7 = new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean selected) {
							// TODO Auto-generated method stub
							Editor edt = sp.edit();
							if(selected){	
								edt.putInt("date_7", 1);
								edt.putBoolean("listener_zhou_7", true);
								open_7 = true;						
							}else{
								edt.putInt("date_7", 0);
								edt.putBoolean("listener_zhou_7", false);
								open_7 = false;							
							}
							edt.commit();
						}
					};
		
		OnCheckedChangeListener listener = new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean selected) {
				// TODO Auto-generated method stub
				Editor edt = sp.edit();
				if(selected){		
					edt.putBoolean("voice_check1", true);
					open = true;
					new VoiceAsynTask().execute();
				}else{
					edt.putBoolean("voice_check1", false);
					open = false;
					new VoiceAsynTask().execute();
				}
				edt.commit();
			}
		};
	OnCheckedChangeListener listener1 = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean selected) {
			// TODO Auto-generated method stub
			Editor edt = sp.edit();
			if(selected){		
				edt.putBoolean("voice_check2", true);
				open2 = true;		
			}else{
				edt.putBoolean("voice_check2", false);
				open2 = false;
			}
			edt.commit();
		}
	};
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.text_add:
//			Toast.makeText(NoVoiceActivity.this, "qo dian ji", 0).show();
			showTimeDailog(text_1); 
			break;
		case R.id.text_add2:
			showTimeDailog(text_2);
			break;
		case R.id.add_noice:		
			VoiceAsynTask voiceAsynTask= new VoiceAsynTask();
			voiceAsynTask.execute();
			break;
		default:
			break;
		}
	}

	private SharedPreferences sp;
	private Dialog dialog;
	private Date start;
	private Date end;
	private TextView text_time;
	private void showTimeDailog(final TextView textView) {
		
		View view = View
					.inflate(this, R.layout.novoice_dialog, null);

		dialog = new Dialog(this, R.style.dw_dialog);
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 设置布局

		final WheelView start_time = (WheelView) view
				.findViewById(R.id.start_time);
		start_time.setViewAdapter(new NumericWheelAdapter(this, 0, 23, "%02d"));
		start_time.setCyclic(false);// 轮子循环
		final WheelView entime_1 = (WheelView) view.findViewById(R.id.entime_1);
		entime_1.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		entime_1.setCyclic(false);
		final WheelView start_time2 = (WheelView) view
				.findViewById(R.id.start_time2);
		start_time2.setViewAdapter(new NumericWheelAdapter(this, 0, 23, "%02d"));
		start_time2.setCyclic(false);// 轮子循环
		final WheelView entime_2 = (WheelView) view.findViewById(R.id.entime_2);
		entime_2.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		entime_2.setCyclic(false);
		Button ok = (Button) view.findViewById(R.id.ok_set_time);
		Button cancel = (Button) view.findViewById(R.id.cl_set_time);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editor ed = sp.edit();
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);// 获得年份
				int month = calendar.get(Calendar.MONTH) + 1;// 获取月份
				int day = calendar.get(Calendar.DAY_OF_MONTH);// 获取月份
				int hour = calendar.get(Calendar.HOUR_OF_DAY);// 获取月份
				int starttimes = start_time.getCurrentItem();
				int endtime = entime_1.getCurrentItem();
				int starttimes2 = start_time2.getCurrentItem();
				int endtime2 = entime_2.getCurrentItem();
				String start = starttimes + "";
				String text =  starttimes + ":" + endtime + "~" +  starttimes2
						+ ":" + endtime2;
				textView.setText(text);
				if(textView.equals(text_1)){
					time.setStartHour(starttimes);
					time.setStartMinute(endtime);
					time.setEndHour(starttimes2);
					time.setEndMinute(endtime2);
				}else if(textView.equals(text_2)){
					time1.setStartHour(starttimes);
					time1.setStartMinute(endtime);
					time1.setEndHour(starttimes2);
					time1.setEndMinute(endtime2);	
				}			
				ed.commit();
				dialog.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	@SuppressWarnings("unused")
	public void back(View v){
		finish();
	}
	
	private class VoiceAsynTask extends AsyncTask<Void, Void, Integer>{
		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
			    if(Acount.getCurrentDevice().getTypeId() == 1){
			    	DataWa data = new DataWa();
					data.setDeviceId(Acount.getCurrentDevice().getId());
					data.setRepeat(count);
				    data.setStartHour1(time.getStartHour());
				    data.setStartMinute1(time.getStartMinute());
				    data.setEndHour1(time.getEndHour());
				    data.setEndMinute1(time.getEndMinute());
				    data.setStartHour2(time1.getStartHour());
				    data.setStartMinute2(time1.getStartMinute());
				    data.setEndHour2(time1.getEndHour());
				    data.setEndMinute2(time1.getEndMinute());
				    data.setIsOpen1(open);
				    data.setIsOpen2(true);
				    return HttpHelperUtils.Dev_dingtong_SchoolTime(data);
			    }else{
			    	return HttpHelperUtils.DevHunterSilentTime(Acount.getCurrentDevice().getId(),time,time1,count);
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
			if(result != null){
				switch (result) {
				case -1:
					Toast.makeText(NoVoiceActivity.this, getString(R.string.set_false), 0).show();
					break;
				case 0:
					Toast.makeText(NoVoiceActivity.this, getString(R.string.set_false), 0).show();			
					break;
				case 1:
					Toast.makeText(NoVoiceActivity.this, getString(R.string.set_true), 0).show();
					break;
				case -10:
					quitToLogin();
					break;
				case -11:
					equipmentOffLine();
				
				default:
					break;
				}
			}else{
				if(!NetworkUtils.isNetworkConnected(NoVoiceActivity.this)){
					Toast.makeText(NoVoiceActivity.this, R.string.please_check_internet, 0).show();
				}
			}
			
		}
		
	}
	
	//获取静音时段的数�?
	private class GetTimeTask extends AsyncTask<Void, Void, DataWa>{
		@Override
		protected DataWa doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				data_time = HttpHelperUtils.GetMuteTimeByDeviceId(Acount.getCurrentDevice().getId());
				return data_time;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(DataWa result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null && !"null".equals(result)){
				String time_1 =  result.getStartHour1() 
						+ ":" + result.getStartMinute1() + "~" + result.getEndHour1() 
						+ ":" + result.getEndMinute1();
				String time_2 =  result.getStartHour2() 
						+ ":" + result.getStartMinute2() + "~" + result.getEndHour2() 
						+ ":" + result.getEndMinute2();
				text_1.setText(time_1);
				text_2.setText(time_2);
				voice_check1.setChecked(result.getIsOpen1());
				open = result.getIsOpen1();
				time.setStartHour(result.getStartHour1());
				time.setStartMinute(result.getStartMinute1());
				time.setEndHour(result.getEndHour1());
				time.setEndMinute(result.getEndMinute1());
				time1.setStartHour(result.getStartHour2());
				time1.setStartMinute(result.getStartMinute2());
				time1.setEndHour(result.getEndHour2());
				time1.setEndMinute(result.getEndMinute2());
			
			}else{
				if(!NetworkUtils.isNetworkConnected(NoVoiceActivity.this)){
					Toast.makeText(NoVoiceActivity.this, R.string.please_check_internet, 0).show();
				}
			}
			voice_check1.setOnCheckedChangeListener(listener);
			voice_check2.setOnCheckedChangeListener(listener1);
		}
	}
}
