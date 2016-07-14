package com.brilyong.technology.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.brilyong.technology.R;
import com.brilyong.technology.entity.Geofence;


/**
 * ��Ӱ�ȫ����
 * 
 * @author Administrator
 * 
 */
public class AddSafeActivity extends BaseUIActivityUtil implements
		OnClickListener {

	public MyLocationListenner myListener = new MyLocationListenner();
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private CircleOptions opt;
	private int radius = 500;// ��ȫ��Ĭ�ϰ뾶
	private GeoCoder mSearch;
	private TextView safe;
	private TextView tv_safeinfo;
	private ImageButton addSafe;
	private ImageButton jianSafe;
	private SeekBar safeRadius;
//	public static Activity sActivity;
	private Geofence mGeofence;
	private LatLng latLng;
	private Button Btaddsafe;
	private LocationClient mLocClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		sActivity = this;
		setContentView(R.layout.add_safe_activity);
		mMapView = (MapView) findViewById(R.id.bmapView);
		Bundle bundle = getIntent().getExtras();
		initViews();
		mMapView.showZoomControls(false);
		mBaiduMap = mMapView.getMap();
		opt = new CircleOptions();
		// ��ʼ����ͼ
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(mOnGetGeoCoder);
		mBaiduMap.setOnMapClickListener(mOnMapClickListener);
		if (bundle != null) {
			mGeofence = (Geofence) bundle.getSerializable("Geofence");
			String location = mGeofence.getData();
			String[] loc = location.split(",");
			latLng = new LatLng(Double.parseDouble(loc[0]),
					Double.parseDouble(loc[1]));
			DrawCircle(latLng, (int) (mGeofence.getRadius()));
			mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
			initDrawCircle(latLng);
			Btaddsafe.setVisibility(View.GONE);
			tv_safeinfo.setText(Math.round(mGeofence.getRadius()) + "m");
			safeRadius.setProgress((int) mGeofence.getRadius());
		} else {
			initLoaction();
		}
		safeRadius.setOnSeekBarChangeListener(mSeekBar);

	}

	// ��ʼ����λ
	private void initLoaction() {
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		// option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	// ��ʼ����ͼ
	private void initDrawCircle(LatLng latlng) {
		MapStatus mMapStatus = new MapStatus.Builder().target(latlng).zoom(16)
				.build();
		// ����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// �Զ�����ʽ���õ�ͼ״̬
		mBaiduMap.animateMapStatus(mMapStatusUpdate);
	}

	// ��ʼ��View
	private void initViews() {
		safe = (TextView) findViewById(R.id.safe_id);
		tv_safeinfo = (TextView) findViewById(R.id.tv_safeinfo);
		addSafe = (ImageButton) findViewById(R.id.add_safe);
		jianSafe = (ImageButton) findViewById(R.id.jian_safe);
		safeRadius = (SeekBar) findViewById(R.id.safe_radius);
		Btaddsafe = (Button) findViewById(R.id.bt_add_asfe);
		addSafe.setOnClickListener(this);
		jianSafe.setOnClickListener(this);
	}

	// ��Բ
	private void DrawCircle(LatLng latLng, int radius) {
		mBaiduMap.clear();
		opt.stroke(new Stroke(2, 0xAA333333));
		opt.center(latLng);
		opt.radius(radius);
		opt.fillColor(Color.argb(60, 51, 51, 51));
		mBaiduMap.addOverlay(opt);
	}

	// ��ȫ�����϶�����
	OnSeekBarChangeListener mSeekBar = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			LatLng latLng = opt.getCenter();
			radius = progress;
			DrawCircle(latLng, progress);
			tv_safeinfo.setText(progress + "m");
		}
	};

	// ������ϸ��ַ �ص�����
	OnGetGeoCoderResultListener mOnGetGeoCoder = new OnGetGeoCoderResultListener() {
		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
			String addr = arg0.getAddress();
			safe.setText(addr);
		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult arg0) {
		}
	};

	// ��ͼ����¼�
	OnMapClickListener mOnMapClickListener = new OnMapClickListener() {

		@Override
		public boolean onMapPoiClick(MapPoi arg0) {
			return false;
		}

		@Override
		public void onMapClick(LatLng latLng) {
			
			DrawCircle(latLng, radius);
			mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
		}
	};

	@Override
	public void onClick(View v) {
		LatLng latLng = opt.getCenter();
		switch (v.getId()) {
		case R.id.add_safe:// �Ӵ�Χ���뾶
			if (radius < 2000) {
				radius += 100;
				DrawCircle(latLng, radius);
				safeRadius.setProgress(radius);
				tv_safeinfo.setText(radius + "m");
			}

			break;
		case R.id.jian_safe:// ����Χ���뾶
			if (radius > 100) {
				radius -= 100;
				DrawCircle(latLng, radius);
				safeRadius.setProgress(radius);
				tv_safeinfo.setText(radius + "m");
			}
			break;
		}

	}

	/**
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null || mMapView == null)
				return;
			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());
			DrawCircle(ll, radius);
			mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
			initDrawCircle(ll);
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	// ȷ�����ð�ȫ����
	public void okSafe(View v) {
		LatLng arg0 = opt.getCenter();
		double latitude = arg0.latitude;
		double longitude = arg0.longitude;
		int Radius = opt.getRadius();
		Intent intent = new Intent(this, SafeAreaInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putDouble("latitude", latitude);
		bundle.putDouble("longitude", longitude);
		bundle.putInt("Radius", Radius);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void back(View v) {
		finish();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// �˳�ʱ���ٶ�λ
		if (mLocClient != null) {
			mLocClient.stop();
		}
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
}
