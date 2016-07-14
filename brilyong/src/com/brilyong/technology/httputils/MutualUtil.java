package com.brilyong.technology.httputils;

import com.baidu.platform.comapi.map.C;
import com.brilyong.technology.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

public class MutualUtil {


	public static int getUrlType(String url) {

		return url.endsWith(".amr") ? 1 : 0;
	}

	public static String GetFileNameFromUrl(String url) {

		if (getUrlType(url) == 1) {
			String[] m = url.split("/");
			return m[2];
		} else {
			String[] m = url.split("&f_name=");
			return m[1];
		}
	}
	
	public static void DialogDismiss(final Dialog dialog){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.dismiss();
			}
			
		};
		new Thread(runnable).start();
	}
	
	public static void CreateNotification(Context context){
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(AppConfig.FILE_NOTIFICATION);
		intent.putExtra("notification", 1);
		PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		Notification.Builder builder = new Notification.Builder(context)
				.setContentIntent(pIntent).setSmallIcon(R.drawable.bl_logo)
				.setLargeIcon(BitmapFactory.decodeResource(null, R.drawable.bl_logo))
				.setWhen(System.currentTimeMillis()).setTicker("防脱落报警")
				.setAutoCancel(true)
				.setContentTitle("防脱落报警")
				.setContentText("点击此消息可关闭报警铃声");
		Notification notification = builder.build();
        manager.notify(100, notification);
		
	}


	  
}
