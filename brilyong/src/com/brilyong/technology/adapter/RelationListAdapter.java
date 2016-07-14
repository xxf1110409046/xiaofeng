package com.brilyong.technology.adapter;

import java.util.ArrayList;

import com.brilyong.technology.R;
import com.brilyong.technology.entity.FamilyConfig;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RelationListAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<FamilyConfig> list;
	public RelationListAdapter(Context context,ArrayList<FamilyConfig> list) {
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
	public Object getItem(int arg0) {
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
		// TODO Auto-generated method stub
		ViewHolder holder; 
		if (convertView == null) {
			holder = new ViewHolder();
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
		FamilyConfig relation_list = list.get(position);
			switch (relation_list.getIndex()) {
			case 0:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_0);
				holder.item_name.setText(context.getString(R.string.sos));
				break;
			case 1:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_1);
				holder.item_name.setText(context.getString(R.string.father));
				break;
			case 2:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_2);
				holder.item_name.setText(context.getString(R.string.mather));
				break;
			case 3:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_3);
				holder.item_name.setText(context.getString(R.string.grandfa));
				break;
			case 4:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_4);
				holder.item_name.setText(context.getString(R.string.grandma));
				break;
			case 5:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_5);
				holder.item_name.setText(context.getString(R.string.grandfater));
				break;
			case 6:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_6);
				holder.item_name.setText(context.getString(R.string.grandmather));
				break;
			case 7:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_7);
				holder.item_name.setText(context.getString(R.string.uncle));
				break;
			case 8:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_8);
				holder.item_name.setText(context.getString(R.string.aunty));
				break;
			case 9:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_9);
				holder.item_name.setText(context.getString(R.string.brother));
				break;
			case 10:
				holder.user_head_image.setBackgroundResource(R.drawable.rela_type_10);
				holder.item_name.setText(context.getString(R.string.sister));
				break;
			default:
				break;
		}
		holder.item_phone.setText(relation_list.getPhone());
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
