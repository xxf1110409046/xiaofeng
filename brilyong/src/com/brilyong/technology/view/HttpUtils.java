package com.brilyong.technology.view;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.brilyong.technology.entity.Acount;


import android.content.SharedPreferences;
import android.os.AsyncTask;

public class HttpUtils {
    private SharedPreferences sp;
	private String filePath;
	
	public HttpUtils(String filePath,SharedPreferences sp) {
		super();
		this.filePath = filePath;
		this.sp = sp;
	}

	private class task extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				
				HttpPost httppost = new HttpPost(
						"http://gps.szwearable.com/OpenApi/AppHandler.ashx");
				File imageFile = new File(filePath);
				MultipartEntity multipartEntity = new MultipartEntity();
				FileBody file = new FileBody(imageFile);
				multipartEntity.addPart("File1", file);
				StringBody par1 = new StringBody(Acount.getCurrentDevice().getId().toString());
				StringBody par = new StringBody("dev_w_voice");
				StringBody parLoginName = new StringBody(sp.getString("LoginName", null).toString());
				multipartEntity.addPart("DeviceId", par1);
				multipartEntity.addPart("action_type", par);
				multipartEntity.addPart("FLoginName", parLoginName);
				httppost.setEntity(multipartEntity);
				DefaultHttpClient dhc = new DefaultHttpClient();
				HttpResponse httpResponse = dhc.execute(httppost);
				if (httpResponse.getStatusLine().getStatusCode() != 404) {
					String result;
					result = EntityUtils.toString(httpResponse.getEntity());
					if ("www".equals(result)) {
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public void start() {
		new task().execute();
	}

}
