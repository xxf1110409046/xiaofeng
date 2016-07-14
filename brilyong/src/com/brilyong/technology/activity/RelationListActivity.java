package com.brilyong.technology.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.brilyong.technology.R;
import com.brilyong.technology.adapter.RelationListAdapter;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Data;
import com.brilyong.technology.entity.FamilyConfig;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.httputils.JSONHelper;
import com.brilyong.technology.utils.DialogUtils;
import com.google.gson.JsonObject;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class RelationListActivity extends BaseUIActivityUtil{

	private ListView relation_number_list;
	private LinearLayout relation_add_button;
	private Dialog dialog;
	private int relattion_position;
	private ArrayList<FamilyConfig> relation_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		DialogUtils.ShowWaitDialog(this);
		setContentView(R.layout.relation_type_list);
		relation_number_list = (ListView) findViewById(R.id.relation_number_list);
		relation_number_list.setOnItemLongClickListener(listener);
		relation_add_button = (LinearLayout) findViewById(R.id.relation_add_button);
		GetRelationTask task = new GetRelationTask();
		task.execute();
//		new GetRelationTask().execute();
		relation_add_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RelationListActivity.this,AddRelationShipActivity.class);
				startActivity(intent);	
			}
		});
	}
	
	//长按删除亲情号码
	OnItemLongClickListener listener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			relattion_position = position;
			dialog = DialogUtils.initRelationDialogView(RelationListActivity.this, phoneListener);
			dialog.show();
			return false;
		}
		
	};
	
	OnClickListener phoneListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.phone_no:
				if(dialog.isShowing()){
					dialog.dismiss();
				}
				break;
			case R.id.phone_ok:
				if(dialog.isShowing()){
					dialog.dismiss();
				}
				new AddRelationTask().execute();
				break;
			default:
				break;
			}
		}
	};
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(Acount.getUnbindState()){
			GetRelationTask task_relation = new GetRelationTask();
			task_relation.execute();
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Acount.setUnbindState(false);
	}
	//获取
	private class GetRelationTask extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.GetXdtFamilyNumbers(Acount.getCurrentDevice().getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null ){
				
				try {
					JSONObject obj = new JSONObject(result);
					String data = obj.getString("Data");
					Data datas = JSONHelper.parseObject(data, Data.class);
					relation_list = new ArrayList<FamilyConfig>();
					ArrayList<FamilyConfig> list = datas.getFamilyList();
					for(int i = 0;i<list.size();i++){
						FamilyConfig family = list.get(i);
						if(!TextUtils.isEmpty(family.getPhone())){
							relation_list.add(family);
						}
					}
					RelationListAdapter adapter = new RelationListAdapter(RelationListActivity.this,relation_list);
					relation_number_list.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				ArrayList<FamilyConfig> list = table.getRelation_list();
			}
		}
	}
	
	//删除
	  private class AddRelationTask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.XdtSettingFamilyNumber(Acount.getCurrentDevice().getId(),"",relation_list.get(relattion_position).getIndex());
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
			if(result != null){
				switch (result.intValue()) {
				case -1:
					Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
					break;
				case 0:
					Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();			
					break;
				case 1:
					Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
					GetRelationTask task = new GetRelationTask();
					task.execute();
					break;
				case -10:
					quitToLogin();			
					break;
				case -11:
					equipmentOffLine();
					break;
				default:
					break;
				}
			}
		}
	  }
}
