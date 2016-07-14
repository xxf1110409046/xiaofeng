package com.brilyong.technology.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.brilyong.technology.R;
import com.brilyong.technology.entity.Acount;

import com.zxing.encoding.EncodingHandler;

/**
 * 基本信息
 * 
 * @author Administrator
 * 
 */
public class BasicInfoActivity extends BaseUIActivityUtil {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_information_activity);
		ImageView imgCode = (ImageView) findViewById(R.id.qr_code);
		String contentString = Acount.getCurrentDevice().getId();
		try {
			if (!contentString.equals("")) {
				Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
						contentString, 350);
				imgCode.setImageBitmap(qrCodeBitmap);
			} else {
				Toast.makeText(this, getString(R.string.text_not_null), Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void back(View v) {
		finish();
	}
}
