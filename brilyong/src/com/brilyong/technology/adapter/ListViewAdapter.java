package com.brilyong.technology.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.brilyong.technology.entity.SchoolData;
import com.brilyong.technology.R;



import android.content.Context;

import android.provider.ContactsContract.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author SunnyCoffee
 * @date 2014-2-2
 * @version 1.0
 * @desc 适配�? * 
 */
public class ListViewAdapter extends BaseAdapter {

	private ViewHolder holder;
	private List<SchoolData> list;
	private Context context;
	private final static int TYPE_1 = 1;
	private final static int TYPE_2 = 2;
	private final static int TYPE_3 = 3;
	private final static int TYPE_4 = 4;

	public ListViewAdapter(Context context, List<SchoolData> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.school_call_adapter, null);
			
			holder.image_school = (ImageView) convertView.findViewById(R.id.image_school);
			holder.text_exit = (TextView) convertView.findViewById(R.id.text_exit);
			holder.text_leave = (TextView) convertView.findViewById(R.id.text_leave);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();	
		}
		SchoolData school = list.get(position);
		Date data = school.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String school_time = df.format(data);
		switch (school.getType()) {
		case TYPE_1:
			holder.image_school.setImageResource(R.drawable.bl_school_exist);
			holder.text_exit.setText(R.string.exit_school);
			holder.text_leave.setText(school_time);
			break;
		case TYPE_2:
			holder.image_school.setImageResource(R.drawable.bl_school_leave);
			holder.text_exit.setText(R.string.leave_school);
			holder.text_leave.setText(school_time);
			break;
		case TYPE_3:
			holder.image_school.setImageResource(R.drawable.bl_sahngche);
			holder.text_exit.setText(R.string.take_bus_time);
			holder.text_leave.setText(school_time);
			break;
		case TYPE_4:
			holder.image_school.setImageResource(R.drawable.bl_xiache);
			holder.text_exit.setText(R.string.get_off_bus);
			holder.text_leave.setText(school_time);
			break;
		default:
			break;
		}
		
		return convertView;
	}

	private static class ViewHolder {
		ImageView image_school;
		TextView text_exit;
		TextView text_leave;
	}

}
