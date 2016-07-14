package com.brilyong.technology.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.BabyMessage;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.Utils;
import com.brilyong.technology.view.NumericWheelAdapter;
import com.brilyong.technology.view.WheelView;
import com.brilyong.technology.R;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateBabyDetailActivity extends BaseUIActivityUtil {
	
	private EditText update_baby_names;
	private TextView update_spiner_birthday,update_baby_phone;
	private TextView baby_name_title;
	private EditText update_spiner_weight;
	private EditText update_spiner_shengao;
	private Spinner update_spiner_relation;
	private Button update_remove_bind;
	private RadioGroup update_radiogroup;
	private RadioButton update_nan;
	private RadioButton update_nv;
	private ImageView baby_update_image;
	private int relationship;
	private int sex;
	private ImageView update_image;
	String[] update_relationship = {"爸爸","妈妈","爷爷","奶奶","陌生人"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_baby_detail);
		initView();
		initSpinerRelation();
		update_radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case R.id.update_nan:
					sex = 1;
					break;
				case R.id.update_nv:
					sex = 0;
					break;
				default:
					break;
				}
		
			}
		});
		
		update_remove_bind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getDate();
			}
		});
		update_spiner_birthday.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showTimeDailog();
			}
		});
	}
	
	private void initView(){
		update_radiogroup = (RadioGroup) findViewById(R.id.update_radiogroup);
		update_nan = (RadioButton) findViewById(R.id.update_nan);
		update_nv = (RadioButton) findViewById(R.id.update_nv);
		update_baby_names = (EditText) findViewById(R.id.update_baby_names);
		update_baby_phone = (EditText) findViewById(R.id.update_baby_phone);
		update_spiner_birthday = (TextView) findViewById(R.id.update_spiner_birthday);
		baby_name_title = (TextView) findViewById(R.id.baby_name_title);
		update_spiner_weight = (EditText) findViewById(R.id.update_spiner_weight);
		update_spiner_relation = (Spinner) findViewById(R.id.update_spiner_relation);
		update_remove_bind = (Button) findViewById(R.id.update_remove_bind);
		update_spiner_shengao = (EditText) findViewById(R.id.update_spiner_shengao);
		update_image = (ImageView) findViewById(R.id.update_image);
		baby_update_image = (ImageView) findViewById(R.id.baby_update_image);
		new GetBabyTask().execute();
	}
	
	private class GetBabyTask extends AsyncTask<Void, Void, BabyMessage>{

		@Override
		protected BabyMessage doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.GetDeviceBaInfo(Acount.getCurrentDevice().getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(BabyMessage result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null){
				update_baby_names.setText(result.getName());
				if(result.getSex() == 0){
//					update_radiogroup.getChildAt(0).setSelected(true);
					update_nv.setChecked(true);
					baby_update_image.setBackgroundResource(R.drawable.bl_baby_nv);
				}else{
//					update_radiogroup.getChildAt(1).setSelected(true);
					update_nan.setChecked(true);
					baby_update_image.setBackgroundResource(R.drawable.bl_baby_nan);
				}
				update_spiner_birthday.setText(com.brilyong.technology.utils.Utils.getFormatDate(result.getBirthday()) + "");
				update_spiner_weight.setText(result.getWeight() + "");
				update_spiner_shengao.setText(result.getHeight() + "");
				update_baby_phone.setText(Acount.getCurrentDevice().getSimPhone());
				update_spiner_relation.setSelection(result.getRelationship(), true);
				date = Utils.getDate(update_spiner_birthday.getText().toString());
				switch (result.getRelationship()) {
				case 0:
					update_image.setBackgroundResource(R.drawable.bl_father);
					break;
				case 1:
					update_image.setBackgroundResource(R.drawable.bl_mather);			
					break;
				case 2:
					update_image.setBackgroundResource(R.drawable.bl_grandfa);
					break;
				case 3:
					update_image.setBackgroundResource(R.drawable.bl_grandmather);
					break;
				case 4:
					update_image.setBackgroundResource(R.drawable.bl_stranger);
					break;
				default:
					break;
				}
			}		
			}
			
	}
	
	private void initSpinerRelation(){		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, update_relationship);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		update_spiner_relation.setAdapter(adapter);
		update_spiner_relation.setOnItemSelectedListener(new SimpleSpinerRelation());
		update_spiner_relation.setVisibility(View.VISIBLE);
	}
	
	class SimpleSpinerRelation implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			relationship = arg2;
			hand.sendEmptyMessage(arg2);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private SharedPreferences sp;
	private Dialog dialog;
	private Date start;
	private Date end;
	private TextView text_time;
	private Date date;
	private void showTimeDailog() {
		View view = View.inflate(this, R.layout.novoice_dialog, null);
		dialog = new Dialog(this, R.style.dw_dialog);
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));// 设置布局
		TextView text_1 = (TextView) view.findViewById(R.id.update_text_1);
		text_1.setVisibility(view.GONE);
		TextView text_2 = (TextView) view.findViewById(R.id.update_text_2);
		text_2.setVisibility(view.GONE);
		TextView text_3 = (TextView) view.findViewById(R.id.update_text_3);
		text_3.setVisibility(view.GONE);
		final WheelView start_time = (WheelView) view
				.findViewById(R.id.start_time);
		final NumericWheelAdapter s = new NumericWheelAdapter(this, 1991, 2015, "%02d");
		start_time.setViewAdapter(s);
		start_time.setCyclic(false);// 轮子循环
		final WheelView entime_1 = (WheelView) view.findViewById(R.id.entime_1);
		entime_1.setVisibility(view.GONE);
