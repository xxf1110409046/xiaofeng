package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.zip.Inflater;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.brilyong.technology.R;
import com.brilyong.technology.adapter.DearNumberAdapter;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.entity.DeviceAttention;
import com.brilyong.technology.httputils.HttpHelperUtils;


/**
 * 亲情号码薄
 * 
 * @author Administrator
 * 
 */
public class AddAttationManActivity extends BaseUIActivityUtil implements
		OnItemClickListener {

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
	private DeviceAttention atttention;
	private int position;
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
		
		attation_number_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if (!(loginName.equals(aDevice.getAdmin()))) {
					Toast.makeText(AddAttationManActivity.this, getString(R.string.is_not_equipment_admin), Toast.LENGTH_SHORT).show();
					return;
				} else {
//					shownNumberDailog(list.get(position));
					atttention = list.get(position);
					if(atttention.getUserId().equals(loginName)){
						Toast.makeText(AddAttationManActivity.this, getString(R.string.not_to_delete_admin), Toast.LENGTH_SHORT).show();	
					}else{
						setDeleteDialog(atttention.getUserId(),position);	
					}
					
				}
			}
		});
	}
	
	// 添加情亲号码和邀请关注对话框
	public void addNmber(View v) {	
		Intent intent = new Intent(AddAttationManActivity.this,
		InviteAttentionActivity.class);
		startActivity(intent);
	}

	public void back(View v) {
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
	
	//删除对话框
	private Dialog dialog_delete;
	public void setDeleteDialog(String msg,final int positon){
		View view = View.inflate(AddAttationManActivity.this, R.layout.add_device_dialog, null);
		dialog_delete = new Dialog(AddAttationManActivity.this, R.style.dw_dialog);
		dialog_delete.setContentView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		dialog_delete.setCancelable(true);
		TextView main_tital = (TextView) view.findViewById(R.id.main_tital);
		main_tital.setText(getString(R.string.delete_follwers));
		TextView main_content = (TextView) view.findViewById(R.id.main_content);
		main_content.setText(getString(R.string.or_to_delete_phone) + msg);
		Button add_device_no = (Button) view.findViewById(R.id.add_device_no);
		Button add_device_ok = (Button) view.findViewById(R.id.add_device_ok);
		
		add_device_no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_delete.dismiss();
			}
		});
		
		add_device_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_delete.dismiss();
				position = positon;
				new AdeviceAttationtask().execute();
			}
		});
		dialog_delete.show();
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
					adapter = new DearNumberAdapter(AddAttationManActivity.this,
							list);
					attation_number_list.setAdapter(adapter);
//					Toast.makeText(getApplicationContext(), "wo" + list.size(), 0).show();
				}
			}
			super.onPostExecute(result);
		}

	}
	
	private class AdeviceAttationtask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.DeleteDeviceAttention(atttention.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			switch (result) {
			case -1:
				Toast.makeText(getApplicationContext(), getString(R.string.delete_false), 0).show();
				break;
			case 0:
				Toast.makeText(getApplicationContext(), getString(R.string.delete_false), 0).show();
				break;
			case 1:
				list.remove(position);
				adapter.notifyDataSetChanged();
				break;
			case -10:
				quitToLogin();
				break;
			default:
				break;
			}
		}
	}
}
