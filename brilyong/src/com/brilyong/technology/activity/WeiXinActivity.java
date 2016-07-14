package com.brilyong.technology.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.brilyong.technology.adapter.VoiceAdapter;
import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.MediaManager;
import com.brilyong.technology.entity.Message;
import com.brilyong.technology.entity.Options;
import com.brilyong.technology.entity.Recorder;
import com.brilyong.technology.entity.UserMessageTable;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.httputils.MutualUtil;
import com.brilyong.technology.view.AudioRecorderButton;
import com.brilyong.technology.view.PullToRefreshLayout;
import com.brilyong.technology.view.AudioRecorderButton.AudioFinishRecorderListener;
import com.brilyong.technology.view.PullToRefreshLayout.OnRefreshListener;
import com.brilyong.technology.R;

public class WeiXinActivity extends BaseUIActivityUtil implements
		OnRefreshListener {

	private VoiceAdapter mAdapter;
	private List<Recorder> mDatas = new ArrayList<Recorder>();
	private AudioRecorderButton mAudioRecorderButton;
	private ListView listView;
	private PullToRefreshLayout ptrl;
	private View animView;
	private SharedPreferences sp;
	private ArrayList<Message> list;
	private Options opt;
	private String loginName;
	private String mUrl;
	private File file;
	private String path;
    private final static String SENDLOADING = "downLoading"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weixin_activity_main);
		// ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		// ptrl.setOnRefreshListener(this);
		listView = (ListView) findViewById(R.id.listView);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		loginName = sp.getString("LoginName", null);
		// ptrl.setOnRefreshListener(this);
		new getVoice().execute();
		registerReceiver(receiver, new IntentFilter("content"));
		mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.id_recorder_button);
		mAudioRecorderButton
				.setAudioFinishRecorderListener(new AudioFinishRecorderListener() {

					@Override
					public void onFinish(float seconds, String filePath) {
						// Recorder mRecorder = new Recorder(seconds, filePath);
						Message msg = new Message();
						msg.setFilePath(filePath);
						msg.setAgre_stat(-100);
						msg.setAddDate(new Date());
						list.add(msg);
						mAdapter.notifyDataSetChanged();
						listView.setSelection(list.size() - 1);
						new com.brilyong.technology.view.HttpUtils(filePath, sp)
								.start();
					}
				});

		listView.setOnItemClickListener(new OnItemClickListener() {
			private AnimationDrawable anim;

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				 Toast.makeText(getApplicationContext(),
//				 list.get(arg2).toString(), 0).show();
				int send = list.get(arg2).getAgre_stat();
				mUrl = list.get(arg2).getMsgData();	
				if(mUrl != null){
					path = MutualUtil.GetFileNameFromUrl(mUrl);					
					path = MyApplication.downPath + path;
					file = new File(path);
				}else{
					path = list.get(arg2).getFilePath();
				}	
				if(file != null && !file.exists()){
					Intent intent = new Intent(SENDLOADING);
					intent.putExtra("loading", list.get(arg2).getMsgData());
					sendBroadcast(intent);
				}else{
					if (TextUtils.isEmpty(path)) {
						Toast.makeText(getApplicationContext(), getString(R.string.no_record_files),
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (animView != null) {
						if (send == -100) {
							animView.setBackgroundResource(R.drawable.bl_adj);
						} else {
							animView.setBackgroundResource(R.drawable.left_3);
						}
						animView = null;
					}
					animView = arg1.findViewById(R.id.recorder_anmi);
					if (send == -100) {
						animView.setBackgroundResource(R.drawable.bl_play_left_anim);
					} else {
						animView.setBackgroundResource(R.drawable.bl_play_anim);
					}
					anim = (AnimationDrawable) animView
							.getBackground();
					anim.start();

					MediaManager.playSound(path,
							new MediaPlayer.OnCompletionListener() {

								@Override
								public void onCompletion(MediaPlayer arg0) {
									anim.selectDrawable(2);
									anim.stop();
								}
							});
				}
				
			}
		});
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			Message msg = (Message) bundle.getSerializable("message");
			mAdapter.add(msg);
			listView.setSelection(mAdapter.getCount() - 1);
		}

	};

	@Override
	protected void onPause() {
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
		unregisterReceiver(receiver);
	}

	private class getVoice extends AsyncTask<Void, Void, UserMessageTable> {

		@Override
		protected UserMessageTable doInBackground(Void... params) {
			try {
				return HttpHelperUtils.GetAndroidVoiceMessageList(loginName, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(UserMessageTable result) {
			list = result.getRows();
			mAdapter = new VoiceAdapter(list, WeiXinActivity.this);
			listView.setAdapter(mAdapter);
			super.onPostExecute(result);
		}
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub

	}	
}
