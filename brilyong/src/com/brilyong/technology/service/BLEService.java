package com.brilyong.technology.service;


import java.util.List;
import java.util.UUID;

import com.brilyong.technology.httputils.AppConfig;
import com.brilyong.technology.utils.DeviceUtils;
import com.brilyong.technology.utils.RBLGattAttributes;
import com.brilyong.technology.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class BLEService extends Service {
	private final static String TAG = BLEService.class.getSimpleName();
	public static int progress = 50;
	protected static final long SCAN_PERIOD = 6000;
	public final static UUID IMMEDIATE_ALERT = UUID
			.fromString(RBLGattAttributes.IMMEDIATE_ALERT);
	public final static UUID ALERT_LEVEL = UUID
			.fromString(RBLGattAttributes.ALERT_LEVEL);
	private byte[] alarm = new byte[] { 0x02 };
	private byte[] stopAlarm = new byte[] { 0x00 };
	private MediaPlayer mediaPlayer;
	private Vibrator vibrator;
	public static final String ACTION_FOUND = "android.bluetooth.device.action.FOUND";
	public static final String ACTION_ACL_CONNECTED = "android.bluetooth.device.action.ACL_CONNECTED";
	public static final String ACTION_ACL_DISCONNECT_REQUESTED = "android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED";
	public static final String ACTION_ACL_DISCONNECTED = "android.bluetooth.device.action.ACL_DISCONNECTED";
	
	@Override
	public void onCreate() {
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		registerReceiver(rexeiver, makeGattUpdateIntentFilter());
//		initialize();
//		scanLeDevice();
		initPlay();
		super.onCreate();
	}

	private IntentFilter makeGattUpdateIntentFilter() {
		IntentFilter intentFilter = new IntentFilter();	
		intentFilter.addAction(ACTION_FOUND);
		intentFilter.addAction(ACTION_ACL_CONNECTED);
		intentFilter.addAction(ACTION_ACL_DISCONNECT_REQUESTED);
		intentFilter.addAction(ACTION_ACL_DISCONNECTED);
		intentFilter.addAction("STOP");
		intentFilter.addAction(AppConfig.FILE_NOTIFICATION);
		return intentFilter;
	}

	boolean isAlarm;


	// ����
	public void Alarm() {
		RBLGattAttributes.alarmState = true;
	}

	public void stopAlarm() {
		RBLGattAttributes.alarmState = false;
	}
	private BroadcastReceiver rexeiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			 	String action = intent.getAction();
		        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		        //Toast.makeText(context, "����״̬�ı�㲥 !", Toast.LENGTH_LONG).show();  
		        Log.i("TAG---BlueTooth","���յ�����״̬�ı�㲥����");
		        if(BluetoothDevice.ACTION_FOUND.equals(action)) 
		        {	
		        	  
//		            Toast.makeText(context, device.getName() + " �豸�ѷ��֣���", Toast.LENGTH_LONG).show();
//		            btMessage=device.getName()+"�豸�ѷ��֣���";
		        }
		        else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) 
		        {	
		        	if(mediaPlayer.isPlaying()){
		        		stop();
		        	}  
//		            Toast.makeText(context, device.getName() + "������", Toast.LENGTH_LONG).show();
//		            btMessage=device.getName()+"�豸�����ӣ���";
		        }
		        
		        else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) 
		        {	
		        	 
//		            Toast.makeText(context, device.getName() + "���ڶϿ��������ӡ�����", Toast.LENGTH_LONG).show();
//		            btMessage=device.getName()+"���ڶϿ��������ӡ�����";
		        }
		        else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) 
		        {	
//		            Toast.makeText(context, device.getName() + "���������ѶϿ�������", Toast.LENGTH_LONG).show();
//		            btMessage=device.getName()+"���������ѶϿ�����";	  
		        	if(mediaPlayer.isPlaying()){
		        		stop();
		        	}
		        	play();
		        	hand.sendEmptyMessage(MUSICSTOP);
		        } 
		        else if("STOP".equals(action)){
		        	if(mediaPlayer.isPlaying()){
		        		stop();
		        	}
		        }
		        else if(AppConfig.FILE_NOTIFICATION.equals(intent.getAction())){
					if(mediaPlayer.isPlaying()){
						mediaPlayer.stop();
					}
				}

		}

	};

	public void initPlay() {
		mediaPlayer = MediaPlayer.create(this, R.raw.a);
	}

	// ���ű�������
	public void play() {
		mediaPlayer.start();
		long[] pattern = { 500, 1000, 500, 1000 };
		// -1��ʾ���ظ�, �������-1, ����ĳ�1, ��ʾ��ǰ�����long������±�Ϊ1��Ԫ�ؿ�ʼ�ظ�.
		vibrator.vibrate(pattern, -1);
	}

	// ֹͣ��������
	public void stop() {
		mediaPlayer.pause();
		mediaPlayer.seekTo(0);
		vibrator.cancel();
	}

	@Override
	public void onDestroy() {
		mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayer = null;
		unregisterReceiver(rexeiver);
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		stop();
		return super.onUnbind(intent);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public class LocalBinder extends Binder {
		public BLEService getService() {
			return BLEService.this;
		}
	}
    private static final int MUSICSTOP = 0;
	private final IBinder mBinder = new LocalBinder();
    
	private Handler hand = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MUSICSTOP:
				NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
				Intent intent = new Intent(AppConfig.FILE_NOTIFICATION);
				intent.putExtra("notification", 1);
				PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
				Notification.Builder builder = new Notification.Builder(getApplicationContext())
						.setContentIntent(pIntent).setSmallIcon(R.drawable.bl_logo)
						.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.bl_logo))
						.setWhen(System.currentTimeMillis()).setTicker("�����ѶϿ�����")
						.setAutoCancel(true)
						.setContentTitle("�����ѶϿ�����")
						.setContentText("�������Ϣ�ɹرձ�������");
				Notification notification = builder.build();
		        manager.notify(100, notification);
				break;
			default:
				break;
			}
			
		};
	};

}
