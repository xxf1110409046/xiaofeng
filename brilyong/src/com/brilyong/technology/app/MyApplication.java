package com.brilyong.technology.app;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.R.string;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.CheckUpdateResult;
import com.brilyong.technology.httputils.HttpHelperUtils;

public class MyApplication extends Application {
	public static int versionName;
	private boolean isDownload;
	private static MyApplication instance;
	public static String downTitle;
	private List<Activity> activityList = new ArrayList<Activity>();
	// ���ذ���װ·��
	public final static String savePath = "/sdcard/blyang";
	// ���ذ�װ�ļ���
	public static String downSaveFileName = "/blyang.gps.apk";
	public static String downPath = "/sdcard/blyang/";
	private String pac;
	@Override
	public void onCreate() {
		super.onCreate();
		// ��ʹ�� SDK �����֮ǰ��ʼ�� context ��Ϣ������ ApplicationContext
		SDKInitializer.initialize(this);
		instance = this;
		getName();
		new CheckTask().execute();
	}

	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	public synchronized static MyApplication getInstance() {
		return instance;
	}

	/**
	 * ��ȡ�汾����Ϣ
	 * 
	 * @return
	 */
	public void getName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(this.getPackageName(), 0);
			versionName = info.versionCode;
			pac=info.packageName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class CheckTask extends AsyncTask<Void, Void, CheckUpdateResult> {

		@Override
		protected CheckUpdateResult doInBackground(Void... params) {
			try {
				return HttpHelperUtils.CheckUpdate(pac,MyApplication.versionName);
			} catch (ConnectException e) {
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(CheckUpdateResult result) {
			Acount.setaCheckUpdateResult(result);
//			Log.v("TAG", pac + "buyao" + MyApplication.versionName + result.isUpdate());
			if("null".equals(result)){
				Log.v("TAG", pac + "buyao" + MyApplication.versionName +  result.isUpdate() + "zhu......");
			}
			super.onPostExecute(result);
		}

	}

	public void addActivity(Activity activity) {
		if (!activityList.contains(activity)) {
			activityList.add(activity);
		}
	}

	public boolean removeActivity(Activity activity) {
		boolean flag = activityList.remove(activity);
		return flag;
	}

	public List<Activity> getActivityList() {
		return this.activityList;
	}

	/**
	 * �˳�����activity��
	 */
	public void exit() {
		try {
			for (Activity activity : activityList) {
				if (activity != null) {
					activity.finish();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getLanguage() {
		// TODO Auto-generated method stub
		Locale curLocale = getResources().getConfiguration().locale;	
		return curLocale.getLanguage();
	}

	public String getImei() {
		 String Imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
				.getDeviceId();

		return Imei;
	}
}
