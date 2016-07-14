package com.brilyong.technology.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.brilyong.technology.adapter.ListViewAdapter;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.BodyData;
import com.brilyong.technology.entity.SchoolData;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.view.AutoListView;
import com.brilyong.technology.view.AutoListView.OnLoadListener;
import com.brilyong.technology.view.AutoListView.OnRefreshListener;
import com.brilyong.technology.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;



/**
 * @author SunnyCoffee
 * @date 2014-1-28
 * @version 1.0
 * @desc listview下拉刷新，上拉自动加载更多�? http�?/blog.csdn.com/limb99
 */

public class TestActivity extends BaseUIActivityUtil implements OnRefreshListener,
		OnLoadListener {
	
	private BodyData body;
	private AutoListView lstv;
	private ListViewAdapter adapter;
	private static int msgIndex = 0;
	private List<SchoolData> result;
	private List<SchoolData> list = new ArrayList<SchoolData>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			result = (List<SchoolData>) msg.obj;
			switch (msg.what) {
			case AutoListView.REFRESH:
				lstv.onRefreshComplete();
				list.clear();
				list.addAll(result);
				break;
			case AutoListView.LOAD:
				lstv.onLoadComplete();
				list.addAll(result);
				break;
			}
			lstv.setResultSize(result.size());
			msgIndex = list.size();
			adapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_call_activity);
		
		lstv = (AutoListView) findViewById(R.id.lstv);
		adapter = new ListViewAdapter(this, list);
		lstv.setAdapter(adapter);
		lstv.setOnRefreshListener(this);
		lstv.setOnLoadListener(this);
		initData();
		lstv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "wo lai le", 0).show();
			}
		});
	}

	private void initData() {
		body = new BodyData();
		body.setDeviceId(Acount.getCurrentDevice().getId());
		body.setMsgIndex(msgIndex);
		body.setPageSize(10);
		loadData(AutoListView.REFRESH);
	}

	private void loadData(final int what) {
		// 这里模拟从服务器获取数据
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				Message msg = handler.obtainMessage();
				msg.what = what;
				try {
					
					msg.obj = HttpHelperUtils.GetRFIDRecord(body);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			}
		}).start();
	}

	@Override
	public void onRefresh() {
		msgIndex = 0;
		body.setMsgIndex(msgIndex);
		loadData(AutoListView.REFRESH);
	}

	@Override
	public void onLoad() {
		body = new BodyData();
		body.setDeviceId(Acount.getCurrentDevice().getId());
		body.setMsgIndex(msgIndex);
		body.setPageSize(10);
		loadData(AutoListView.LOAD);
	}

	public void back(View v){
		finish();
	}
	
}
