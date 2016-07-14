package com.brilyong.technology.view;

 

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.brilyong.technology.view.AudioManager.AudioStateListener;
import com.brilyong.technology.R;

public class AudioRecorderButton extends Button implements AudioStateListener {
	private static final int DISTANCE_Y_CANCEL = 50;
	private static final int STATE_NORMAL = 1;
	private static final int STATE_RECORDING = 2;
	private static final int STATE_WANT_TO_CANCEL = 3;
	private int mCurState = STATE_NORMAL;
	boolean isRecording = false;
	private DialogManager mDialogManager;
	private AudioManager mAudioManager;
	private float mTime;
	private boolean mReady;
	// 获取音量大小的Runnable
	private Runnable mGetVoiceLevelRunnable = new Runnable() {

		@Override
		public void run() {
			while (isRecording) {
				try {
					Thread.sleep(100);
					mTime += 0.1f;
					hander.sendEmptyMessage(MSG_VOICE_CHANGED);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	};

	public AudioRecorderButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public AudioRecorderButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDialogManager = new DialogManager(getContext());
		String dir = Environment.getExternalStorageDirectory() + "/blyang";
		mAudioManager = AudioManager.getInstance(dir,context);
		mAudioManager.setOnAudioStateListener(this);
		setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				mReady = true;
				mAudioManager.prepareAudio();
				return false;
			}
		});
	}

	public AudioRecorderButton(Context context) {
		this(context, null);
	}

	public interface AudioFinishRecorderListener {
		void onFinish(float seconds, String filePath);
	}

	private AudioFinishRecorderListener mListener;

	public void setAudioFinishRecorderListener(
			AudioFinishRecorderListener Listener) {
		mListener = Listener;
	}

	@Override
	public void wellPrepared() {
		hander.sendEmptyMessage(MSG_AUDIO_PREPARED);
	}

	private static final int MSG_AUDIO_PREPARED = 0X110;
	private static final int MSG_VOICE_CHANGED = 0X111;
	private static final int MSG_DIALOG_DIMISS = 0X112;
	private Handler hander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_AUDIO_PREPARED:
				mDialogManager.showRecordingDialog();
				isRecording = true;
				new Thread(mGetVoiceLevelRunnable).start();
				break;
			case MSG_VOICE_CHANGED:
				mDialogManager.updateViceLeve(mAudioManager.getVoiceLevel(7));
				break;
			case MSG_DIALOG_DIMISS:
				mDialogManager.dimissDialog();
				break;

			default:
				break;
			}
		};
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			changeState(STATE_RECORDING);
			break;
		case MotionEvent.ACTION_MOVE:
			// 根据x,y坐标，判断是否取消
			if (isRecording) {
				if (wantToCancel(x, y)) {
					changeState(STATE_WANT_TO_CANCEL);
				} else {
					changeState(STATE_RECORDING);
				}
			}

			break;
		case MotionEvent.ACTION_UP:
			if (!mReady) {
				reset();
				return super.onTouchEvent(event);
			}
			if (!isRecording || mTime < 0.6f) {
				mDialogManager.tooShort();
				mAudioManager.cancel();
				hander.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
			} else if (mCurState == STATE_RECORDING) {
				if (mListener != null) {
					mListener.onFinish(mTime,
							mAudioManager.getCurrentFilePath());
				}
				mDialogManager.dimissDialog();
				mAudioManager.release();

			} else if (mCurState == STATE_WANT_TO_CANCEL) {
				mDialogManager.dimissDialog();
				mAudioManager.cancel();
			}
			reset();
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 恢复状态以及标志位
	 */
	private void reset() {
		isRecording = false;
		mReady = false;
		mTime = 0;
		changeState(STATE_NORMAL);
	}

	private boolean wantToCancel(int x, int y) {
		if (x < 0 || x > getWidth()) {
			return true;
		}
		if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
			return true;
		}
		return false;
	}

	private void changeState(int state) {
		if (mCurState != state) {
			mCurState = state;
			switch (state) {
			case STATE_NORMAL:
				setBackgroundResource(R.drawable.bl_btn_recoreder_nomal);
				setText(R.string.str_recorder_normal);
				break;
			case STATE_RECORDING:
				setBackgroundResource(R.drawable.bl_btn_recoreder_press);
				setText(R.string.str_recorder_record);
				if (isRecording) {
					mDialogManager.recoiding();
				}
				break;
			case STATE_WANT_TO_CANCEL:
				setBackgroundResource(R.drawable.bl_btn_recoreder_press);
				setText(R.string.str_recorder_want_cancel);
				mDialogManager.wantToCancel();
				break;

			default:
				break;
			}
		}

	}

}
