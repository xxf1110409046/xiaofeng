package com.brilyong.technology.adapter;



import java.util.ArrayList;

import com.brilyong.technology.entity.Alarm;
import com.brilyong.technology.R;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<Alarm> list;
	
	
	
	public AlarmAdapter(Context context,ArrayList<Alarm> list) {
	// TODO Auto-generated constructor stub
		super();
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if(convertView == null){
			convertView = View.inflate(context, R.layout.alarms_activity, null);
			holder.alarm_image =  (ImageView) convertView.findViewById(R.id.alarm_image);
			holder.alarm_text_1 = (TextView) convertView.findViewById(R.id.alarm_text_1);
			holder.alarm_text_2 = (TextView) convertView.findViewById(R.id.alarm_text_2);
			holder.alarm_check_1 = (CheckBox) convertView.findViewById(R.id.alarm_check_1);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
//		final Alarm fig = list.get(position);  
		
		int s = position + 1;
		final Alarm alarm = list.get(position);
		holder.alarm_text_1.setText(context.getString(R.string.alarm) + s);	
		holder.alarm_text_2.setText(alarm.getText_content());
		holder.alarm_check_1.setChecked(alarm.getAlarm_checked());
		Log.v("TAG", alarm.getAlarm_checked() + "");
		holder.alarm_check_1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				if(isChecked){
					
//					alarm.setText_content(alarm.getText_content());
//					alarm.setAlarm_checked(isChecked);	
//				}else{
//					alarm.setText_content(alarm.getText_content());
//					alarm.setAlarm_checked(false);
//				}
				if(alarm.getAlarm_checked()){
					alarm.setAlarm_checked(false);
				}else{
					alarm.setAlarm_checked(true);
				}
				list.set(position, alarm);
				Log.v("TAG", alarm.getAlarm_checked() + "wo shi ge zei" + position);
				notifyDataSetChanged();
			}
		});
		// TODO Auto-generated method stub
		return convertView;
	}
	
	private static class ViewHolder{
		ImageView alarm_image;
		TextView alarm_text_1;
		TextView alarm_text_2;
		CheckBox alarm_check_1;
	}

}
