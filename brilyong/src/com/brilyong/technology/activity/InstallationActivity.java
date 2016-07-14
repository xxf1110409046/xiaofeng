package com.brilyong.technology.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.brilyong.technology.httputils.AppConfig;
import com.brilyong.technology.R;


public class InstallationActivity extends BaseUIActivityUtil {

	private WebView webView;
	private ProgressBar progressBar  ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.installation_activity);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		webView = (WebView) findViewById(R.id.webView);
		webView.loadUrl(AppConfig.URL+"/Login/Install");
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				progressBar.setProgress(newProgress);
				progressBar.postInvalidate();
			}
		});
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				progressBar.setVisibility(View.GONE);
			}
		});
	}

	public void back(View v) {
		finish();
	}
}
