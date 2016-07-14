package com.brilyong.technology.utils;

import com.brilyong.technology.activity.AddEquipmentActivity;
import com.brilyong.technology.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class DialogUtils {

	private static TextView progress_dialog_message;
	private Context context;
	private Dialog dialog;
	private static Dialog wait_dialog;
	
	
	public DialogUtils(Context context) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
	}

	public static Dialog getDialog(Context context, String text) {
		View view = View.inflate(context, R.layout.progress_daogin, null);
		progress_dialog_message = (TextView) view
				.findViewById(R.id.progress_dialog_message);
		progress_dialog_message.setText(text);
		Dialog dialog = new Dialog(context, R.style.dw_dialog);
		dialog.setCancelable(false);
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// ���ò���
		return dialog;
	}

	public static void setDialogMessage(String text) {
		progress_dialog_message.setText(text);
	}
	
	public void showRecoverFactory(){
		initDialogView();
		dialog.show();
	}
	private OnClickListener mlistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.add_device_ok:
				dialog.dismiss();
				Intent intent = new Intent(context,AddEquipmentActivity.class);
				context.startActivity(intent);
				break;
			case R.id.add_device_no:
				dialog.dismiss();
				break;

			default:
				break;
			}
		}
	};
	
	
	public void initDialogView(){
		View view = View.inflate(context, R.layout.add_device_dialog, null);
		Button add_device_no = (Button) view.findViewById(R.id.add_device_no);
		Button add_device_ok = (Button) view.findViewById(R.id.add_device_ok);
		dialog = new Dialog(context, R.style.dw_dialog);
		dialog.setCancelable(false);
		dialog.setContentView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.MATCH_PARENT));
		add_device_no.setOnClickListener(mlistener);
		add_device_ok.setOnClickListener(mlistener);
	}
	
	//��ʼ������Dialog
		public static Dialog initPhoneDialogView(Context context,String name,OnClickListener phoneListener){
			View view = View.inflate(context, R.layout.phone_listen, null);
			Button add_device_no = (Button) view.findViewById(R.id.phone_no);
			Button add_device_ok = (Button) view.findViewById(R.id.phone_ok);
			TextView phone_listen_text = (TextView) view.findViewById(R.id.phone_listen_text);
			if(!TextUtils.isEmpty(name)){
				phone_listen_text.setText("�Ƿ�ز�" + name + "�������");
			}
			Dialog dialog = new Dialog(context, R.style.dw_dialog);
			dialog.setCancelable(false);
			dialog.setContentView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
					LinearLayout.LayoutParams.MATCH_PARENT));
			add_device_no.setOnClickListener(phoneListener);
			add_device_ok.setOnClickListener(phoneListener);
			return dialog;
		}
		//�����ĵȴ������ڼ�������
		public static Dialog waitDialog(Context context){
			View view = View.inflate(context, R.layout.progress_daogin, null);
			
			
			Dialog dialogWait = new Dialog(context, R.style.dw_dialog);
			
			
			TextView progress_dialog_message = (TextView) view.findViewById(R.id.progress_dialog_message);
			
			progress_dialog_message.setText(context.getString(R.string.loading));
			
			dialogWait.setCanceledOnTouchOutside(true);
			
			dialogWait.setContentView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
			
			return dialogWait;
			
		}
		
		public static void ShowWaitDialog(Context context){
			
			wait_dialog = waitDialog(context);
			
			wait_dialog.show();
		}
		
		//�ȴ�����ʧ
		public static void dismissDialog(){
			if(wait_dialog != null && wait_dialog.isShowing()){
				wait_dialog.dismiss();
			}
		}		
		
		//��ʼ��ɾ���������
		public static Dialog initRelationDialogView(Context context,OnClickListener phoneListener){
			View view = View.inflate(context, R.layout.phone_listen, null);
			Button add_device_no = (Button) view.findViewById(R.id.phone_no);
			Button add_device_ok = (Button) view.findViewById(R.id.phone_ok);
			TextView phone_listen_text = (TextView) view.findViewById(R.id.phone_listen_text);
			phone_listen_text.setText("�Ƿ�ɾ����");
			Dialog dialog = new Dialog(context, R.style.dw_dialog);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setContentView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
					LinearLayout.LayoutParams.MATCH_PARENT));
			add_device_no.setOnClickListener(phoneListener);
			add_device_ok.setOnClickListener(phoneListener);
			return dialog;
		}						
		
}
