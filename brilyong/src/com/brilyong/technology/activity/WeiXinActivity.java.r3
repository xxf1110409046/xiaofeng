package com.blyang.technology.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.brilyong.technology.R;
import com.blyang.technology.adapter.RecorderAdapter;
import com.blyang.technology.entity.MediaManager;
import com.blyang.technology.entity.Recorder;
import com.blyang.technology.view.AudioRecorderButton;
import com.blyang.technology.view.AudioRecorderButton.AudioFinishRecorderListener;



public class WeiXinActivity extends Activity {

	private ArrayAdapter<Recorder> mAdapter;
	private List<Recorder> mDatas = new ArrayList<Recorder>();
	private AudioRecorderButton mAudioRecorderButton;
	private ListView listView;
	private View animView;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weixin_activity_main);
		listView = (ListView) findViewById(R.id.listView);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.id_recorder_button);
		mAudioRecorderButton
				.setAudioFinishRecorderListener(new AudioFinishRecorderListener() {

					@Override
					public void onFinish(float seconds, String filePath) {
						Recorder mRecorder = new Recorder(seconds, filePath);
						mDatas.add(mRecorder);
						mAdapter.notifyDataSetChanged();
						listView.setSelection(mDatas.size() - 1);
						new com.blyang.technology.view.HttpUtils(filePath,sp).start();
					}
				});
		mAdapter = new RecorderAdapter(WeiXinActivity.this, mDatas);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (animView != null) {
					animView.setBackgroundResource(R.drawable.bl_adj);
					animView = null;
				}
				animView = arg1.findViewById(R.id.recorder_anmi);
				animView.setBackgroundResource(R.drawable.bl_play_anim);
				AnimationDrawable anim = (AnimationDrawable) animView
						.getBackground();
				anim.start();

				MediaManager.playSound(mDatas.get(arg2).getFilePath(),
						new MediaPlayer.OnCompletionListener() {

							@Override
							public void onCompletion(MediaPlayer arg0) {
								animView.setBackgroundResource(R.drawable.bl_adj);
							}
						});
			}
		});
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MediaManager.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MediaManager.resume();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MediaManager.release();
	}

}