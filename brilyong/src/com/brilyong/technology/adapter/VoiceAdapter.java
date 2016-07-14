package com.brilyong.technology.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brilyong.technology.entity.Message;
import com.brilyong.technology.R;

public class VoiceAdapter extends BaseAdapter {

	private final int TYPE_RECEIVER_VOICE = 0;
	private final int TYPE_SEND_VOICE = 1;
	private ArrayList<Message> list;
	public LayoutInflater mInflater;
	private Context context;

	public VoiceAdapter(ArrayList<Message> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
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

	public void add(Message msg) {
		list.add(msg);
		notifyDataSetChanged();
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	private View createViewByType(Message message, int position) {
		return getItemViewType(position) == TYPE_RECEIVER_VOICE ? mInflater
				.inflate(R.layout.dioatt_left, null) : mInflater.inflate(
				R.layout.dioatt_right, null);
	}

	@Override
	public int getItemViewType(int position) {
		int send = list.get(position).getAgre_stat();
		if (send == -100) {
			return TYPE_SEND_VOICE;
		} else {
			return TYPE_RECEIVER_VOICE;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message item = list.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = createViewByType(item, position);
			holder = new ViewHolder();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_time = (TextView) convertView.findViewById(R.id.tv_sendtime);
		SimpleDateFormat dateformat1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date a = item.getAddDate();
		holder.tv_time.setText(dateformat1.format(a));
		return convertView;
	}
	class ViewHolder {
		TextView tv_time;
	}

}
