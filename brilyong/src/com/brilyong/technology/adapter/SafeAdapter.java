package com.brilyong.technology.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brilyong.technology.entity.Geofence;
import com.brilyong.technology.R;


public class SafeAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Geofence> list;

	public SafeAdapter(Context context, ArrayList<Geofence> list) {
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
			convertView = View.inflate(context, R.layout.safe_area_list_item,
					null);
			holder.img_safe = (ImageView) convertView
					.findViewById(R.id.img_safe);
			holder.Address = (TextView) convertView.findViewById(R.id.address);
			holder.safename = (TextView) convertView
					.findViewById(R.id.safename);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Geofence mGeofence = list.get(position);
		switch (mGeofence.getType()) {
		case 0:
			holder.Address.setText(R.string.exit_fencing_call);
			break;
		case 1:
			holder.Address.setText(R.string.leave_fencing_call);
			break;
		case 2:
			holder.Address.setText(R.string.exit_leave_fengcing_call);
			break;

		default:
			break;
		}
		holder.safename.setText(mGeofence.getName());
		return convertView;
	}

	class ViewHolder {
		ImageView img_safe;
		TextView Address;
		TextView safename;
	}

}
