package com.brilyong.technology.activity;

import java.util.List;

import android.app.Notification.Action;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.brilyong.technology.entity.Bluth;
import com.brilyong.technology.service.BLEService;
import com.brilyong.technology.utils.RBLGattAttributes;
import com.brilyong.technology.utils.ServiverUtils;
import com.brilyong.technology.R;

/**
 * ·À¶ªÌáÐÑÄ£¿é
 * 
 * @author Administrator
 * 
 */
public class PreventThrowReminderActivity extends BaseUIActivityUtil {


	private BluetoothAdapter mBluetoothAdapter;
	private static final int REQUEST_ENABLE_BT = 1;
	private BLEService mBLEService;
	private AnimationDrawable anim;
	private ListView list_bluth;
	private Button btn_bluth;
	private String service = "com.blyang.technology.service.BLEService";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prevent_reminder_activity);	
		initViews();
		btn_bluth = (Button) findViewById(R.id.btn_bluth);
		btn_bluth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent intent = new Intent("STOP");
			   sendBroadcast(intent);
			}
		});
		registerReceiver(receiver, makeGattUpdateIntentFilter());
	}
	
	private void initViews() {
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	private IntentFilter makeGattUpdateIntentFilter() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(RBLGattAttributes.ACTION_GATT_CONNECTED);
		intentFilter.addAction(RBLGattAttributes.ACTION_GATT_DISCONNECTED);
		intentFilter.addAction(RBLGattAttributes.STARTALARMANIMATION);
		intentFilter.addAction(RBLGattAttributes.STOPALARMANIMATION);
		return intentFilter;
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			final String action = arg1.getAction();
			if (RBLGattAttributes.ACTION_GATT_CONNECTED.equals(action)) {
			} else if (RBLGattAttributes.ACTION_GATT_DISCONNECTED
					.equals(action)) {
				if (RBLGattAttributes.isAnima) {
					stop();
				}
			} else if (RBLGattAttributes.STARTALARMANIMATION.equals(action)) {
			} else if (RBLGattAttributes.STOPALARMANIMATION.equals(action)) {
				stop();
			}

		}
	};
	private Button alarm;

	public void stop() {
		anim.stop();
	}

	public void start() {
		anim.start();
	}
}
