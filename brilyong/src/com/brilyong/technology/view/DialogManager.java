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
			mLable.setText("手指上滑，取消发送");
		}
	}

	public void wantToCancel() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVice.setVisibility(View.GONE);
			mLable.setVisibility(View.VISIBLE);
			mIcon.setImageResource(R.drawable.bl_cancel);
			mLable.setText("松开手指，取消发送");
		}

	}

	public void tooShort() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVice.setVisibility(View.GONE);
			mLable.setVisibility(View.VISIBLE);
			mIcon.setImageResource(R.drawable.bl_voice_to_short);
			mLable.setText("录音时间过短");
		}
	}

	public void dimissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
			mDialog = null;
		}

	}

	/**
	 * 通过leve去更新
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
