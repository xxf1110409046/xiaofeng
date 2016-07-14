package com.brilyong.technology.adapter;

import java.util.ArrayList;


import com.brilyong.technology.adapter.BabyAdapter.ViewHolder;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.entity.DeviceAttention;
import com.brilyong.technology.R;


import android.R.string;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DearNumberAdapter extends BaseAdapter {
	private String admin = Acount.getCurrentDevice().getAdmin();
	private Context context;

	private ArrayList<DeviceAttention> list;

	public DearNumberAdapter(Context context, ArrayList<DeviceAttention> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.dear_number_list_item,
					null);
			holder.user_head_image = (ImageView) convertView
					.findViewById(R.id.user_head_image);
			holder.item_name = (TextView) convertView
					.findViewById(R.id.item_name);
			holder.item_phone = (TextView) convertView
					.findViewById(R.id.item_phone);
			holder.tv_admin = (TextView) convertView
					.findViewById(R.id.tv_admin);
			holder.phone_number = (ImageView) convertView
					.findViewById(R.id.phone_number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		DeviceAttention atttention = list.get(position);
		if (atttention.isIsAdmin()) {
			holder.item_name.setText("Admin");
			holder.item_phone.setText(admin);
			holder.tv_admin.setText(context.getString(R.string.administer));
			holder.phone_number.setBackgroundResource(R.drawable.bl_phone_number_1);
		} else{
			if(atttention.getRelationship()==1){
				holder.user_head_image.setBackgroundResource(R.drawable.bl_father);
				holder.item_name.setText(R.string.father);
				holder.phone_number.setBackgroundResource(R.drawable.bl_phone_number_1);
			}
			if(atttention.getRelationship()==2){
				holder.user_head_image.setBackgroundResource(R.drawable.bl_mather);
				holder.item_name.setText(R.string.mather);
				holder.phone_number.setBackgroundResource(R.drawable.bl_phone_number_3);
			if(atttention.getRelationship()==3){
				holder.user_head_image.setBackgroundResource(R.drawable.bl_grandfa);
				holder.item_name.setText(R.string.grandfa);
				holder.phone_number.setBackgroundResource(R.drawable.bl_phone_number_4);
			}
			if(atttention.getRelationship()==4){
				holder.user_head_image.setBackgroundResource(R.drawable.bl_grandmather);
				holder.item_name.setText(R.string.grandma);
				holder.phone_number.setBackgroundResource(R.drawable.bl_phone_number_5);
			}
			if(atttention.getRelationship()==5){
				holder.user_head_image.setBackgroundResource(R.drawable.bl_stranger);
				holder.item_name.setText(R.string.stranger);
				holder.phone_number.setBackgroundResource(R.drawable.bl_phone_number_6);
			}
		}
		}
		holder.item_phone.setText(atttention.getUserId());
		return convertView;
	}

	class ViewHolder {
		ImageView user_head_image;
		TextView item_name;
		TextView item_phone;
		TextView tv_admin;
		ImageView phone_number;
	}

}
