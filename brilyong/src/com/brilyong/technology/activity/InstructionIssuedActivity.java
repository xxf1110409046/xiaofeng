package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.entity.Dormancy;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.service.BLEService;
import com.brilyong.technology.utils.DialogUtils;
import com.brilyong.technology.view.NumericWheelAdapter;
import com.brilyong.technology.view.WheelView;
import com.brilyong.technology.R;

/**
 * 指令下发界面
 * 
 * @author Administrator
 * 
 */
public class InstructionIssuedActivity extends BaseUIActivityUtil implements
		OnClickListener {
	private int WorkMode;
	private CheckBox cb_call;
	private CheckBox check_blue,check_box_jingyin;
	private boolean isOpen;
	private Date start;
	private Date end;
	private Dialog dialogs;
	private AlertDialog dialog_factory;
	private boolean open_blue;
	private Dormancy time = new Dormancy();
	private String deviceId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instruction_issued_activity);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		admin = Acount.getCurrentDevice().getAdmin();
		deviceId = Acount.getCurrentDevice().getId();
		loginName = sp.getString("LoginName", null);
		isOpen = sp.getBoolean("isOpen", false);
		open_blue = Acount.getCurrentDevice().getDevDingtongConfig().Bluetooth;
		text_time = (TextView) findViewById(R.id.text_time);		
		cb_call = (CheckBox) findViewById(R.id.cb_call);
		check_blue = (CheckBox) findViewById(R.id.check_blue);
		check_box_jingyin = (CheckBox) findViewById(R.id.check_box_jingyin);
		check_blue.setChecked(open_blue);
		check_blue.setOnCheckedChangeListener(listener_blue);
		cb_call.setChecked(isOpen);
		cb_call.setOnTouchListener(listener);
		setMuteTime();
		hintDialog = DialogUtils.getDialog(this, getString(R.string.is_setting));
		initDialog();	

	}
	
	public void setOnclick(View v){
		switch (v.getId()) {
		case R.id.yuanchen:
			Intent msgIntent = new Intent(InstructionIssuedActivity.this, PhoneCallActivity.class);
			startActivity(msgIntent);
			break;
		case R.id.dinwei:
			showLocatingFrequency();
			break;
		case R.id.jibuqi:
			Intent intent_send = new Intent(InstructionIssuedActivity.this,PedometerTaskSend.class);
			startActivity(intent_send);
			break;
		case R.id.tiem_xiu:
			showTimeDailog();
			break;
		case R.id.message_center:
			Intent msgIntent1 = new Intent(InstructionIssuedActivity.this, NoVoiceActivity.class);
			startActivity(msgIntent1);
			break;
		case R.id.recover_set:
			showRecoverFactory();	
			break;
		case R.id.voice_procast:
			Intent intent = new Intent(InstructionIssuedActivity.this,VoiceProcastActivity.class);
			startActivity(intent);
			break;
		case R.id.xiafa_naozhong:
			Intent intent_alarm = new Intent(InstructionIssuedActivity.this,AlarmActivity.class);
			startActivity(intent_alarm);
			break;	
		case R.id.set_load_time:
			Intent intent_load = new Intent(InstructionIssuedActivity.this,LoadTimeActivity.class);
			startActivity(intent_load);
			break;
		default:
			break;
		}
	}
	private void initDialog() {
		View view = View.inflate(this, R.layout.progress_daogin, null);
		TextView progress_dialog_message = (TextView) view
				.findViewById(R.id.progress_dialog_message);
		progress_dialog_message.setText(getString(R.string.is_loading));
		dialogs = new Dialog(this, R.style.dw_dialog);
		dialogs.setCanceledOnTouchOutside(false);
		dialogs.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 设置布局
	}

	private int pyte;
	private RelativeLayout tiem_xiu;
	private TextView text_time;
	private SharedPreferences sp;
	private Dialog dialog;
	private AlertDialog dailogs;
	private String admin;
	private String set_time;
	OnCheckedChangeListener listener_blue = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
			// TODO Auto-generated method stub
			Editor ed = sp.edit();
			switch (arg0.getId()) {
			case R.id.check_box_jingyin:
				if(isChecked){
					time.setIsOpen(true);
					set_time = "1" + "," + text_time.getText().toString();
					new TimeTask().execute();
				}else{
					time.setIsOpen(false);
					set_time = "0" + "," + text_time.getText().toString();
					new TimeTask().execute();
				}
				break;
			case R.id.check_blue:
			if(isChecked){
				ed.putBoolean("open_blue", true);
				open_blue = true;
				BlueToothAsyntask blueTooth = new BlueToothAsyntask();
				blueTooth.execute();
			}else{
				ed.putBoolean("open_blue", false);
				open_blue = false;
				BlueToothAsyntask blueTooth1 = new BlueToothAsyntask();
				blueTooth1.execute();
			}
			ed.commit();
				break;
			default:
				break;
			}
		}
	};

	OnTouchListener listener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (MotionEvent.ACTION_DOWN == event.getAction()) {
				Editor ed = sp.edit();
				if(Acount.getCurrentDevice().getTypeId() == 1){
					if (isOpen) {
						cb_call.setChecked(false);
						isOpen = false;
						ed.putBoolean("isOpen", false);
						ed.commit();
						CallTask calltask = new CallTask();
						calltask.execute();
					} else {
						cb_call.setChecked(true);
						isOpen = true;
						ed.putBoolean("isOpen", true);
						ed.commit();
						CallTask calltask = new CallTask();
						calltask.execute();
					}
					hintDialog.show();
				}else{
					if (!(loginName.equals(admin))) {
						Toast.makeText(getApplicationContext(), getString(R.string.is_not_equipment_admin),
								Toast.LENGTH_SHORT).show();
						cb_call.setClickable(false);
					} else {
						if (isOpen) {
							cb_call.setChecked(false);
							isOpen = false;
							ed.putBoolean("isOpen", false);
							ed.commit();
							CallTask calltask = new CallTask();
							calltask.execute();
						} else {
							cb_call.setChecked(true);
							isOpen = true;
							ed.putBoolean("isOpen", true);
							ed.commit();
							CallTask calltask = new CallTask();
							calltask.execute();
						}
						hintDialog.show();
					}	
				}		
			}
			return true;
		}
	};
	private Dialog hintDialog;
	private String loginName;

	// 来电防火�?
	private class CallTask extends AsyncTask<Void, Void, Integer> {
		@Override
		protected Integer doInBackground(Void... params) {
			try {
				if(Acount.getCurrentDevice().getTypeId() == 1){
					return HttpHelperUtils.Dev_Dingtong_FirewallSetting(Acount
							.getCurrentDevice().getId(),isOpen);
				}else{
					return HttpHelperUtils.DevHunterFirwallSetting(isOpen, Acount
							.getCurrentDevice().getId());
				}
			} catch (ConnectException e) {
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result != null) {
				if (result == -1) {
					Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0)
							.show();
				} else if (result == 0) {
					Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0).show();

				} else if (result == 1) {
					Toast.makeText(getApplicationContext(), getString(R.string.send_true), 0)
							.show();
				}
			}
			hintDialog.dismiss();
			super.onPostExecute(result);
		}
	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.jibuqi:
