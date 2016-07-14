package com.brilyong.technology.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brilyong.technology.entity.Device;
import com.brilyong.technology.R;


public class BabyAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<Device> listDevice;

	public BabyAdapter(Context context, ArrayList<Device> listDevice) {
		super();
		this.context = context;
		this.listDevice = listDevice;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listDevice.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.baby_list_item, null);
			holder.icon_baby = (ImageView) convertView
					.findViewById(R.id.icon_baby);
			holder.baby_name = (TextView) convertView
					.findViewById(R.id.baby_name);
			holder.baby_electricity = (ImageView) convertView
					.findViewById(R.id.baby_electricity);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Device aDevice = listDevice.get(position);
		int batt = aDevice.getBattery() / 20;
		holder.baby_name.setText(aDevice.getName());
		if (batt <= 1) {
			holder.baby_electricity.setImageResource(R.drawable.bl_electricity1);
		} else if (batt <= 2) {
			holder.baby_electricity.setImageResource(R.drawable.bl_electricity2);
		} else if (batt <= 3) {
			holder.baby_electricity.setImageResource(R.drawable.bl_electricity3);
		} else if (batt <= 4) {
			holder.baby_electricity.setImageResource(R.drawable.bl_electricity4);
		} else if (batt <= 7) {
			holder.baby_electricity.setImageResource(R.drawable.bl_electricity5);
		}
		return convertView;
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
	class ViewHolder {
		ImageView baby_electricity;
		ImageView icon_baby;
		TextView baby_name;
	}
}
