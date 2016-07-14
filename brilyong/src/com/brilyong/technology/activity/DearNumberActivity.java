package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brilyong.technology.adapter.DearNumberAdapter;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.entity.DeviceAttention;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.R;


/**
 * 亲情号码薄
 * 
 * @author Administrator
 * 
 */
public class DearNumberActivity extends BaseUIActivityUtil implements
		OnItemClickListener, OnClickListener {

	private AlertDialog dailog;
	private Device aDevice;// 当前设备
	private ListView attation_number_list;
	private SharedPreferences sp;
	private String loginName;// 当前用户
	private String admin;
	private AlertDialog dialog;
	private ArrayList<DeviceAttention> list;
	private DearNumberAdapter adapter;
	private Intent intent;
	LinearLayout ll_add_number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attation_number);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		attation_number_list = (ListView) findViewById(R.id.attation_number_list);
		ChaTask cha = new ChaTask();
		cha.execute();
		loginName = sp.getString("LoginName", null);
		aDevice = Acount.getCurrentDevice();
		
//		Toast.makeText(getApplicationContext(), "sd" + aDevice.getId(), 0).show();
		attation_number_list.setOnItemClickListener(this);
	}
	
	// 添加情亲号码和邀请关注对话框
	public void addNmber(View v) {	
		AlertDialog.Builder builder = new Builder(this);
		final View view = View.inflate(this, R.layout.add_number_dialog, null);
		LinearLayout ll_yaoqing = (LinearLayout) view
				.findViewById(R.id.ll_yaoqing);
		ll_add_number = (LinearLayout) view
				.findViewById(R.id.ll_add_number);
		ll_yaoqing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DearNumberActivity.this,
						InviteAttentionActivity.class);
				startActivity(intent);
				dailog.dismiss();
			}
		});
		ll_add_number.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ll_add_number.setVisibility(View.VISIBLE);
				Intent intent = new Intent(DearNumberActivity.this,
						AddContactActivity.class);
				startActivity(intent);
				dailog.dismiss();
			}
		});
		builder.setView(view);
		dailog = builder.create();
		dailog.show();
	}

	public void back(View v) {
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (!(loginName.equals(aDevice.getAdmin()))) {
			Toast.makeText(this, getString(R.string.is_not_equipment_admin), Toast.LENGTH_SHORT).show();
			return;
		} else {
//			shownNumberDailog(list.get(position));
			
		}
	}


	// 设置情亲号码
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_set_number1:// 情亲号码1

			break;
		case R.id.ll_set_number2:// 情亲号码2

			break;
		case R.id.ycsh:// 远程守护

			break;
		case R.id.ll_delete:// 删除
			dialog.dismiss();
			DeleteTask delete = new DeleteTask();
			delete.execute();
			break;

		default:
			break;
		}
	}

	// 获取设备关注者
	private class ChaTask extends
			AsyncTask<Void, Void, ArrayList<DeviceAttention>> {

		@Override
		protected ArrayList<DeviceAttention> doInBackground(Void... params) {
			try {
				return HttpHelperUtils.GetDeviceAttention(Acount
						.getCurrentDevice().getId());
			} catch (ConnectException e) {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<DeviceAttention> result) {
			if (result != null) {
				list = result;
				if (list.size() != 0) {
					adapter = new DearNumberAdapter(DearNumberActivity.this,
							list);
					attation_number_list.setAdapter(adapter);
//					Toast.makeText(getApplicationContext(), "wo" + list.size(), 0).show();
				}
			}
			super.onPostExecute(result);
		}

	}

	private class DeleteTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				HttpHelperUtils.UnBindDevice("15574849004", Acount
						.getCurrentDevice().getId());
			} catch (ConnectException e) {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}
}
