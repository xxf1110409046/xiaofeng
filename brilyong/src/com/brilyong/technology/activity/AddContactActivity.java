package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.text.BreakIterator;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brilyong.technology.R;
import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.DevDingtongConfig;
import com.brilyong.technology.entity.DevHunterDetail;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.NetworkUtils;


/**
 * 添加联系人
 * 
 * @author Administrator
 * 
 */
public class AddContactActivity extends BaseUIActivityUtil {

	private static final int PICK_CONTACT = 0;
	private AlertDialog dailog_add;
	private EditText sos1;
	private EditText sos2;
	private EditText sos3;
	private EditText sos4;
	private EditText sos5;
	private EditText sos6;
	private EditText sos7;
	private EditText sos8;
	private String phone1;
	private String phone2;
	private String phone3;
	private String phone4;
	private String phone5;
	private String phone6;
	private String phone7;
	private String phone8;
	private Dialog dialog;
	private TextView progress_dialog_message;
	private Button submit_all_number;
	private Device aDevice;// 当前设备
	private Device aDevice1;// 当前设备
	private DevHunterDetail list_all;
	private DevDingtongConfig list_all2;
	private LinearLayout btn_add;
	private LinearLayout type_sos5;
	private LinearLayout type_sos6;
	private LinearLayout type_sos7;
	private LinearLayout type_sos8;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contacts_activity);
		initView();
		setNumberOrigin(aDevice);
		initDiaolg();
		
	}
	@SuppressWarnings("unused")
	private void initView(){
		submit_all_number = (Button) findViewById(R.id.submit_all_number);
//		btn_add = (LinearLayout) findViewById(R.id.btn_add);
		sos1 = (EditText) findViewById(R.id.sos1);
		sos2 = (EditText) findViewById(R.id.sos2);
		sos3 = (EditText) findViewById(R.id.sos3);
		sos4 = (EditText) findViewById(R.id.sos4);
		sos5 = (EditText) findViewById(R.id.sos5);
		sos6 = (EditText) findViewById(R.id.sos6);
		sos7 = (EditText) findViewById(R.id.sos7);
		sos8 = (EditText) findViewById(R.id.sos8);	
		type_sos5 = (LinearLayout)findViewById(R.id.type_sos5);
		type_sos6 = (LinearLayout)findViewById(R.id.type_sos6);
		type_sos7 = (LinearLayout)findViewById(R.id.type_sos7);
		type_sos8 = (LinearLayout)findViewById(R.id.type_sos8);
	}
	private void setNumberOrigin(Device device){
		if(Acount.getCurrentDevice().getTypeId() == 1){
			type_sos6.setVisibility(View.GONE);
			type_sos7.setVisibility(View.GONE);
			type_sos8.setVisibility(View.GONE);
			aDevice = Acount.getCurrentDevice();
			list_all2 = aDevice.getDevDingtongConfig();
			if (list_all2 != null) {
				if("null".equals(list_all2.getPhone1())){
					sos1.setHint(R.string.sos1);			
				}else{
					sos1.setText(list_all2.getPhone1());
				}
				if("null".equals(list_all2.getPhone2())){
					sos2.setHint(R.string.sos1);
				}else{
					sos2.setText(list_all2.getPhone2());
				}	
				if("null".equals(list_all2.getPhone3())){
					sos3.setHint(R.string.sos1);			
				}else{
					sos3.setText(list_all2.getPhone3());
				}
				if("null".equals(list_all2.getSosPhone())){
					sos4.setHint(R.string.sos1);
				}else{
					sos4.setText(list_all2.getSosPhone());
				}	
				if("null".equals(list_all2.getListenerPhone())){
					sos5.setHint(R.string.sos1);			
				}else{
					sos5.setText(list_all2.getListenerPhone());
				}
				}
		}else{
			device = Acount.getCurrentDevice();
			list_all = device.getDevHunterDetail();
			if (list_all!=null) {
				if("null".equals(list_all.getSos1())){
					sos1.setHint(R.string.sos1);
				}else{
					sos1.setText(list_all.getSos1());
				}	
				if("null".equals(list_all.getSos2())){
					sos2.setHint(R.string.sos1);			
				}else{
					sos2.setText(list_all.getSos2());
				}
				
				if("null".equals(list_all.getSos3())){
					sos3.setHint(R.string.sos1);	
				}else{
					sos3.setText(list_all.getSos3());
				}	
				if("null".equals(list_all.getSos4())){
					sos4.setHint(R.string.sos1);	
				}else{
					sos4.setText(list_all.getSos4());
				}
				if("null".equals(list_all.getSos5())){
					sos5.setHint(R.string.sos1);			
				}else{
					sos5.setText(list_all.getSos5());
				}	
				if("null".equals(list_all.getSos6())){
					sos6.setHint(R.string.sos1);		
				}else{
					sos6.setText(list_all.getSos6());
				}
				if("null".equals(list_all.getSos7())){
					sos7.setHint(R.string.sos1);		
				}else{
					sos7.setText(list_all.getSos7());
				}
				if("null".equals(list_all.getSos8())){
					sos8.setHint(R.string.sos1);
				}else{		
					sos8.setText(list_all.getSos8());
				}
			}		
		}
	}
	

	private void initDiaolg() {
		// 等候对话框
		View view = View.inflate(this, R.layout.progress_daogin, null);
		progress_dialog_message = (TextView) view
				.findViewById(R.id.progress_dialog_message);
		progress_dialog_message.setText(getString(R.string.is_loading));
		dialog = new Dialog(this, R.style.dw_dialog);
		dialog.setCancelable(false);
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 设置布局
	}
	
	private void getContact(){
		phone1 = sos1.getText().toString().trim();
		phone2 = sos2.getText().toString().trim();
		phone3 = sos3.getText().toString().trim();
		phone4 = sos4.getText().toString().trim();
		phone5 = sos5.getText().toString().trim();
		phone6 = sos6.getText().toString().trim();
		phone7 = sos7.getText().toString().trim();
		phone8 = sos8.getText().toString().trim();
		if (!NetworkUtils.isNetworkConnected(this)) {
			Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
			return;
		}
		dialog.show();
		DearTask task = new DearTask();
		task.execute();
	}

	public void keep(View v) {
		switch (v.getId()) {
		case R.id.submit_all_number:
			getContact();
			break;
		default:
			break;
		}
		
	}

	private class DearTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				if(Acount.getCurrentDevice().getDevDingtongConfig() != null){
					return HttpHelperUtils.Dev_Dingtong_SetPhoneNumbers(Acount
							.getCurrentDevice().getId(), phone4,phone1,phone2,phone3,phone5);
				}else{
					return HttpHelperUtils.DevHunterPhoneNumbersSetting8(Acount
							.getCurrentDevice().getId(), phone1,phone2,phone3,phone4,phone5,phone6,
							phone7,phone8);
				}			
			} catch (ConnectException e) {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Integer result) {
			if (result != null) {
				if (result == -1) {
					Toast.makeText(getApplicationContext(), getString(R.string.no_permission), 0).show();
				} else if (result == 0) {
					Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0).show();
				} else if (result == 1) {
					Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
//					sos1.setCursorVisible(false);
//					sos2.setCursorVisible(false);
//					sos3.setCursorVisible(false);
//					sos4.setCursorVisible(false);
//					sos5.setCursorVisible(false);
//					sos6.setCursorVisible(false);
//					sos7.setCursorVisible(false);
//					sos8.setCursorVisible(false);
					if(Acount.getCurrentDevice().getTypeId() == 1){
						Acount.getCurrentDevice().getDevDingtongConfig().setSosPhone(phone4);
						Acount.getCurrentDevice().getDevDingtongConfig().setPhone1(phone1);
						Acount.getCurrentDevice().getDevDingtongConfig().setPhone2(phone2);
						Acount.getCurrentDevice().getDevDingtongConfig().setPhone3(phone3);
						Acount.getCurrentDevice().getDevDingtongConfig().setListenerPhone(phone5);
					}else{
						Acount.getCurrentDevice().getDevHunterDetail().setSos1(phone1);
						Acount.getCurrentDevice().getDevHunterDetail().setSos2(phone2);
						Acount.getCurrentDevice().getDevHunterDetail().setSos3(phone3);
						Acount.getCurrentDevice().getDevHunterDetail().setSos4(phone4);
						Acount.getCurrentDevice().getDevHunterDetail().setSos5(phone5);
						Acount.getCurrentDevice().getDevHunterDetail().setSos6(phone6);
						Acount.getCurrentDevice().getDevHunterDetail().setSos7(phone7);
						Acount.getCurrentDevice().getDevHunterDetail().setSos8(phone8);
					}
//					hand.sendEmptyMessage(0);
				} else if(result == -10){
					quitToLogin();
				} else if(result == -11){
					equipmentOffLine();
				}
			}
			dialog.dismiss();
			super.onPostExecute(result);
		}

	}

	// 添加手机联系人


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

	}

	public void back(View v) {
		finish();
	}
}
