package com.brilyong.technology.view;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaRecorder;

import com.brilyong.technology.entity.Acount;

public class AudioManager {

	private MediaRecorder mMediaRecorder;
	private String mDir;
	private String mCurrentFilePath;
	private boolean isPrepare;

	private static AudioManager mInstance;

	public AudioManager(String dir, Context context) {
		mDir = dir;
		SharedPreferences sp = context.getSharedPreferences("config",
				context.MODE_PRIVATE);
		loginName = sp.getString("LoginName", null);
	}

	public static AudioManager getInstance(String dir, Context context) {
		if (mInstance == null) {
			synchronized (AudioManager.class) {
				if (mInstance == null) {
					mInstance = new AudioManager(dir, context);
				}
			}
		}
		return mInstance;
	}

	public void prepareAudio() {

		try {
			isPrepare = false;
			File dir = new File(mDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String fileName = generateFileName();
			File file = new File(dir, fileName);
			mCurrentFilePath = file.getAbsolutePath();
			mMediaRecorder = new MediaRecorder();
			mMediaRecorder.setOutputFile(file.getAbsolutePath());
			// 设置音频源为麦克风
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// 设置音频格式
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			// 设置音频的编码为amr
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mMediaRecorder.prepare();
			mMediaRecorder.start();
			isPrepare = true;
			if (mListener != null) {
				mListener.wellPrepared();
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 随机生成文件名称
	 * 
	 * @return
	 */
	private String generateFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyHHddHHmmss");
		String time = sdf.format(new Date());
		return time + "_" + Acount.getCurrentDevice().getId() + "_" + loginName
				+ "_" + UUID.randomUUID().toString() + ".amr";
	}

	public int getVoiceLevel(int maxLevel) {
		if (isPrepare) {
			try {
				return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
		return 1;

	}

	public void release() {
		mMediaRecorder.stop();
		mMediaRecorder.release();
		isPrepare = false;
		mMediaRecorder = null;
	}

	public void cancel() {
		release();
		if (mCurrentFilePath != null) {
			File file = new File(mCurrentFilePath);
			file.delete();
			file = null;
		}
	}

	public interface AudioStateListener {
		void wellPrepared();
	}

	public AudioStateListener mListener;
	private String loginName;

	public void setOnAudioStateListener(AudioStateListener Listener) {
		mListener = Listener;
	}

	public String getCurrentFilePath() {
		// TODO Auto-generated method stub
		return mCurrentFilePath;
	}
}
