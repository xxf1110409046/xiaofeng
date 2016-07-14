package com.brilyong.technology.view;

import com.brilyong.technology.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogManager {

	private Dialog mDialog;
	private ImageView mIcon;
	private ImageView mVice;
	private TextView mLable;
	private Context context;

	public DialogManager(Context context) {
		super();
		this.context = context;
	}

	public void showRecordingDialog() {
		View view = View.inflate(context, R.layout.dialog_rec, null);
		mDialog = new Dialog(context, R.style.Theme_AudioDialog);
		mDialog.setContentView(view);
		mIcon = (ImageView) view.findViewById(R.id.dialog_icon);
		mVice = (ImageView) view.findViewById(R.id.dialog_voice);
		mLable = (TextView) view.findViewById(R.id.dialog_lable);
		mDialog.show();
	}

	public void recoiding() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVice.setVisibility(View.VISIBLE);
			mLable.setVisibility(View.VISIBLE);
			mIcon.setImageResource(R.drawable.bl_recorder);
			mLable.setText("��ָ�ϻ���ȡ������");
		}
	}

	public void wantToCancel() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVice.setVisibility(View.GONE);
			mLable.setVisibility(View.VISIBLE);
			mIcon.setImageResource(R.drawable.bl_cancel);
			mLable.setText("�ɿ���ָ��ȡ������");
		}

	}

	public void tooShort() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVice.setVisibility(View.GONE);
			mLable.setVisibility(View.VISIBLE);
			mIcon.setImageResource(R.drawable.bl_voice_to_short);
			mLable.setText("¼��ʱ�����");
		}
	}

	public void dimissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
			mDialog = null;
		}

	}

	/**
	 * ͨ��leveȥ����
	 * 
	 * @param level
	 */
	public void updateViceLeve(int level) {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVice.setVisibility(View.VISIBLE);
			mLable.setVisibility(View.VISIBLE);
			int resId = context.getResources().getIdentifier("bl_v" + level,
					"drawable", context.getPackageName());
			mVice.setImageResource(resId);
		}
	}
}
