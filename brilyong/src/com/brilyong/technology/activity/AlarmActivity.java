package com.brilyong.technology.activity;

import java.util.ArrayList;

import org.json.JSONObject;

import junit.framework.Test;

import com.brilyong.technology.R;
import com.brilyong.technology.adapter.AlarmAdapter;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Alarm;
import com.brilyong.technology.entity.DevStuAlarmConfig;
import com.brilyong.technology.entity.GetDevStuAlarmEntityByDeviceIdResult;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.httputils.JSONHelper;
import com.brilyong.technology.view.NumericWheelAdapter;
import com.brilyong.technology.view.WheelView;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AlarmActivity extends BaseUIActivityUtil{
	private ListView alarm_list;
	private ArrayList<Alarm> list = new ArrayList<Alarm>();
	private TextView alarm_text_baocun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.alarm_activity);
    	initView();
    	new AlarmTask().execute();
    	//实现Item的监听
    	alarm_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				showTimeDailog(position);
			}
		});
    }
    	
    //初始化数据
    private void initView(){
    	alarm_list = (ListView)findViewById(R.id.alarm_listView);
    	alarm_text_baocun = (TextView)findViewById(R.id.alarm_text_baocun);
    	alarm_text_baocun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new SetTimeTask().execute();
			}
		});
    }
    
    //获取闹钟的数据
    private AlarmAdapter adapter;
    private class AlarmTask extends AsyncTask<Void, Void, GetDevStuAlarmEntityByDeviceIdResult>{
		@Override
		protected GetDevStuAlarmEntityByDeviceIdResult doInBackground(
				Void... params) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.GetDevStuAlarmEntityByDeviceId(Acount.getCurrentDevice().getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(GetDevStuAlarmEntityByDeviceIdResult result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null && !"".equals(result)){
				ArrayList<DevStuAlarmConfig> data = result.getData();
				if(data == null || "null".equals(data) || "".equals(data)){
					for(int i = 0;i<8;i++){
						Alarm alarm = new Alarm();
			    		alarm.setText_content("00" + ":" + "00");
			    		alarm.setAlarm_checked(true);
						list.add(alarm);
					}
				}else{
					for(int i = 0;i<data.size();i++){
						DevStuAlarmConfig fig = data.get(i);
						boolean s = fig.getIsOpen();
						String msg = fig.getHour();
						String msg_1 = fig.getMinute();
						Alarm alarm = new Alarm();
			    		alarm.setText_content(msg + ":" + msg_1);
			    		alarm.setAlarm_checked(s);
			    		list.add(alarm);
					}
				}
			}
			adapter = new AlarmAdapter(AlarmActivity.this, list);
			alarm_list.setAdapter(adapter);
		}
    }
    
 // 定时休眠对话框
    private Dialog dialog;
	private Alarm alarm; 
 	private void showTimeDailog(final int position) {
 	
 		View view = View.inflate(this, R.layout.novoice_dialog, null);
 		dialog = new Dialog(this, R.style.dw_dialog);
 		dialog.setContentView(view, new LinearLayout.LayoutParams(
 				LayoutParams.FILL_PARENT,
 				LayoutParams.FILL_PARENT));// 设置布局
 		TextView time_text = (TextView) view.findViewById(R.id.time_text);
 		time_text.setText("定时休眠");
 		final WheelView start_time = (WheelView) view
 				.findViewById(R.id.start_time);
 		start_time.setViewAdapter(new NumericWheelAdapter(this, 0, 23, "%02d"));
 		start_time.setCyclic(false);// 轮子循环
 		final WheelView entime_1 = (WheelView) view.findViewById(R.id.entime_1);
 		entime_1.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
 		entime_1.setCyclic(false);
 		final WheelView start_time2 = (WheelView) view
 				.findViewById(R.id.start_time2);
 		start_time2.setVisibility(View.GONE);
 		final WheelView entime_2 = (WheelView) view.findViewById(R.id.entime_2);
 		entime_2.setVisibility(View.GONE);
 		TextView text_1 = (TextView) view.findViewById(R.id.update_text_3);
 		text_1.setVisibility(View.GONE);
 		TextView update_text_2 = (TextView) view.findViewById(R.id.update_text_2);
 		update_text_2.setVisibility(View.GONE);
 		Button ok = (Button) view.findViewById(R.id.ok_set_time);
 		Button cancel = (Button) view.findViewById(R.id.cl_set_time);
 		alarm = new Alarm();
 		ok.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				int starttimes = start_time.getCurrentItem();
 				int start_minutes = entime_1.getCurrentItem();
 				String msg = starttimes + ":" + start_minutes;
 					switch (position) {
					case 0:
						update(position,msg);
						dialog.dismiss();
						break;
					case 1:
						update(position,msg);
						dialog.dismiss();
						break;
					case 2:
						update(position,msg);
						dialog.dismiss();
						break;
					case 3:
						update(position,msg);
						dialog.dismiss();
						break;
					case 4:
						update(position,msg);
						dialog.dismiss();
						break;
					case 5:
						update(position,msg);
						dialog.dismiss();
						break;
					case 6:
						update(position,msg);
						dialog.dismiss();
						break;
					case 7:
						update(position,msg);
						dialog.dismiss();
						break;

					default:
						break;
					}
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
 	
 	//更新适配器
 	private void update(int position,String text){
 		alarm.setText_content(text);
		alarm.setAlarm_checked(list.get(position).getAlarm_checked());
		list.set(position, alarm);
		adapter.notifyDataSetChanged();
 	}
 	
 	public void back(View v){
 		finish();
 	}
 	
 	//数据提交到后台
 	public class SetTimeTask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try {
				GetDevStuAlarmEntityByDeviceIdResult alarm_date = new GetDevStuAlarmEntityByDeviceIdResult();
				alarm_date.setDeviceId(Acount.getCurrentDevice().getId());
				ArrayList<DevStuAlarmConfig> data = new ArrayList<DevStuAlarmConfig>();
				for(int i = 0;i<list.size();i++){
					DevStuAlarmConfig config = new DevStuAlarmConfig();
					Alarm list_date = list.get(i);
					String text_content = list_date.getText_content();
					String[] text_content_1 = text_content.split(":");
					config.setIsOpen(list_date.getAlarm_checked());
					config.setHour(text_content_1[0]);
					config.setMinute(text_content_1[1]);
					data.add(config);
				}
				alarm_date.setDeviceId(Acount.getCurrentDevice().getId());
				alarm_date.setData(data);
				String id = Acount.getCurrentDevice().getId();
				return HttpHelperUtils.Dev_dingtong_SendAlarm(id,data);
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
			case 0:
				Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
				break;
			case -1:
				Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
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
}
