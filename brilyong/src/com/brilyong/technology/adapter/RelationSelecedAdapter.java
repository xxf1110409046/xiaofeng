package com.brilyong.technology.adapter;

import java.util.ArrayList;

import com.brilyong.technology.R;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;





public class RelationSelecedAdapter extends BaseAdapter{
	
	//包含关系的集合
	private ArrayList<String> relationList;
	private String relationShip;
	private Context context;
	private Boolean relationShipType = false;
	private SharedPreferences share;
	public RelationSelecedAdapter(Context context) {
		// TODO Auto-generated constructor stub
		super();   
		this.context = context;
		relationList = new ArrayList<String>();
		for(int i = 0;i < 10;i++){
			relationShip = String.valueOf(i);
			relationList.add(relationShip);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return relationList.size();
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.relation_seleced_list, null);
			holder.relation_type_image = (ImageView) convertView.findViewById(R.id.relation_type_image);
			holder.relation_type_text = (TextView) convertView.findViewById(R.id.relation_type_text);
			holder.relation_selected = (ImageView) convertView.findViewById(R.id.relation_selected);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(relationShipType){
			share = context.getSharedPreferences("config", context.MODE_PRIVATE);
			int typePosition = share.getInt("relationType", 0);
			if(typePosition == position){
				holder.relation_selected.setVisibility(View.VISIBLE);
			}else{
				holder.relation_selected.setVisibility(View.GONE);
			}
		}
		//判断关系类型
		switch (position) {
		case 0:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_0);
			holder.relation_type_text.setText(context.getString(R.string.sos));
			break;
		case 1:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_1);
			holder.relation_type_text.setText(context.getString(R.string.father));
			break;
		case 2:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_2);
			holder.relation_type_text.setText(context.getString(R.string.mather));
			break;
		case 3:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_3);
			holder.relation_type_text.setText(context.getString(R.string.grandfa));
			break;
		case 4:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_4);
			holder.relation_type_text.setText(context.getString(R.string.grandma));
			break;
		case 5:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_5);
			holder.relation_type_text.setText(context.getString(R.string.grandfater));
			break;
		case 6:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_6);
			holder.relation_type_text.setText(context.getString(R.string.grandmather));
			break;
		case 7:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_7);
			holder.relation_type_text.setText(context.getString(R.string.uncle));
			break;
		case 8:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_8);
			holder.relation_type_text.setText(context.getString(R.string.aunty));
			break;
		case 9:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_9);
			holder.relation_type_text.setText(context.getString(R.string.brother));
			break;
		case 10:
			holder.relation_type_image.setBackgroundResource(R.drawable.rela_type_10);
			holder.relation_type_text.setText(context.getString(R.string.sister));
			break;
		default:
			break;
		}
		return convertView;
	}
    
	public class ViewHolder{
		private ImageView relation_type_image;
		private TextView relation_type_text;
		private ImageView relation_selected;
	}
	
	public void hideRelationTypeSelectedState(){
		relationShipType = false;
	}
	
	public void showRelationTypeSelectedState(){
		relationShipType = true;
		notifyDataSetChanged();
	}
}
