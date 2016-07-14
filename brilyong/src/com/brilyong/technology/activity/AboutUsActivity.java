package com.brilyong.technology.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.CheckUpdateResult;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.DialogUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.brilyong.technology.R;


/**
 * 关于我们
 * 
 * @author Administrator
 * 
 */
public class AboutUsActivity extends BaseUIActivityUtil implements OnClickListener {
	private CheckUpdateResult aCheckUpdateResult;
	private Dialog dialog;
	private ProgressDialog mpDialog;
	private int progress;
	private int lastRate = 0;
	private boolean canceled;
	private String packge_name;
	private TextView version_name;
	private RelativeLayout call_me;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us_activity);
		version_name = (TextView) findViewById(R.id.version_name);
		RelativeLayout official_website = (RelativeLayout) findViewById(R.id.official_website);
		RelativeLayout official_weixin = (RelativeLayout) findViewById(R.id.official_weixin);
		call_me = (RelativeLayout) findViewById(R.id.call_me);
		official_website.setOnClickListener(this);
		official_weixin.setOnClickListener(this);
		call_me.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.official_website:
			showWeixinDialog("wangzhan");
			break;
		case R.id.official_weixin:
			showWeixinDialog("weixin");
			break;
		case R.id.call_me:
			Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"13316818231"));
			startActivity(intent);
			break;
		}	
	}
	
	//官方微信
		private void showWeixinDialog(String msg) {
			
			View view = View.inflate(this, R.layout.official_weixin_dialog, null);
			Button cancel = (Button) view.findViewById(R.id.weixin_cancel);	
			ImageView wangzhan = (ImageView) view.findViewById(R.id.wangzhan);
			ImageView weixin = (ImageView) view.findViewById(R.id.weixin);
			if("weixin".equals(msg)){
				
				weixin.setVisibility(view.VISIBLE);
			}
			else if("wangzhan".equals(msg)){
				
				wangzhan.setVisibility(view.VISIBLE);
			}
			final Dialog dialog = new Dialog(this, R.style.dw_dialog);
			dialog.setContentView(view, new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT));// 设置布局
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
		}		
		public void back(View v){
			finish();
		}
}
