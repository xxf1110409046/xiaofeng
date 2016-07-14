package com.brilyong.technology.utils;

import java.net.ConnectException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.brilyong.technology.activity.AddEquipmentActivity;
import com.brilyong.technology.activity.HomeBaiduActivity;
import com.brilyong.technology.adapter.BabyAdapter;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.R;


/**
 * PopupWindow 工具类
 * 
 * @author Administrator
 * 
 */
public abstract class PopupWindowUtils {

	private Context context;
	private ArrayList<Device> listDevice;
	private SharedPreferences sp;
	private int mScreenWidth;
	private View popupWindow;
	private PopupWindow mPopupWindow;
	private int mScreenHeight;

	public PopupWindowUtils(Context context, ArrayList<Device> listDevice,
			SharedPreferences sp, int mScreenWidth, int mScreenHeight) {
		super();
		this.context = context;
		this.listDevice = listDevice;
		this.sp = sp;
		this.mScreenWidth = mScreenWidth;
		this.mScreenHeight = mScreenHeight;
	}

	public interface CurrentDevice {
		/**
		 * 设置当前设备名称
		 * 
		 * @param pre
		 *            当前进度
		 */
		public void onSetName(String name, String Battery);
	}

	public PopupWindow getPopupWindow(final CurrentDevice cd) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		popupWindow = layoutInflater
				.inflate(R.layout.popup_window_shebei, null);
		mPopupWindow = new PopupWindow(popupWindow, mScreenWidth, mScreenHeight/2);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context
				.getResources()));
		ListView list_baby = (ListView) popupWindow
				.findViewById(R.id.list_baby);
		RelativeLayout add_baby = (RelativeLayout) popupWindow
				.findViewById(R.id.add_baby);
		AllTasks task = new AllTasks();
		task.execute();
		listDevice = Acount.getDevices();
		Log.v("TAG", "hujiaoxiaobao：" + listDevice.size());
		if (listDevice != null) {
			BabyAdapter adapter = new BabyAdapter(context, listDevice);
			list_baby.setAdapter(adapter);
		}
		list_baby.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Acount.setCurrentDevice(listDevice.get(position));
				Editor ed = sp.edit();
				ed.putInt("id", position);
				ed.commit();
				cd.onSetName(listDevice.get(position).getName(), listDevice
						.get(position).getBattery() + "%");
				mPopupWindow.dismiss();
				initBaiMap();
			}
		});
		add_baby.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AddEquipmentActivity.class);
				context.startActivity(intent);
				mPopupWindow.dismiss();
			}
		});
		return mPopupWindow;

	}

	public abstract void initBaiMap();
	
	/**
	 * 异步获取用户下所有数据
	 * @author Administrator
	 * 
	 */
	private class AllTasks extends AsyncTask<String, Void, ArrayList<Device>> {
		@Override
		protected ArrayList<Device> doInBackground(String... params) {
			String loginName = sp.getString("LoginName", null);
			try {
				ArrayList<Device> listDevice = HttpHelperUtils
						  .GetDeviceListByLoginName(loginName);
				return listDevice;
			} catch (ConnectException e) {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Device> result) {
			if (result != null) {
				Acount.setDevices(result);
				Log.v("TAG", "告诉我：" + listDevice.size());
			}
		}
	}
}