//			if (!(loginName.equals(admin))) {
//				Toast.makeText(this, "您不是设备管理员，不能进行操�?, Toast.LENGTH_SHORT)
//						.show();
//				return;
//			}
//			showDailog();
//			break;
//		case R.id.tiem_xiu:
//			if (!(loginName.equals(admin))) {
//				Toast.makeText(this, "您不是设备管理员，不能进行操�?, Toast.LENGTH_SHORT)
//						.show();
//				return;
//			}
//			showTimeDailog();
//			break;
//		
//		case R.id.dinwei:
//			showLocatingFrequency();
//			break;
//		case R.id.yuanchen:
//			
//			break;
//		case R.id.message_center:
//			Intent msgIntent = new Intent(this, MessageCenterActivity.class);
//			startActivity(msgIntent);
//			break;
//
//		default:
//			break;
//		}
//
//	}

	// 定时休眠对话�?
	private void showTimeDailog() {
		
		View view = View.inflate(this, R.layout.novoice_dialog, null);
		dialog = new Dialog(this, R.style.dw_dialog);
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// éè??2???
		TextView time_text = (TextView) view.findViewById(R.id.time_text);
		time_text.setText(getString(R.string.client_time));
		final WheelView start_time = (WheelView) view
				.findViewById(R.id.start_time);
		start_time.setViewAdapter(new NumericWheelAdapter(this, 0, 23, "%02d"));
		start_time.setCyclic(false);// ??×ó?-?·
		final WheelView entime_1 = (WheelView) view.findViewById(R.id.entime_1);
		entime_1.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		entime_1.setCyclic(false);
		final WheelView start_time2 = (WheelView) view
				.findViewById(R.id.start_time2);
		start_time2.setViewAdapter(new NumericWheelAdapter(this, 0, 23, "%02d"));
		start_time2.setCyclic(false);// 
		final WheelView entime_2 = (WheelView) view.findViewById(R.id.entime_2);
		entime_2.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		entime_2.setCyclic(false);
		Button ok = (Button) view.findViewById(R.id.ok_set_time);
		Button cancel = (Button) view.findViewById(R.id.cl_set_time);
		ok.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {
				Editor ed = sp.edit();
//				getCurruntTime();
				int starttimes = start_time.getCurrentItem();
				int start_minutes = entime_1.getCurrentItem();
				int endtime = start_time2.getCurrentItem();
				int end_minutes = entime_2.getCurrentItem();
				String startString = starttimes + "";
				String endString = start_minutes + "";
				String startString2 = endtime + "";
				String endString2 = end_minutes + "";
				time.setDeviceId(Acount.getCurrentDevice().getId());
				time.setIsOpen(check_dinshi);
				time.setStartHour(endtime);
				time.setStartMinute(entime_2.getCurrentItem());
				time.setEndHour(starttimes);
				time.setEndMinute(entime_1.getCurrentItem());
				text_time.setText(startString + ":" + endString + "-" + startString2 + ":" + endString2);
				if(check_dinshi == true){
					set_time = "1" + "," + text_time.getText().toString();
					
				}else{
					set_time = "0" + "," + text_time.getText().toString();
				}
					ed.commit();
					Acount.getCurrentDevice().getDevDingtongConfig().setDormancy(set_time);
					new TimeTask().execute();
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

	// 计步�?
	private class PedometerTask extends AsyncTask<Void, Void, Integer> {
		@Override
		protected Integer doInBackground(Void... params) {
			try {
				if(Acount.getCurrentDevice().getTypeId() == 1){
					return HttpHelperUtils.Dev_dingtong_SendPedometer(pyte, Acount
							.getCurrentDevice().getId());
				}else{
					return HttpHelperUtils.DevHunterPedometerSetting(pyte, Acount
							.getCurrentDevice().getId());
				}
				
			} catch (ConnectException e) {
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result != null) {
				if (result == -1) {
					Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0)
							.show();
				} else if (result == 0) {
					Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0).show();

				} else if (result == 1) {
					Toast.makeText(getApplicationContext(), getString(R.string.send_true), 0)
							.show();
				} else if (result == -10){
					quitToLogin();
				} else if (result == -11){
					equipmentOffLine();
				}
			}
			hintDialog.dismiss();
			super.onPostExecute(result);
		}
	}

	private class TimeTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				if(Acount.getCurrentDevice().getTypeId() == 1){
					return HttpHelperUtils.Dev_dingtong_dormancy(time);
					
				}else{
					return HttpHelperUtils.DevHunterTimeRest(Acount
							.getCurrentDevice().getId(), start, end);
				}
				
			} catch (ConnectException e) {
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result != null) {
				switch (result) {
				case 0:
					Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0).show();
					break;
				case -1:
					Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0)
					.show();
					break;
				case 1:
					Toast.makeText(getApplicationContext(), getString(R.string.send_true), 0)
					.show();
					Acount.getCurrentDevice().getDevDingtongConfig().setDormancy(set_time);
					Log.v("TAG", Acount.getCurrentDevice().getDevDingtongConfig().getDormancy() + "ni mei");
					Log.v("TAG", set_time);
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
			hintDialog.dismiss();
			super.onPostExecute(result);
		}
	}

	public void back(View v) {
		finish();
	}
	
	private Device aDevice;
	private int mode;

	private void showLocatingFrequency() {
		Drawable drawable = getResources().getDrawable(
				R.drawable.bl_radiobutton_checked);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		View view = View
				.inflate(this, R.layout.locating_frequency_dialog, null);
		TextView zzms = (TextView) view.findViewById(R.id.zzms);
		TextView gao = (TextView) view.findViewById(R.id.gao);
		String works = Acount.getCurrentDevice().getDevDingtongConfig().getWORKMODE();
		zzms.setOnClickListener(this);
		gao.setOnClickListener(this);
		if(works != null){
			if (works.equals("2")) {
				zzms.setCompoundDrawables(drawable, null, null, null);
			}else if (works.equals("1")) {
				gao.setCompoundDrawables(drawable, null, null, null);
			}	
		}	
		dialog = new Dialog(this, R.style.dw_dialog);
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 设置布局
		dialog.show();
	}
	
	// 设置工作模式
		private class WorkModeTask extends AsyncTask<Void, Void, Integer> {
			@Override
			protected void onPreExecute() {
				dialogs.show();
				super.onPreExecute();
			}

			@Override
			protected Integer doInBackground(Void... params) {
				try {
					return HttpHelperUtils.Dev_dingtong_DevWorkMode(
							Acount.getCurrentDevice().getId(),WorkMode);
				} catch (ConnectException e) {
					dialogs.dismiss();
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Integer result) {
				if (result != null) {
					dialogs.dismiss();
					switch (result) {
					case 0:
						Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
						break;
					case -1:
						Toast.makeText(getApplicationContext(), getString(R.string.equipment_is_not_exist), 0)
						.show();
						break;
					case 1:
						Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
						String setWork = WorkMode + "";
						Acount.getCurrentDevice().getDevDingtongConfig().setWORKMODE(setWork);
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
				}else{
					dialogs.dismiss();
				}
				super.onPostExecute(result);
			}
		}
		// 获取设备信息
		private class GtTask extends AsyncTask<Void, Void, Integer> {

			@Override
			protected Integer doInBackground(Void... params) {
				try {
					aDevice = HttpHelperUtils.GetDeviceById(Acount
							.getCurrentDevice().getId());
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
				mode = aDevice.getDevHunterDetail().getWorkMode();
				dialogs.dismiss();
				super.onPostExecute(result);
			}

		}
		
		private class FactoryAsynTask extends AsyncTask<Void, Void, Integer>{

			@Override
			protected Integer doInBackground(Void... arg0) {
				try {
					if(Acount.getCurrentDevice().getTypeId() == 1){
						return HttpHelperUtils.Dev_dingtong_Factory(Acount.getCurrentDevice().getId());
					}else{
						return HttpHelperUtils.DevHunterFactory(Acount.getCurrentDevice().getId());
					}
					
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
					Toast.makeText(getApplicationContext(), getString(R.string.equipment_is_not_exist), 0).show();
					break;
				case 0:
					Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
					break;
				case 1:
					Toast.makeText(getApplicationContext(), getString(R.string.recover_factory_set), 0).show();
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

		@Override
		public void onClick(View v) {
			WorkModeTask task = new WorkModeTask();
			switch (v.getId()) {
			case R.id.zzms:
				WorkMode = 2;
				task.execute();
				dialog.dismiss();
				break;
			case R.id.gao:
				WorkMode = 1;
				task.execute();
				dialog.dismiss();
				break;
			default:
				break;
			}
		}
		
		
		private void showRecoverFactory(){
			View view = View.inflate(this, R.layout.recover_factory_dialog, null);
			Button btn_recover = (Button)view.findViewById(R.id.btn_recover);
			Button btn_fctory = (Button)view.findViewById(R.id.btn_factory);
			AlertDialog.Builder builder = new Builder(this);
			builder.setView(view);
			dialog_factory = builder.create();
			dialog_factory.show();
			btn_recover.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dialog_factory.dismiss();
					FactoryAsynTask factory = new FactoryAsynTask();
					factory.execute();
					
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
		
		private class BlueToothAsyntask extends AsyncTask<Void, Void, Integer> {

			@Override
			protected Integer doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				try {
					if(Acount.getCurrentDevice().getTypeId() == 1){
						return HttpHelperUtils.Dev_dingtong_BluetoothSetting(Acount.getCurrentDevice().getId(),open_blue);
					}else{
						return HttpHelperUtils.DevHunterBluetoothSetting(Acount.getCurrentDevice().getId(),open_blue);
					}
					
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
					Toast.makeText(InstructionIssuedActivity.this, getString(R.string.set_false), 0).show();
					break;
				case 0:
					Toast.makeText(InstructionIssuedActivity.this, getString(R.string.set_false), 0).show();
					break;
				case 1:
					Toast.makeText(InstructionIssuedActivity.this, getString(R.string.set_true), 0).show();
					if(open_blue){
						Intent gattServiceIntent = new Intent(
								getApplicationContext(), BLEService.class);
						startService(gattServiceIntent);		
						Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
						startActivity(intent);
					}	
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
		
		private boolean check_dinshi; 
		public void setMuteTime(){
			String msg = Acount.getCurrentDevice().getDevDingtongConfig().getDormancy();
			Log.v("TAG", msg);
			Log.v("TAG", Acount.getCurrentDevice().getDevDingtongConfig().getDormancy() + "ci yi zhi");
			try{
				if(msg != null && !"".equals(msg) && !"null".equals(msg)){
					String[] message = msg.split(",");
					String box_state = message[0];
					String text_times = message[1];
					text_time.setText(text_times);
					if("0".equals(box_state)){
						check_box_jingyin.setChecked(false);
						check_dinshi = false;
						set_time = "0" + "," + text_time.getText().toString();
					}else{
						check_box_jingyin.setChecked(true);
						check_dinshi = true;
						set_time = "1" + "," + text_time.getText().toString();
					}
					String[] msg_1 = text_times.split("-");
					String[] time_msg_1 = msg_1[0].split(":");
					String[] time_msg_2 = msg_1[1].split(":");
					time.setDeviceId(Acount.getCurrentDevice().getId());
					time.setIsOpen(check_dinshi);
					time.setStartHour(Integer.parseInt(time_msg_2[0]));
					time.setStartMinute(Integer.parseInt(time_msg_2[1]));
					time.setEndHour(Integer.parseInt(time_msg_1[0]));
					time.setEndMinute(Integer.parseInt(time_msg_1[1]));
					check_box_jingyin.setOnCheckedChangeListener(listener_blue);
				}else{
					check_box_jingyin.setOnCheckedChangeListener(listener_blue);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		
		}
}
