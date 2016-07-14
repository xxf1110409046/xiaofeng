package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.util.ArrayList;

import android.R.menu;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.brilyong.technology.R;
import com.brilyong.technology.activity.AddEquipmentActivity;
import com.brilyong.technology.activity.HomeBaiduActivity;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.BabyMessage;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.DialogUtils;
import com.brilyong.technology.utils.NetworkUtils;
import com.brilyong.technology.utils.Utils;
import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;



/**
 * 设备详情界面
 * 
 * @author Administrator
 * 
 */
public class BybeDetailsActivity extends BaseUIActivityUtil implements OnClickListener{
//	private LinearLayout user_message;
	private TextView baby_names;
	private TextView baby_sex;
	private TextView baby_birthday;
	private TextView baby_wigth;
	private TextView baby_height;
	private TextView baby_phone;
	private TextView baby_parent_phone;
	private TextView baby_relationship;
	private TextView title_text_name;
	private Button remove_bind;
	private Button create_code;
	private SharedPreferences sp;
	private String loginName;
	private LinearLayout update_babys;
	private ImageView image_relation;
	private ImageView baby_detail_image;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.usermessage);	
			initView();
			sp = getSharedPreferences("config", MODE_PRIVATE);
			loginName = sp.getString("LoginName", null);
//			user_message.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent(BybeDetailsActivity.this,CreateQrCodeActivity.class);
//					startActivity(intent);
//				}
//			});
			
		}
		private void initView(){
//			user_message = (LinearLayout)findViewById(R.id.user_message);
			baby_names = (TextView) findViewById(R.id.baby_names);
			baby_sex = (TextView) findViewById(R.id.baby_sex);
			baby_birthday = (TextView) findViewById(R.id.baby_birthday);
			baby_wigth = (TextView) findViewById(R.id.baby_wigth);
			baby_height = (TextView) findViewById(R.id.baby_height);
			baby_parent_phone = (TextView) findViewById(R.id.baby_parent_phone);
			baby_phone = (TextView) findViewById(R.id.baby_phone);
			title_text_name = (TextView) findViewById(R.id.title_text_name);
			baby_relationship = (TextView) findViewById(R.id.baby_relationship);
			remove_bind = (Button) findViewById(R.id.remove_bind);
			create_code = (Button) findViewById(R.id.create_code);
			update_babys = (LinearLayout) findViewById(R.id.update_babys);
			image_relation = (ImageView) findViewById(R.id.image_relation);
			baby_detail_image = (ImageView) findViewById(R.id.baby_detail_image);
			update_babys.setOnClickListener(this);
			create_code.setOnClickListener(this);
			remove_bind.setOnClickListener(this);
		}
		
		
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			GetBabyTask task = new GetBabyTask();
			task.execute();
		}
		private final static int FATHER = 0;
		private final static int MATHER = 1;
		private final static int GRANDFA = 2;
		private final static int GRANDMA = 3;
		private final static int GRANDFATHER = 4;
		private final static int GRAMDERMATHER = 5;
		private final static int STRANGER = 6;
		

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
					baby_names.setText(result.getName());
					if(result.getSex() == 0){
						baby_sex.setText(getString(R.string.nv));
						baby_detail_image.setBackgroundResource(R.drawable.bl_baby_nv);
					}else{
						baby_sex.setText(getString(R.string.nan));
						baby_detail_image.setBackgroundResource(R.drawable.bl_baby_nan);
					}
					
					baby_birthday.setText(Utils.getFormatDate(result.getBirthday()) + "");
					baby_wigth.setText(result.getWeight() + "");
					baby_height.setText(result.getHeight() + "");
					baby_parent_phone.setText(getString(R.string.local_number) + Acount.getCurrentDevice().getSimPhone());
					baby_phone.setText("IMEI：" + Acount.getCurrentDevice().getId());
					String name = getString(R.string.is_babys);
					switch (result.getRelationship()) {
						case FATHER:
							baby_relationship.setText(name + getString(R.string.father));
							hand.sendEmptyMessage(FATHER);
							break;
						case MATHER:
							baby_relationship.setText(name + getString(R.string.mather));
							hand.sendEmptyMessage(MATHER);
							break;
						case GRANDFA:
							baby_relationship.setText(name + getString(R.string.grandfa));
							hand.sendEmptyMessage(GRANDFA);
							break;
						case GRANDMA:
							baby_relationship.setText(name + getString(R.string.grandma));
							hand.sendEmptyMessage(GRANDMA);
							break;
						case GRANDFATHER:
							baby_relationship.setText(name + getString(R.string.stranger));
							hand.sendEmptyMessage(STRANGER);
							break;
					default:
						break;
					}
				}
				
			}
			
			
		}
		public void back(View v) {
			finish();
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.remove_bind:
				showRecoverFactory();
				break;
			case R.id.create_code:
				showCreateCode();
				break;
			case R.id.update_babys:
				Intent intent = new Intent(this, UpdateBabyDetailActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}


		private AlertDialog dialog_factory;
		private AlertDialog dialog_create_code;
		private void showRecoverFactory(){
			View view = View.inflate(this, R.layout.add_device_dialog, null);
			TextView main_tital = (TextView)view.findViewById(R.id.main_tital);
			TextView main_content = (TextView)view.findViewById(R.id.main_content);
			Button btn_recover = (Button)view.findViewById(R.id.add_device_ok);
			Button btn_fctory = (Button)view.findViewById(R.id.add_device_no);
			main_tital.setText(getString(R.string.delete_bind));
			main_content.setText(getString(R.string.main_content));
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(view);
			dialog_factory = builder.create();
			dialog_factory.show();
			btn_recover.setOnClickListener(new OnClickListener() {		
				@Override
				public void onClick(View arg0) {
					UnBindTask task = new UnBindTask();
					task.execute();
					dialog_factory.dismiss();
				}
			});
			btn_fctory.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dialog_factory.dismiss();
				}
			});
		}
		
		private Dialog dialog;
		private void showCreateCode(){
			View view = View.inflate(this, R.layout.createcode, null);
			dialog = new Dialog(this, R.style.dw_dialog);
			ImageView image_code = (ImageView) view.findViewById(R.id.image_code);
			Button btn_delete = (Button) view.findViewById(R.id.btn_delete);
			dialog.setCancelable(false);
			try {
				image_code.setImageBitmap(EncodingHandler.createQRCode(Acount.getCurrentDevice().getId(), 500));
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT));
			dialog.show();
			btn_delete.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
		}
		
		private class UnBindTask extends AsyncTask<Void, Void, Integer> {

			@Override
			protected Integer doInBackground(Void... params) {
				try {
					return HttpHelperUtils.UnBindDevice(loginName, Acount
							.getCurrentDevice().getId());
				} catch (ConnectException e) {
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Integer result) {
				super.onPostExecute(result);
				if (result != null) {
					switch (result) {
					case 0:
						Toast.makeText(getApplicationContext(), getString(R.string.operate_fasle), 0).show();
						break;
					case -1:
						Toast.makeText(getApplicationContext(), getString(R.string.equipment_not_exist), 0).show();
						break;
					case -10:
						quitToLogin();
						break;
					case 1:
						new AllTask().execute();
						Editor ed = sp.edit();
						ed.putInt("id", 0);
						ed.commit();
						break;
					default:
						break;
					}
				}
			}
		} 
		
		/**
		 * 异步获取用户下所有数据
		 * @author Administrator
		 * 
		 */
		private class AllTask extends AsyncTask<String, Void, ArrayList<Device>> {
			@Override
			protected ArrayList<Device> doInBackground(String... params) {
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
				}
				Intent intent = new Intent(BybeDetailsActivity.this,HomeBaiduActivity.class);
				startActivity(intent);	
			}
		}
		
		private Handler hand = new Handler(){
			public void handleMessage(android.os.Message msg) {
				
				switch (msg.what) {
				case 0:
					image_relation.setBackgroundResource(R.drawable.bl_father);
					break;
				case 1:
					image_relation.setBackgroundResource(R.drawable.bl_mather);			
					break;
				case 2:
					image_relation.setBackgroundResource(R.drawable.bl_grandfa);
					break;
				case 3:
					image_relation.setBackgroundResource(R.drawable.bl_grandmather);
					break;
				case 4:
					image_relation.setBackgroundResource(R.drawable.bl_stranger);
					break;
			default:
				break;
			}
			};
		};
}
