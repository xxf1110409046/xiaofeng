package com.brilyong.technology.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.brilyong.technology.entity.Message;
import com.brilyong.technology.R;

/**
 * ��Ϣ����λ����Ϣ
 * 
 * @author Administrator
 * 
 */
public class MessageAddressLocationActivity extends Activity {

	private MapView mMapView;
	private GeoCoder mSearch;
	private BaiduMap mBaiduMap;
	private InfoWindow mInfoWindow;
	private ImageView loaction_icon;
	private ImageView electricity_icon;
	private TextView position_info;
	private TextView loaction_mode;
	private TextView loaction_time;
	private View view;
	private Message msg;
	private String time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_loaction_activity);
		initViews();
		mMapView = (MapView) findViewById(R.id.bmapView);
		// ���õ�ͼ�Ƿ���ʾ���ſؼ�
		mMapView.showZoomControls(false);
		mBaiduMap = mMapView.getMap();
		Bundle bundle = getIntent().getExtras();
		msg = (Message) bundle.getSerializable("Message");
		mSearch = GeoCoder.newInstance();
		// ���ص�ַ�ص��ӿ�
		mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
				position_info.setText(arg0.getAddress());
			}

			@Override
			public void onGetGeoCodeResult(GeoCodeResult arg0) {
				// TODO Auto-generated method stub

			}
		});
		loaction();
	}

	// ��ʼ����ͼ������ͼ
	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.bd_loaction_daogin, null);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBaiduMap.hideInfoWindow();
			}
		});
		loaction_icon = (ImageView) view.findViewById(R.id.loaction_icon);
		electricity_icon = (ImageView) view.findViewById(R.id.electricity_icon);
		position_info = (TextView) view.findViewById(R.id.position_info);
		loaction_mode = (TextView) view.findViewById(R.id.loaction_mode);
		loaction_time = (TextView) view.findViewById(R.id.loaction_time);

	}

	// ���õ�ͼ��������Ϣ
	private void setContent() {
		if (msg != null) {
			int typeid = msg.getLocationType();
			int battery = msg.getBattery();
			switch (typeid) {
			case 0:
				loaction_icon.setImageResource(R.drawable.bl_lbs_location);
				loaction_mode.setText(getString(R.string.fuzzy_location));
				break;
			case 1:
				loaction_icon.setImageResource(R.drawable.bl_wifi_location);
				loaction_mode.setText(getString(R.string.fuzzy_location));
				break;
			case 2:
				loaction_icon.setImageResource(R.drawable.bl_gps_location);
				loaction_mode.setText(getString(R.string.exact_location));
				break;
			}
			int batt = (battery / 20);
			if (batt <= 1) {
				electricity_icon.setImageResource(R.drawable.bl_electricity1);
			} else if (batt <= 2) {
				electricity_icon.setImageResource(R.drawable.bl_electricity2);
			} else if (batt <= 3) {
				electricity_icon.setImageResource(R.drawable.bl_electricity3);
			} else if (batt <= 4) {
				electricity_icon.setImageResource(R.drawable.bl_electricity4);
			} else if (batt <= 7) {
				electricity_icon.setImageResource(R.drawable.bl_electricity5);
			}
			date(msg.getGpsTime());
			loaction_time.setText(time);
		}

	}
	// ��ʼ��ʱ��
	private void date(Date date){
		SimpleDateFormat simple_date = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
		time = simple_date.format(date);
	}
//
	private void loaction() {
		// ���õ�ͼ״̬
		LatLng cenpt = new LatLng(msg.getBLat(), msg.getBLng());
		MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(16)
				.build();
		// ����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// �Զ�����ʽ���õ�ͼ״̬
		mBaiduMap.animateMapStatus(mMapStatusUpdate);
		// ��ȡ��ϸ��ַ��Ϣ
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
		// ����Markerͼ��
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.bl_loaction);
		setContent();
		// ����MarkerOption�������ڵ�ͼ�����Marker
		OverlayOptions option = new MarkerOptions().position(cenpt)
				.icon(bitmap);
		// �ڵ�ͼ�����Marker������ʾ
		mBaiduMap.addOverlay(option);
		mInfoWindow = new InfoWindow(view, cenpt, -100);
		mBaiduMap.showInfoWindow(mInfoWindow);
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// setCenter();
				mBaiduMap.showInfoWindow(mInfoWindow);
				return true;
			}
		});
	}

	public void back(View v) {
		finish();
	}
}
