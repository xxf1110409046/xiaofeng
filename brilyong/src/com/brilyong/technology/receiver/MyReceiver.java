package com.brilyong.technology.receiver;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import cn.jpush.android.api.JPushInterface;

import com.brilyong.technology.activity.MessageCenterActivity;
import com.brilyong.technology.activity.WeiXinActivity;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.httputils.AppConfig;
import com.brilyong.technology.utils.PushExampleUtil;

public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private String user_id;
	private String add_date;
	private String msg_data;
	private String send_count;
	private String message_type;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		String content = bundle.getString(JPushInterface.EXTRA_ALERT);
		int IsVoice = 0;
		int type = 0;
		try {
			if (!TextUtils.isEmpty(extras)) {
				JSONObject js = new JSONObject(extras);		
				IsVoice = js.optInt("voice");
				JSONObject obj = new JSONObject(js.getString("data"));
				type = obj.getInt("Type");
				// String data=js.optString("data");
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		String a = message;
		String b = extras;
		String aa = content;
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
//			dbHelper = new MessageSqlite(context, "stu_db", null, 1);
			Bundle mbundle = intent.getExtras();
			String Mymessage = mbundle.getString(JPushInterface.EXTRA_MESSAGE);
			String mMessage;
			try {
				mMessage = new JSONObject(Mymessage).getString("op");
				if ("devlogout_22222222222223".equals(mMessage)) {
				  Intent deveceMiss = new Intent(AppConfig.DEVICEMISS);	
				  context.sendBroadcast(deveceMiss);
				  Acount.getCurrentDevice().setIsOnline(0);
				}else if("devlogin_22222222222223".equals(mMessage)){
				  Intent deveceOnline = new Intent(AppConfig.DEVICEONLINE);	
				  context.sendBroadcast(deveceOnline);
				  Acount.getCurrentDevice().setIsOnline(1);
				}else if("loc_22222222222223".equals(mMessage)){
				  Intent deveceOnline = new Intent(AppConfig.DEVICEONLINE);	
				  context.sendBroadcast(deveceOnline);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			processCustomMessage(context, bundle);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			int notifactionId = bundle
					.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//			dbHelper = new MessageSqlite(context, "stu_db", null, 1);
			processCustomMessage(context, bundle);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
		
			if (IsVoice == 1) {
				bundle.getInt(JPushInterface.EXTRA_EXTRA);
				Intent i = new Intent(context, WeiXinActivity.class);
				i.putExtras(bundle);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(i);

			} else {
				bundle.getInt(JPushInterface.EXTRA_EXTRA);
				Intent i = new Intent(context, MessageCenterActivity.class);
				i.putExtras(bundle);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				if(type == 17){
					Intent i_notification = new Intent(AppConfig.MESSAGENOTIFICATION);
					context.sendBroadcast(i_notification);
				}
				context.startActivity(i);
			}

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction())) {
		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
				.getAction())) {
			boolean connected = intent.getBooleanExtra(
					JPushInterface.EXTRA_CONNECTION_CHANGE, false);

		}
	}

	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	private void processCustomMessage(Context context, Bundle bundle) {
		if (true) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(
					MessageCenterActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MessageCenterActivity.KEY_MESSAGE, message);
			if (!PushExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(MessageCenterActivity.KEY_EXTRAS,
								extras);
					}
				} catch (JSONException e) {
				}
			}
			context.sendBroadcast(msgIntent);
		}
	}

	private static final int MSG_Send = 1001;
	
}