//		entime_1.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
//		entime_1.setCyclic(false);
		final WheelView start_time2 = (WheelView) view
				.findViewById(R.id.start_time2);
		start_time2.setViewAdapter(new NumericWheelAdapter(this, 1, 12, "%02d"));
		start_time2.setCyclic(false);// 轮子循环
		final WheelView entime_2 = (WheelView) view.findViewById(R.id.entime_2);
		entime_2.setViewAdapter(new NumericWheelAdapter(this, 1, 31, "%02d"));
		entime_2.setCyclic(false);
		Button ok = (Button) view.findViewById(R.id.ok_set_time);
		Button cancel = (Button) view.findViewById(R.id.cl_set_time);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int starttimes = start_time.getCurrentItem();
				String ss = (String) s.getItemText(starttimes);
//				int endtime = entime_1.getCurrentItem();
				int starttimes2 = start_time2.getCurrentItem() + 1;
				int endtime2 = entime_2.getCurrentItem() + 1;
				String start = starttimes + "";
				String text =  ss + "-" +  starttimes2 + ""
						+ "-" + endtime2 + "";
				update_spiner_birthday.setText(text);
			    date = Utils.getDate(update_spiner_birthday.getText().toString());
				dialog.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private void getDate(){
		
		new GetBabyDetailtask().execute();
	}
	
	//得到宝贝信息详情	
	private String update_baby;
	private class GetBabyDetailtask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				String baby_names = update_baby_names.getText().toString();
				String spiner_weight = update_spiner_weight.getText().toString();
				double weight = Double.valueOf(spiner_weight);
				String spiner_shengao = update_spiner_shengao.getText().toString();
				double shengao = Double.valueOf(spiner_shengao);
				update_baby = update_baby_phone.getText().toString();
				return HttpHelperUtils.UpdateDeviceDetailInfo(Acount.getCurrentDevice().getId(),baby_names,relationship,weight,shengao,date,sex,update_baby);
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
			case 0:
				Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
				break;
			case -1:
				Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
				Acount.getCurrentDevice().setSimPhone(update_baby);
				Intent intent = new Intent(UpdateBabyDetailActivity.this,BybeDetailsActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
				
	}
	
	private Handler hand = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 0:
				update_image.setBackgroundResource(R.drawable.bl_father);
				break;
			case 1:
				update_image.setBackgroundResource(R.drawable.bl_mather);			
				break;
			case 2:
				update_image.setBackgroundResource(R.drawable.bl_grandfa);
				break;
			case 3:
				update_image.setBackgroundResource(R.drawable.bl_grandmather);
				break;
			case 4:
				update_image.setBackgroundResource(R.drawable.bl_stranger);
				break;
		default:
			break;
		}
		};
	};
	
}
