package com.brilyong.technology.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * 校验某个服务是否还活着 
 * serviceName :传进来的服务的名称
 */
public class ServiverUtils {

	public static boolean isRunningSerivcer(Context context, String serviceName) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> run = am.getRunningServices(100);
		for (RunningServiceInfo s : run) {
			String serivcerName = s.service.getClassName();
			if (serivcerName.equals(serviceName)) {
				return true;
			}
		}
		return false;

	}
}
