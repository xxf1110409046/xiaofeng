package com.brilyong.technology.activity;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;



import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.R;

import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class PedometerTaskSend extends BaseUIActivityUtil implements OnClickListener{
	//radioGroup
	private RadioGroup group;
	private RadioButton btn_0;
	private RadioButton btn_1;
	private RadioButton btn_2;
	private ColorStateList cs1;
	private ColorStateList cs2;
	//秒表计时器
	private TextView text_start;
	private Button start_time;
	private Button stop_time;
//	private LinearLayout reset_time;
	private long time = 0;
	private long startTime;
	private Handler hand;
	private int state = 0;
	private int type_1;
	private int type_2;
	private int type_3;
    //记录状态 
	private  static int STATE_STOP = 0;
	private  static int STATE_PAUSE = 2;
	private Timer timer;
	private int finishStepCount; 
	private TextView text_finishStep;
	private Task task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.radioactivity);
		initView();
		timer = new Timer();
		task = new Task();
		Log.i("TAG", "***********oncreate");
		
		try {
			timer.scheduleAtFixedRate(task, 2000, 3000);

		} catch (Exception e) {
			// TODO: handle exception
			Log.i("TAG", "**"+e.toString());
		}
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case R.id.btn_0:
					btn_0.setTextColor(cs2);
					btn_1.setTextColor(cs1);
					btn_2.setTextColor(cs1);
					type_1 = 0;
					hand.sendEmptyMessage(2);
					break;
				case R.id.btn_1:
					btn_0.setTextColor(cs1);
					btn_1.setTextColor(cs2);
					btn_2.setTextColor(cs1);
					type_1 = 1;
					hand.sendEmptyMessage(3);
					break;
				case R.id.btn_2:
					btn_0.setTextColor(cs1);
					btn_1.setTextColor(cs1);
					btn_2.setTextColor(cs2);
					type_1 = 2;
					hand.sendEmptyMessage(4);
					break;
				}
			}
		});
		//定义handler
		hand = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					start_time.setVisibility(View.GONE);
					stop_time.setVisibility(View.VISIBLE);
					break;
				case 1:
					start_time.setVisibility(View.VISIBLE);
					stop_time.setVisibility(View.GONE);
					break;
				case 2:
					new PeSendTask().execute();
					break;
				case 3:
					new PeSendTask().execute();
					break;
				case 4:
					new PeSendTask().execute();
					break;
				case 5:
					text_finishStep.setText(finishStepCount + "");
					break;
				default:
					break;
				}
			}
		};
	}
	

	
	//初始化视图
	private void initView(){
		group = (RadioGroup) findViewById(R.id.group);
		btn_0 = (RadioButton) findViewById(R.id.btn_0);
		btn_1 = (RadioButton) findViewById(R.id.btn_1);
		btn_2 = (RadioButton) findViewById(R.id.btn_2);
		text_finishStep = (TextView) findViewById(R.id.text_finishStep);
		cs1 = getResources().getColorStateList(android.R.color.black);
		cs2 = getResources().getColorStateList(R.color.title);	
		text_start = (TextView) findViewById(R.id.text_start_minute);
		start_time = (Button) findViewById(R.id.start_time);
		start_time.setOnClickListener(this);
		stop_time = (Button) findViewById(R.id.stop_time);
		stop_time.setOnClickListener(this);
	}
	
	

	/**
	 * 得到一个格式化的时间
	 * 
	 * @param time
	 * 			时间 毫秒
	 * @return 分：秒：毫秒
	 */
	
	private class PeSendTask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.Dev_dingtong_SendPedometer(Acount.getCurrentDevice().getId(),type_1);
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
			switch (result) {
			case 0:
				Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
				break;
			case -1:
				Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
				break;

			default:
				break;
			}
		}
	}
	
	//获取计步器下发的线程
  class Task extends TimerTask{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String name;
		try {
			name = HttpHelperUtils.GetStepDataByDeviceId(Acount.getCurrentDevice().getId());
			JSONObject obj = new JSONObject(name);
			finishStepCount = obj.getInt("FinishStepCount");
			hand.sendEmptyMessage(5);
			Log.v("TAG", "*****214*****");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	  
  }

@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	
}

@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	if(task != null){
		task.cancel();
		task = null;
		Log.v("TAG", "**********" + "onDestroy" + "被干掉了");
	}
	timer.cancel();
	super.onDestroy();
}
	
}
