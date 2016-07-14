package com.brilyong.technology.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.EditText;

import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.MediaManager;
import com.brilyong.technology.entity.Message;
import com.brilyong.technology.httputils.AppConfig;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.httputils.JSONHelper;
import com.brilyong.technology.httputils.MutualUtil;
import com.brilyong.technology.utils.PushExampleUtil;
import com.brilyong.technology.R;



public class GetMessageService extends Service {
	private EditText text;
	private String user_id;
	private Date add_date;
	private String msg_data;
	private int send_count;
	private int message_type;
	private Date data;
	private String startTime;
	private SimpleDateFormat sdf;
	private String hour;
	private String min;
	private SharedPreferences sp;
	private String loginName; // 用户名
	private String mUrl;
	private String mName;
	private int locationtype;
	private String gpstime;
	private int battery;
	private boolean flag = false;
	private MediaPlayer player;
	private Context context;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		registerMessageReceiver();
		sp = getSharedPreferences("config", MODE_PRIVATE);
		loginName = sp.getString("LoginName", null);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.blyang.technology.MESSAGE_RECEIVED_ACTION";
	public static final String MESSAGE_RECEIVED_DOENLOAD = "com.blyang.technology.MESSAGE_RECEIVED_DOWNLOAD";
	public final static String SENDLOADING = "downLoading"; 
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	public static final String KEY_DOWAN = "doawn";
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		filter.addAction(MESSAGE_RECEIVED_DOENLOAD);
		filter.addAction(SENDLOADING);
		filter.addAction(AppConfig.MESSAGENOTIFICATION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				flag = false;
				// showMsg.append(messge + "\n");
				if (!PushExampleUtil.isEmpty(extras)) {
					showMsg.append(extras + "\n");
				}
				setCostomMsg(showMsg.toString());
				mhandler.sendMessage(mhandler.obtainMessage(MSG_Send,
						showMsg.toString()));
			}
			if(MESSAGE_RECEIVED_DOENLOAD.equals(intent.getAction())){
				String dowan = intent.getStringExtra(KEY_DOWAN);
				mUrl = dowan;		
				mName = MutualUtil.GetFileNameFromUrl(mUrl);			
				downloadSound();
				
			}
			if(SENDLOADING.equals(intent.getAction())){
				String dowan = intent.getStringExtra("loading");
				mUrl = dowan;
				mName = MutualUtil.GetFileNameFromUrl(mUrl);			
				downloadSound();	
				flag = true;
			}
			if(AppConfig.MESSAGENOTIFICATION.equals(intent.getAction())){
				if(player.isPlaying()){
					player.stop();
				}
			}
		
		}
	}

	private static final int MSG_Send = 1001;
	private Handler mhandler = new Handler() {

		private String msg1;
		private String path;

		@Override
		public void handleMessage(android.os.Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_Send:
				sdf = new SimpleDateFormat("MM-dd HH:mm");
				String json = (String) msg.obj;
				try {
					JSONObject jsn = new JSONObject(json);
					msg1 = jsn.getString("data");
					JSONObject obj = new JSONObject(msg1);
					if(obj.getInt("Type") == 17){
						player = MediaPlayer.create(getApplicationContext(), R.raw.a);
						if(player.isPlaying()){
							player.stop();
						}
						player.start();
					}
					if (obj.getInt("Type") == 7) {
						
						mUrl = obj.getString("MsgData");
//						String[] m = mUrl.split("/");
						mName = MutualUtil.GetFileNameFromUrl(mUrl);
						path = MyApplication.downPath + mName;
						File file = new File(path);
						if (!file.exists()) {
							downloadSound();
						}
						Message message = JSONHelper.parseObject(msg1,
								Message.class);	
						message.setFilePath(path);
						Intent intent = new Intent();
						Bundle bun = new Bundle();
						bun.putSerializable("message", message);
						intent.putExtras(bun);
						intent.setAction("content");
						sendBroadcast(intent);
					}
					if(obj.getInt("Type") == 17){
						
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				
				break;
			}
		}
	};

	private void setCostomMsg(String msg) {
		if (null != text) {
			text.setText(msg);
			text.setVisibility(android.view.View.VISIBLE);
		}
	}

	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mMessageReceiver);
	}

	// 下载录音
	private void downloadSound() {
		Thread downLoadThread = new Thread(downLoadApkRunnable);
		downLoadThread.start();
	}

	// 下载录音
	private Runnable downLoadApkRunnable = new Runnable() {
		private FileOutputStream fos;
		private InputStream is;
		private File apkFile;
		@Override
		public void run() {
			try {
				
				if (mUrl.startsWith("/")) {
					mUrl=mUrl.substring(1);
				}
				URL url = new URL(AppConfig.URL + mUrl);
				
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(40000);
				conn.setReadTimeout(40000);
				conn.connect();
				if (conn.getResponseCode() == 200) {
					is = conn.getInputStream();
					File file = new File(MyApplication.downPath);
					if (!file.exists()) {
						file.mkdirs();
					}
					apkFile = new File(MyApplication.downPath + mName);
					fos = new FileOutputStream(apkFile);
					byte[] buf = new byte[1024];
					int numRead = 0;
					while ((numRead = is.read(buf)) != -1) {
						fos.write(buf, 0, numRead);
					}
					if(flag == true){
						if(apkFile.exists()){
							String path = MyApplication.downPath + mName;
							MediaManager.playSound(path,
									new MediaPlayer.OnCompletionListener() {

										@Override
										public void onCompletion(MediaPlayer arg0) {
								
										}
									});
						}
					}
				}
			} catch (ConnectException e) {
				// TODO: handle exception
			} catch (MalformedURLException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fos.close();
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	};
	
	private static void CreateNotification(){
		
	}

}
