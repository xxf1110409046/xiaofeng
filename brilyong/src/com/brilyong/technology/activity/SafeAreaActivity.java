package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.brilyong.technology.adapter.SafeAdapter;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Geofence;
import com.brilyong.technology.entity.GeofenceWrap;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.R;


/**
 * 安全区域
 * 
 * @author Administrator
 * 
 */
public class SafeAreaActivity extends BaseUIActivityUtil {
	private ArrayList<Geofence> list;
	private ListView lsitview;
	private SafeAdapter adpater;
	private GeofenceWrap wrap;
	private String safeId;
	private int positions;
	private int PageIndex = 1;
	private int PageSize = 7;
	private TextView progress_dialog_message;
	private Dialog dialogs;
//	private MyRervice rervice;
	private TextView nodata;
	public static Activity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.safe_area_activity);
		initDialog();
		if(list != null && list.size() > 0){
			list.clear();
		}
		ChaTask query = new ChaTask();
		query.execute();
		lsitview = (ListView) findViewById(R.id.listview);
		nodata = (TextView) findViewById(R.id.tv_nodata);
		lsitview.setOnItemClickListener(listener);
		lsitview.setOnScrollListener(mOnScrollListener);
		lsitview.setOnItemLongClickListener(longlistener);
//		registerreceiver();
	}

	// 注册广播
//	private void registerreceiver() {
//		rervice = new MyRervice();
//		IntentFilter filter = new IntentFilter();
//		filter.addAction("com.safe.add");
//		registerReceiver(rervice, filter);
//	}

	// 用于查询所有电子围栏的广播
//	private class MyRervice extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			PageIndex = 1;
//			if (list.size() == PageSize * PageIndex) {
//				++PageIndex;
//				new ChaTask().execute();
//				dialogs.show();
//			} else {
//				new ChaTask().execute();
//				dialogs.show();
//			}
//			Toast.makeText(getApplicationContext(), "啊", 0).show();
//		}

//	}

	// 初始化对话框
	private void initDialog() {
		View view = View.inflate(this, R.layout.progress_daogin, null);
		progress_dialog_message = (TextView) view
				.findViewById(R.id.progress_dialog_message);
		progress_dialog_message.setText(getString(R.string.is_loading));
		dialogs = new Dialog(this, R.style.dw_dialog);
		dialogs.setCanceledOnTouchOutside(true);
		dialogs.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 设置布局
		dialogs.show();
	}

	// listview 长按事件
	OnItemLongClickListener longlistener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				final int position, long id) {
			AlertDialog.Builder builder = new Builder(SafeAreaActivity.this);
			builder.setTitle(getString(R.string.delete_area));
			builder.setMessage(getString(R.string.or_delete_area));
			builder.setPositiveButton(getString(R.string.delete_area), new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					positions = position;
					safeId = list.get(position).getId();
					DeleteTask delete = new DeleteTask();
					delete.execute();
					dialogs.show();
				}
			});
			builder.create().show();
			return false;
		}
	};
	// listview 点击事件
	OnItemClickListener listener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Geofence mGeofence = list.get(position);
			Intent intent = new Intent(SafeAreaActivity.this,
					AddSafeActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("Geofence", mGeofence);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
	// listview滑动监听
	OnScrollListener mOnScrollListener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				int posint = view.getLastVisiblePosition();
				if (posint == list.size() - 1) {
					if (list.size() == PageSize * PageIndex) {
						++PageIndex;
						ChaTask query = new ChaTask();
						query.execute();
						dialogs.show();
					}
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	};

	// 删除电子围栏
	private class DeleteTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				return HttpHelperUtils.DeleteGeofenceById(safeId);
			} catch (ConnectException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 1) {
				list.remove(positions);
				adpater.notifyDataSetChanged();
			} else if (result == 0) {
				Toast.makeText(getApplicationContext(), getString(R.string.operate_fasle),
						Toast.LENGTH_SHORT).show();
			}
			if (list.size() == 0) {
				nodata.setVisibility(View.VISIBLE);
				lsitview.setVisibility(View.GONE);
			} else {
				nodata.setVisibility(View.GONE);
				lsitview.setVisibility(View.VISIBLE);
			}
			dialogs.dismiss();
			super.onPostExecute(result);
		}

	}

	// 异步获取所有安全区
	private class ChaTask extends AsyncTask<Void, Void, GeofenceWrap> {
		@Override
		protected GeofenceWrap doInBackground(Void... params) {
			try {
				return HttpHelperUtils.GetGeofenceByDeviceId(Acount
						.getCurrentDevice().getId(), PageSize, PageIndex);
			} catch (ConnectException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(GeofenceWrap wrap) {
			if (wrap != null) {
				if (list == null) {
					list = wrap.getRows();
				} else if (list.size() < PageSize) {
					list.clear();
					list.addAll(wrap.getRows());
				} else {
					list.addAll(wrap.getRows());
				}
				if (adpater == null) {
					adpater = new SafeAdapter(SafeAreaActivity.this, list);
					lsitview.setAdapter(adpater);
				} else {
					adpater.notifyDataSetChanged();
				}
			}
			if (list.size() == 0) {
				nodata.setVisibility(View.VISIBLE);
				lsitview.setVisibility(View.GONE);
			} else {
				nodata.setVisibility(View.GONE);
				lsitview.setVisibility(View.VISIBLE);
			}
			dialogs.dismiss();
			super.onPostExecute(wrap);
		}
	}

	@Override
	protected void onDestroy() {
//		unregisterReceiver(rervice);
		super.onDestroy();
	}

	public void addSafe(View v) {
		Intent intent = new Intent(this, AddSafeActivity.class);
		startActivity(intent);
	}

	public void back(View v) {
		finish();
	}
}
