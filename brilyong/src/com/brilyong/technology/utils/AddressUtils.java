package com.brilyong.technology.utils;

import android.os.AsyncTask;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.brilyong.technology.adapter.MessageAdapter.ViewHolder;

public class AddressUtils extends AsyncTask<Void, Void, String> {
	private double latitude;
	private double longitude;
	private ViewHolder holder;
	private GeoCoder mSearch;

	public AddressUtils(double latitude, double longitude, ViewHolder holder) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.holder = holder;
		mSearch = GeoCoder.newInstance();
	}

	@Override
	protected String doInBackground(Void... params) {
		LatLng latlng = new LatLng(latitude, longitude);
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latlng));
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
				holder.message_record_to_play.setText(arg0.getAddress());
			}

			@Override
			public void onGetGeoCodeResult(GeoCodeResult arg0) {
				// TODO Auto-generated method stub

			}
		});
		super.onPostExecute(result);
	}
}
