package com.brilyong.technology.activity;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.james.mime4j.message.Body;

import com.brilyong.technology.adapter.ListViewAdapter;
import com.brilyong.technology.adapter.MessageAdapter;
import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.BodyData;
import com.brilyong.technology.entity.Message;
import com.brilyong.technology.entity.Option;
import com.brilyong.technology.entity.Options;
import com.brilyong.technology.entity.SchoolData;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.httputils.MutualUtil;
import com.brilyong.technology.service.GetMessageService.MessageReceiver;
import com.brilyong.technology.view.AutoListView;
import com.brilyong.technology.view.RefreshListView;
import com.brilyong.technology.view.AutoListView.OnLoadListener;
import com.brilyong.technology.view.AutoListView.OnRefreshListener;
import com.brilyong.technology.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;



/**
 * 消息中心
 * 
 * @author Administrator
 * @param <RefreshDataAsynTask>
 * 
 */
public class MessageCenterActivity<RefreshDataAsynTask> extends
		BaseUIActivityUtil implements OnClickListener,
		OnRefreshListener,OnLoadListener {

	// 2015.6.29 上拉下拉刷新加载
	private AutoListView lstv_message;
	private EditText text;
	TextView message_text_noinfomation;
	private Button message_btn;
	private Date data;
	private String startTime;
	private SimpleDateFormat sdf;
	private String hour;
	private String min;
	private SharedPreferences sp;
	private String loginName; // 用户名
	private ArrayList<Message> result;
	private static int msgIndex;
	private MediaPlayer mPlayer = new MediaPlayer();
	private ArrayList<Message> list = new ArrayList<Message>(); 
	private MessageAdapter adapter;
	private Options body;
	private String name; 
	private MessageReceiver mMessageReceiver;
	private String id;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			result =  (ArrayList<Message>) msg.obj;
			switch (msg.what) {
			case AutoListView.REFRESH:
				lstv_message.onRefreshComplete();
				list.clear();
				list.addAll(result);
				break;
			case AutoListView.LOAD:
				lstv_message.onLoadComplete();
				list.addAll(result);
				break;
			}
			lstv_message.setResultSize(result.size());
			msgIndex = list.size();
			adapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_center_activity);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		loginName = sp.getString("LoginName", null);
		adapter = new MessageAdapter(this, list, loginName);
		lstv_message = (AutoListView) findViewById(R.id.lstv_message);
		lstv_message.setAdapter(adapter);
		lstv_message.setOnRefreshListener(this);
		lstv_message.setOnLoadListener(this);		
		
		initView();		
		if (list.size() == 0) {
			message_text_noinfomation.setVisibility(View.VISIBLE);
		} else {
			lstv_message.setVisibility(View.VISIBLE);
			message_text_noinfomation.setVisibility(View.GONE);
		}
		// listview点击事件
		lstv_message.setOnItemClickListener(listener);
		// listview长按事件
		lstv_message.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				showDelectDialog(position);
				return false;
			}

		});
	}

	// 删除消息对话
	private void showDelectDialog(final int position) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(getString(R.string.hint));
		builder.setMessage(getString(R.string.comfir_delete_message));
		builder.setPositiveButton(getString(R.string.comfir), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Message msg = list.get(position - 1);
				id = msg.getId();
				list.remove(position - 1);
				adapter.notifyDataSetChanged();
				
			}
		});
		builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		builder.create().show();
	}



	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Message msg = list.get(position - 1);
			// Toast.makeText(getApplicationContext(),
			// position + ":" + msg.toString(), 0).show();
			if (msg.getType() == 8) {
				Intent intent = new Intent(getApplicationContext(),
						MessageAddressLocationActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("Message", msg);
				intent.putExtras(bundle);
				startActivity(intent);
			} else if (msg.getType() == 7) {
				TextView message_record_to_plays = (TextView) view
						.findViewById(R.id.message_record_to_plays);
				final AnimationDrawable rocketAnimation = (AnimationDrawable) message_record_to_plays
						.getBackground();

				name = msg.getMsgData();
				name = MutualUtil.GetFileNameFromUrl(name);
//				if(name.contains("upload")){
//					name = name.replace("upload", "");
//				}
//				String[] mName = name.split("/");
				try {
					if (mPlayer.isPlaying()) {
						mPlayer.stop();
						rocketAnimation.selectDrawable(0);
						rocketAnimation.stop();
					} else {
						rocketAnimation.start();
						File file = new File(MyApplication.downPath + name);
						if(!file.exists()){
							Intent intent = new Intent(MESSAGE_RECEIVED_DOENLOAD);
							intent.putExtra(KEY_DOWAN, msg.getMsgData());
							sendBroadcast(intent);		
						}else{
							FileInputStream fie = new FileInputStream(
									MyApplication.downPath + name);	
//							fie.reset();
							mPlayer.reset();
							mPlayer.setDataSource(fie.getFD());
							mPlayer.prepare();
							mPlayer.start();
							mPlayer.setOnCompletionListener(new OnCompletionListener() {
								@Override
								public void onCompletion(MediaPlayer mp) {
									rocketAnimation.selectDrawable(0);
									rocketAnimation.stop();
								}
							});
						}
//						if(file.exists()){
//							Toast.makeText(getApplicationContext(), "无此音乐播放文件", 0).show();
//							rocketAnimation.selectDrawable(0);
//							rocketAnimation.stop();
//							file.mkdir();
//						}			
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	public static final String MESSAGE_RECEIVED_ACTION = "com.blyang.technology.MESSAGE_RECEIVED_ACTION";
	public static final String MESSAGE_RECEIVED_DOENLOAD = "com.blyang.technology.MESSAGE_RECEIVED_DOWNLOAD";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_DOWAN = "doawn";
	public static final String KEY_EXTRAS = "extras";

	private void setCostomMsg(String msg) {
		if (null != text) {
			text.setText(msg);
			text.setVisibility(android.view.View.VISIBLE);
		}
	}

	@SuppressWarnings("unused")
	private void initView() {
		text = (EditText) findViewById(R.id.message_text);
		message_btn = (Button) findViewById(R.id.message_btn1);
		lstv_message = (AutoListView) findViewById(R.id.lstv_message);
		message_text_noinfomation = (TextView) findViewById(R.id.message_text_noinfomation);
		message_btn.setOnClickListener(this);
		body = new Options();
		msgIndex = 0;
		body.setLoginName(loginName);
		body.setMsgIndex(msgIndex);
		body.setPageSize(10);
		loadData(AutoListView.REFRESH);
	}

	@SuppressWarnings("unused")
	private void init() {
		JPushInterface.init(getApplicationContext());
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_btn1:
			init();
			break;

		default:
			break;
		}
	}
	
	private void loadData(final int what) {
		// 这里模拟从服务器获取数据
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				android.os.Message msg = handler.obtainMessage();
				msg.what = what;
				try {
					
					msg.obj = (HttpHelperUtils.GetAndroidUserMessageList(body)).getRows();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			}
		}).start();
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		body = new Options();
		body.setLoginName(loginName);
		body.setMsgIndex(msgIndex);
		body.setPageSize(10);
		loadData(AutoListView.LOAD);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		msgIndex = 0;
		body.setMsgIndex(msgIndex);
		loadData(AutoListView.REFRESH);
	}
	public void back(View v) {
		finish();
	}
	
	private class DeleteTask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				return HttpHelperUtils.DeleteUserMessageById(id);
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
			if(result == -10) {
				quitToLogin();
			}
		}
		
		
	}
}
