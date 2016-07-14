package com.brilyong.technology.activity;

import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.net.nsd.NsdManager.RegistrationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.brilyong.technology.activity.BybeDetailsActivity;
import com.brilyong.technology.activity.HomeBaiduActivity;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.entity.Location;
import com.brilyong.technology.httputils.AppConfig;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.utils.DialogUtils;
import com.brilyong.technology.utils.PhoneListenTask;
import com.brilyong.technology.utils.PopupWindowUtils;
import com.brilyong.technology.utils.PopupWindowUtils.CurrentDevice;
import com.brilyong.technology.view.CalendarView;
import com.brilyong.technology.view.CalendarView.OnItemClickListener;
import com.brilyong.technology.R;


/**
 * 地图主页??
 * 
 * @author Administrator
 * 
 */
public class HomeBaiduActivity extends BaseUIActivityUtil implements
		OnClickListener {
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private ImageView show_maplayer;
	boolean isNomer = true;
	private SlidingMenu mMenu;
	// 当前设备
	private Device aDevice;
	private InfoWindow mInfoWindow;
	private View view;
	private LatLng cenpt;
	private ImageView loaction_icon;// 定位类型图标
	private ImageView electricity_icon;// 电量大小类型
	// 定位模式
	private TextView loaction_mode;
	// 位置信息
	private TextView position_info;
	// 定位时间
	private TextView loaction_time;
	private GeoCoder mSearch;
	private Timer timer = new Timer();
	// 历史轨迹经纬度集
	private ArrayList<Location> list;
	// 用户????的设??
	private ArrayList<Device> listDevice;
	private TextView tv_baby_name;// 设备名称
	private String time;// 定时??
	private ImageButton selection_date;// 选择历史轨迹日历
	private long mExitTime;
	private PopupWindowUtils mPopupWindowUtils;
	public static Activity mActivity;
	private boolean threadRuning;
	
	private LinearLayout btn_phone;
	private LinearLayout btn_wei;
	private LinearLayout btn_uni;
	private LinearLayout btn_dui;
	private LinearLayout btn_alarm;
	private LinearLayout btn_SOS;
	private LinearLayout btn_set;
	private LinearLayout btn_our;
	private LinearLayout btn_quit;
	private LinearLayout btn_school;
	private FrameLayout ll_image;
	private int index = 0;
	private boolean flag = true;
	private boolean orbit;
	private List<LatLng> lsitt;
	private TimerTk timer_task;
	private DialogUtils dialog_util = new DialogUtils(this);
	private Dialog dialog_phone;
	private String phone;
	private RelativeLayout mRelativeLayout;
	private ImageView cancelImage;
	private static String GETLOCATION = "getlocation";
	private static String GETLOCATIONTYPE = "getlocationtype";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mActivity = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_baidu_activity);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		orbit = sp.getBoolean("orbit", false);
		list = new ArrayList<Location>();
		listDevice = new ArrayList<Device>();
		mMapView = (MapView) findViewById(R.id.bmapView);
		show_maplayer = (ImageView) findViewById(R.id.show_maplayer);
		listDevice = Acount.getDevices();// 获取当前用户????设备
		initButton();
		initPopupWindow();// 初始化PopupWindow
		initViews();// 初始化地图弹出视??
		deviceId = sp.getInt("id", 0);
		if (listDevice != null && listDevice.size() > 0) {
			if(deviceId > listDevice.size() - 1){
				Acount.setCurrentDevice(listDevice.get(0));
			}else{
				Acount.setCurrentDevice(listDevice.get(deviceId));
			}
			tv_baby_name.setText(Acount.getCurrentDevice().getName());
		}else{
			showRecoverFactory();
		}
		isOnline();//判断设备是否在线
		// 设置地图是否显示缩放控件
		mMapView.showZoomControls(false);
		mBaiduMap = mMapView.getMap();
		// 切换地图??
		show_maplayer.setOnClickListener(this);
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		// 返回地址回调接口
		mSearch.setOnGetGeoCodeResultListener(listener);
		// 获取当前设备信息
		getloct(GETLOCATION);
		selection_date.setOnClickListener(this);
		mBaiduMap
		.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				if(list.size() > 0){
					Location date = list.get(marker.getZIndex());
					String sa = Dataa(date.getGpsTime());
					setContent(date.getBattery(), sa,date.getLocationType());
					LatLng LatLng = marker.getPosition();
					mSearch.reverseGeoCode(new ReverseGeoCodeOption()
							.location(LatLng));
					mInfoWindow = new InfoWindow(view,
							marker.getPosition(), -100);
					mBaiduMap.showInfoWindow(mInfoWindow);
					return true;	
				}
				return true;
			}
		});	
	}
			// 定时??每隔????钟定位一??
		 class TimerTk extends TimerTask{
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Log.i("TAG", "******198");
							mBaiduMap.clear();
						}
					});
					getloct(GETLOCATION);
				}
	};
	

	private void initButton(){
		btn_phone = (LinearLayout) findViewById(R.id.btn_phone);
		btn_wei = (LinearLayout) findViewById(R.id.btn_wei);
		btn_uni = (LinearLayout) findViewById(R.id.btn_uni);
		btn_dui = (LinearLayout) findViewById(R.id.btn_dui);
		btn_school = (LinearLayout) findViewById(R.id.btn_school);
		btn_alarm = (LinearLayout) findViewById(R.id.btn_alarm);
		btn_set = (LinearLayout) findViewById(R.id.btn_set); 
		btn_our = (LinearLayout) findViewById(R.id.btn_our);
		ll_image = (FrameLayout) findViewById(R.id.ll_image);
		mRelativeLayout = (RelativeLayout) findViewById(R.id.equipment_miss);
		cancelImage = (ImageView) findViewById(R.id.cancel_offline);
		cancelImage.setOnClickListener(this);
		ll_image.setOnClickListener(this);
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		
	}	
	
	//判断设备是否在线
	public void isOnline(){
		if(Acount.getCurrentDevice() != null){
			if(Acount.getCurrentDevice().getIsOnline() == 1){
				mRelativeLayout.setVisibility(View.GONE);
			}else if(Acount.getCurrentDevice().getIsOnline() == 0){
				mRelativeLayout.setVisibility(View.VISIBLE);
			}
		}
	}
	// 详细地址回调方法
	OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
			String addr = arg0.getAddress();
			Message msg = handler.obtainMessage();
			msg.obj = addr;
			msg.what = 0;
			handler.sendMessage(msg);
		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult arg0) {
		}
	};

	// 初始化PopupWindow
	private void initPopupWindow() {
		int mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		int mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
		mPopupWindowUtils = new PopupWindowUtils(this, listDevice, sp,
				mScreenWidth, mScreenHeight) {
			@Override
			public void initBaiMap() {
					mBaiduMap.clear();
					getloct(GETLOCATION);
			}
		};
	}

	// 初始化地图弹出视??
	private void initViews() {
		selection_date = (ImageButton) findViewById(R.id.selection_date);
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
		tv_baby_name = (TextView) findViewById(R.id.tv_baby_name);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				loaction();
				break;
			case 0:
				position_info.setText(msg.obj.toString());
				break;
			case 3:
				final Location s = (Location)msg.obj;
				LatLng latlng = new LatLng(s.getBLat(), s.getBLng());
				lsitt.add(latlng);
				String sa = Dataa(s.getGpsTime());
				setCenter(latlng);
				// 构建Marker图标
				BitmapDescriptor bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.bl_loaction);
				orbit = sp.getBoolean("orbit", false);
				if (orbit) {
					if (lsitt.size() > 1) {
						PolylineOptions op = new PolylineOptions();
						op.color(Color.BLUE);
						op.width(5);
						op.points(lsitt);
						mBaiduMap.addOverlay(op);		
					}
			}
			setContent(s.getBattery(), sa, s.getLocationType());
			// 构建MarkerOption，用于在地图上添加Marker
			OverlayOptions option = new MarkerOptions().position(
					latlng).icon(bitmap);
			// 在地图上添加Marker，并显示
			mBaiduMap.addOverlay(option).setZIndex(index++);
			mSearch.reverseGeoCode(new ReverseGeoCodeOption()
					.location(latlng));
			mInfoWindow = new InfoWindow(view, latlng, -100);
			mBaiduMap.showInfoWindow(mInfoWindow);
		    break;
		    case 4:
		    	mRelativeLayout.setVisibility(View.VISIBLE);
		    	break;
		    case 5:
		    	mRelativeLayout.setVisibility(View.GONE);
		    	break;
		    case 6:
		    	if(getGiveLocationState()){
		    		setGiveLocationState();
		    		if(delayTimer != null){
		    			delayTimer.cancel();
		    			delayTimer = null;
		    		}
		    		agineLocation();
		    		
		    	}
		    	break;
		    case 7:
		    	if(getGiveLocationState()){
					setGiveLocationState();
					agineLocation();
				}
		    	break;
			default:
				break;
			}

		}

	};

	// 设置地图弹出层信??
	
		private void setContent(int battery, String text, int typeid) {
		int batt = (battery / 20);
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
		switch (batt) {
		case 0:
		case 1:
			electricity_icon.setImageResource(R.drawable.bl_electricity1);
			break;
		case 2:
			electricity_icon.setImageResource(R.drawable.bl_electricity2);
			break;
		case 3:
			electricity_icon.setImageResource(R.drawable.bl_electricity3);
			break;
		case 4:
			electricity_icon.setImageResource(R.drawable.bl_electricity4);
			break;
		case 5:
		case 6:	
		case 7:
			electricity_icon.setImageResource(R.drawable.bl_electricity5);
			break;
		default:
			break;
		}
		loaction_time.setText(text);
	}

	// 定位
	private void setCenter(LatLng latlng) {
		// 定义地图�??
		MapStatus mMapStatus = new MapStatus.Builder().target(latlng).zoom(16)
				.build();
		// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 以动画形式设置地图状??
		mBaiduMap.animateMapStatus(mMapStatusUpdate);
	}

	private void loaction() {
		// 设置地图�??
		cenpt = new LatLng(aDevice.BLat, aDevice.BLng);
		setCenter(cenpt);
		// 获取详细地址信息
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.bl_loaction);
		setContent(aDevice.getBattery(),Dataa(aDevice.getGpsTime()),aDevice.getPositionType());
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(cenpt)
				.icon(bitmap);
		// 在地图上添加Marker，并显示
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

	// 格式化时??
	public void Data(Date date) {
		SimpleDateFormat dateformat1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		time = dateformat1.format(date);
	}

	// 格式化时??
	public String Dataa(Date date) {
		SimpleDateFormat dateformat1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateformat1.format(date);
	}

	// 获取当前设备信息
	private void getloct(String message) {
		LoginTask mLoginTask = new LoginTask(message);
		mLoginTask.execute();
	}

	/**
	 * 异步任务提交数据
	 * 
	 * @author Administrator
	 * 
	 */
	private class LoginTask extends AsyncTask<String, Void, Boolean> {
		
		private String message;
		public LoginTask(String message) {
			// TODO Auto-generated constructor stub
			this.message = message;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			try {
				aDevice = HttpHelperUtils.GetDeviceById(Acount
						.getCurrentDevice().getId());
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			DialogUtils.dismissDialog();
			if (result) {
				if("getlocation".equals(message)){
					Data(aDevice.getGpsTime());
					loaction();
				}else if("getlocationtype".equals(message)){
					String timeMessage = time;
					Data(aDevice.getGpsTime());
					if(time.equals(timeMessage)){
						Toast.makeText(getApplicationContext(), "获取定位失败", Toast.LENGTH_SHORT).show();
						loaction();
					}else{
						loaction();
					}
					setGiveLocationState();
				}
				
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * 切换图层 
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.selection_date:
			if(listDevice.size() > 0){
				showDateDialog(v);
			}else{
				
			}
			
//			 guijihuifang();
			break;
		case R.id.ll_image:
			if(listDevice.size() > 0){
				Intent i = new Intent(HomeBaiduActivity.this,BybeDetailsActivity.class);
				startActivity(i);
			}else{
				dialog_util.showRecoverFactory();
			}
			break;
		case R.id.show_maplayer:// 切换地图图层
			if (isNomer) {
				show_maplayer
						.setImageResource(R.drawable.bl_map_icon_layer_weixin);
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				isNomer = false;
			} else {
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				show_maplayer.setImageResource(R.drawable.bl_map_icon_layer_moren);
				isNomer = true;
			}
			break;
		case R.id.cancel_offline:
			mRelativeLayout.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	private CalendarView calendar;
	private ImageButton calendarLeft;
	private TextView calendarCenter;
	private ImageButton calendarRight;
	private SimpleDateFormat format;
	private PopupWindow mPopupWindow;

	// 日历控件dialog
	private void showDateDialog(View v) {
		View view = View.inflate(this, R.layout.calendar_dialog, null);
		mPopupWindow = new PopupWindow(view, android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int popupWidth = view.getMeasuredWidth();
		int popupHeight = view.getMeasuredHeight();

		int[] location = new int[2];
		mPopupWindow.setFocusable(true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(this
				.getResources()));
		format = new SimpleDateFormat("yyyy-MM-dd");
		// 获取日历控件对象
		calendar = (CalendarView) view.findViewById(R.id.calendar);
		calendar.setSelectMore(false); // �??
		calendarLeft = (ImageButton) view.findViewById(R.id.calendarLeft);
		calendarCenter = (TextView) view.findViewById(R.id.calendarCenter);
		calendarRight = (ImageButton) view.findViewById(R.id.calendarRight);
		try {
			// 设置日历日期
			Date date = format.parse("2015-01-01");
			calendar.setCalendarData(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 获取日历中年??ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改??
		String[] ya = calendar.getYearAndmonth().split("-");
		calendarCenter.setText(ya[0] + getString(R.string.years) + ya[1] + getString(R.string.month));
		calendarLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calendarRight.setVisibility(View.VISIBLE);
				// 点击上一??同样返回年月
				String leftYearAndmonth = calendar.clickLeftMonth();
				String[] ya = leftYearAndmonth.split("-");
				calendarCenter.setText(ya[0] + getString(R.string.years) + ya[1] + getString(R.string.month));
			}
		});

		calendarRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int mot = new Date().getMonth() + 1;
				// 点击下一??
				String rightYearAndmonth = calendar.clickRightMonth();
				String[] ya = rightYearAndmonth.split("-");
				int moth = Integer.parseInt(ya[1]);
				if (mot == moth) {
					calendarRight.setVisibility(View.INVISIBLE);
				}
				calendarCenter.setText(ya[0] + getString(R.string.years) + ya[1] + getString(R.string.month));
			}
		});
		// 设置控件监听，可以监听到点击的每????（大家也可以在控件中根据????设定??
		calendar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void OnItemClick(Date selectedStartDate,
					Date selectedEndDate, Date downDate) {
				startDate = new Date(downDate.getYear(), downDate.getMonth(),
						downDate.getDate(), 0, 0, 0);
				endDate = new Date(downDate.getYear(), downDate.getMonth(),
						downDate.getDate(), 23, 59, 59);
				new TrackTask().execute();
				mPopupWindow.dismiss();
			}
		});
		v.getLocationOnScreen(location);
		mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
				(location[0] + v.getWidth() / 2) - popupWidth / 2, location[1]
						- popupHeight);

	}

	private Date startDate;// 历史轨迹????时间
	private Date endDate;// 历史轨迹结束时间

	private boolean trackRuning = true;// 确定线程
	private SharedPreferences sp;
	private Thread trackThread;// 轨迹回放的线??

	// 播放历史轨迹
	public void trackPlay(ArrayList<Location> result) {	
				trackThread = new Thread(mRunnable);
				startTask();		
				list = result;
				mBaiduMap.clear();
				trackThread.start();
				timer.cancel();	
				index = 0;
				lsitt = new ArrayList<LatLng>();
		}

   
    public void stopTask() {

    	flag = false;

    	}
    public void startTask() {

    	flag = true;

    	}
	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {	
				for (final Location s : list) {
					if(flag){
						Message msg = handler.obtainMessage();
						msg.obj = s;
						msg.what = 3;
						handler.sendMessage(msg);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
                                                return;
					}
					Log.i("main", trackThread.getName());
				}
			}
			}
	};

	// 获取历史轨迹数据
	private class TrackTask extends AsyncTask<Void, Void, ArrayList<Location>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected ArrayList<Location> doInBackground(Void... params) {
			try {
				return HttpHelperUtils.GetHistoryData(Acount.getCurrentDevice()
						.getId(), startDate, endDate);
			} catch (ConnectException e) {
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Location> result) {
			if (result != null) {
				if (result.size() > 0) {
					if(trackThread != null){
						stopTask();
						trackThread.interrupt();
						trackThread = null;
					}
					trackPlay(result);
				} else {
					Toast.makeText(getApplicationContext(), getString(R.string.no_location_message), 0)
							.show();
					stopTask();
				}
			}
			super.onPostExecute(result);
		}
	}

	// 进入消息界面
	public void EnterMsg(View v) {
		Intent intent = new Intent(this, MessageCenterActivity.class);
		startActivity(intent);
	}

	// 呼叫小宝
	public void CallBaby(View v) {
		if(listDevice.size() != 0){
			String phone = Acount.getCurrentDevice().getSimPhone();
			if(phone.isEmpty() || "".equals(phone) || "null".equals(phone)){
				Toast.makeText(HomeBaiduActivity.this, getString(R.string.equipment_no_phone), 0).show();
			}else{
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phone));
				startActivity(intent);
			}
		}else{
			dialog_util.showRecoverFactory();
		}		
	}
	// 切换宝贝
	public void change(View v) {
		mPopupWindowUtils.getPopupWindow(new CurrentDevice() {
			@Override
			public void onSetName(String name, String Battery) {
				tv_baby_name.setText(name);
			}
		}).showAsDropDown(v);
		listDevice = Acount.getDevices();
	}
	
	//微信对讲
	public void TrackBaby(View v){
		if(listDevice.size() > 0){
			Intent intent = new Intent(this,WeiXinActivity.class);
			startActivity(intent);
		}else{
			dialog_util.showRecoverFactory();
		}
	}

	//10秒延迟timer.
	private Timer delayTimer;
	
	// �??录音指令
	public void tape(View v) {
		switch (v.getId()) {
		case R.id.btn_danci_dinwei:
			if(aDevice != null){
				if(listDevice.size() > 0){
					DialogUtils.ShowWaitDialog(this);
					new ZzTask().execute();
					//timer计时10秒，10秒后执行
					if(delayTimer == null){
						delayTimer = new Timer();
						delayTimer.schedule(new DelayTimerTask(), 10000);
					}else{
						delayTimer.cancel();
						delayTimer = null;
						DialogUtils.dismissDialog();
						Toast.makeText(getApplicationContext(), "定位点击过快", Toast.LENGTH_SHORT).show();
					}
				}else{
					dialog_util.showRecoverFactory();
				}
			}
			break;		
		case R.id.btn_qq_luyin:
			if(listDevice.size() > 0){
				new TapeTask().execute();
			}else{
				dialog_util.showRecoverFactory();
			}
			
			break;			
		case R.id.btn_shebei_chazhao:
			if(listDevice.size() > 0){
				new FindDeviceTask().execute();
			}else{
				dialog_util.showRecoverFactory();
			}
			break;	
		case R.id.btn_phone_listen:
			if(listDevice.size() > 0){
				phone = Acount.getCurrentDevice().getDevDingtongConfig().getListenerPhone();
				dialog_phone = dialog_util.initPhoneDialogView(HomeBaiduActivity.this,phone, phoneListen);
				dialog_phone.show();
			}else{
				dialog_util.showRecoverFactory();
			}
			break;	
		default:
			break;
		}
	}
	
	//10秒延迟的执行体
	public class DelayTimerTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			handler.sendEmptyMessage(7);
		}
		
	}
	
	//监听远程守护号码的事件 
		OnClickListener phoneListen = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.phone_no:
					if(dialog_phone.isShowing()){
						dialog_phone.dismiss();
					}
					break;
				case R.id.phone_ok:
					if(dialog_phone.isShowing()){
						dialog_phone.dismiss();
					}
					clickPhoneOk(phone);
					break;

				default:
					break;
				}
			}
		};

	//监听号码点击是，ok
		private void clickPhoneOk(String phone_listen){
			if(TextUtils.isEmpty(phone_listen)){
				Intent intent = new Intent(this,PhoneCallActivity.class);
				startActivity(intent);
			}else{
				PhoneListenTask task = new PhoneListenTask(HomeBaiduActivity.this, phone_listen);
				task.execute();
			}
		}
	// 追踪设备
//	public void zzDevice(View v) {
//		new ZzTask().execute();
//	}

	// 单点地址查询
	private class ZzTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			try {
//				if(Utils.getType(Acount.getCurrentDevice().getTypeId())){
					return HttpHelperUtils.Dev_Dingtong_LocationQuery(Acount
							.getCurrentDevice().getId());
//				}else{
//					return HttpHelperUtils.DevHunterLocationQuery(Acount
//							.getCurrentDevice().getId());
//				}
				
			} catch (ConnectException e) {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result != null) {
				if (result == -1) {
//					Toast.makeText(getApplicationContext(), getString(R.string.equipment_is_not_exist), 0)
//							.show();
				} else if (result == 0) {
//					Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0).show();
				} else if (result == 1) {
//					Toast.makeText(getApplicationContext(), getString(R.string.send_true), 0).show();
				} else if (result == -10){
					quitToLogin();
				} else if (result == -11){
					equipmentOffLine();
				} 
				
			}
			super.onPostExecute(result);
		}
	}

	// �??录音指令
	private class TapeTask extends AsyncTask<Void, Void, Integer> {
		@Override
		protected Integer doInBackground(Void... params) {
			try {
				if(Acount.getCurrentDevice().getTypeId() == 1){
					return HttpHelperUtils.Dev_dingtong_Recording(Acount
							.getCurrentDevice().getId());
				}else{
					return HttpHelperUtils.DevHunterStartRecord(Acount
							.getCurrentDevice().getId());
				}
				
			} catch (ConnectException e) {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				switch (result) {
				case 0:
					Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0).show();
					break;
				case 1:
					Toast.makeText(getApplicationContext(), getString(R.string.send_true), 0).show();
					break;
				case -1:
					Toast.makeText(getApplicationContext(), R.string.equipment_is_not_exist, 0)
					.show();
					break;
				case -10:
					quitToLogin();
					break;
				case -11:
					equipmentOffLine();
					break;
				default:
					break;
				}
			}
		}

	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if(timer_task != null){
			timer_task.cancel();
			timer_task = null;
		}
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		// ????时销毁定??
		mMapView = null;
		if (trackThread != null && trackThread.isAlive()) {
			trackThread.stop();
		}
		if(timer_task != null){
			timer_task.cancel();
			timer_task =  null;
		}
		unregisterReceiver(mDeviceMissReceiver);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		try {
			if(timer != null){
				if(timer_task == null){
					timer_task = new TimerTk();
					timer.schedule(timer_task,  60 * 1000,60 * 1000);
				}else{
					timer.schedule(timer_task,  60 * 1000,60 * 1000);
				}
			}else{
				timer = new Timer();
				timer_task = new TimerTk();
				timer.schedule(timer_task,  60 * 1000,60 * 1000);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("TAG", "*******890"+e.toString());

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管??
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

		mMapView.onPause();
	}

	// 推出整个程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean home = sp.getBoolean("HOME", false);
		if (!home) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				if ((System.currentTimeMillis() - mExitTime) > 3000) {
					Toast.makeText(this, getString(R.string.click_again_exit_program), Toast.LENGTH_SHORT)
							.show();
					mExitTime = System.currentTimeMillis();
				} else {
					// FunctionListActivity.aActivity.finish();
					finish();
					if (trackThread != null && trackThread.isAlive()) {
						trackThread.stop();
					}
				}
				return true;
			}
		} else {
			overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
			if (trackThread != null && trackThread.isAlive()) {
				trackThread.stop();
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	public void actiongong(View view)
	{
		mMenu.toggle();
	}
	
	public void onClick_Event(View v){
		switch (v.getId()) {
		case R.id.btn_phone:      	// 亲情号码
			if(listDevice.size()!=0){
				Intent dearIntent = new Intent();
				dearIntent.setClass(this, RelationListActivity.class);
				if(Acount.getCurrentDevice().getTypeId() == 1){
					
					startActivity(dearIntent);
				}else{	
					
					startActivity(dearIntent);
				}
				
			}else{
				dialog_util.showRecoverFactory();
				break;
			}			
			break;
		case R.id.btn_dui:			//关注列表
			if(listDevice.size()!=0){
				Intent dearNumber = new Intent(this, AddAttationManActivity.class);
				startActivity(dearNumber);
			}else{
				dialog_util.showRecoverFactory();
				break;
			}	
			break;
		case R.id.btn_school:			//校园考勤
			if(listDevice.size()!=0){
				Intent school = new Intent(this, TestActivity.class);
				startActivity(school);
			}else{
				dialog_util.showRecoverFactory();
				break;
			}	
			break;
		case R.id.btn_wei:     		//电子围栏
			if(listDevice.size()!=0){
				Intent safeIntent = new Intent(this, SafeAreaActivity.class);
				startActivity(safeIntent);
			}else{
				dialog_util.showRecoverFactory();
				break;
			}		
			break;
		case R.id.btn_uni:			//指令下发
			if(listDevice.size()!=0){
				Intent instIntent = new Intent(this,
						InstructionIssuedActivity.class);
				startActivity(instIntent);
			}else{
				dialog_util.showRecoverFactory();
				break;
			}	
			break;
		case R.id.btn_alarm:		//脱落警报
			if(listDevice.size()!=0){
				Intent avoidDrop = new Intent(this, AvoidDropActivity.class);
				startActivity(avoidDrop);
			}else{
				dialog_util.showRecoverFactory();
				break;
			}	
			break;
		case R.id.btn_set:			//应用设置
			Intent appsetIntent = new Intent(this,
					ApplicationSettingActivity.class);
			startActivity(appsetIntent);
			break;
		case R.id.btn_our:          //关于我们
			Intent aboutIntent = new Intent(this, AboutUsActivity.class);
			startActivity(aboutIntent);
			break;
		default:
			break;
		}
	}
	
	private class FindDeviceTask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.Dev_dingtong_DevFind(Acount.getCurrentDevice().getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			switch (result) {
			case -1:
				Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0).show();
				break;
			case 0:
				Toast.makeText(getApplicationContext(), getString(R.string.send_false), 0).show();			
				break;
			case 1:
				Toast.makeText(getApplicationContext(), getString(R.string.send_true), 0).show();
				break;
			case -10:
				quitToLogin();
				break;
			case -11:
				equipmentOffLine();
				break;	
			default:
				break;
			}
		}	
	}
	private AlertDialog dialog_factory;
	private int deviceId;
	private void showRecoverFactory(){
		View view = View.inflate(this, R.layout.add_device_dialog, null);
		Button btn_recover = (Button)view.findViewById(R.id.add_device_ok);
		Button btn_fctory = (Button)view.findViewById(R.id.add_device_no);
		AlertDialog.Builder builder = new Builder(this);
		builder.setView(view);
		dialog_factory = builder.create();
		dialog_factory.show();
		btn_recover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent eqipment = new Intent(HomeBaiduActivity.this, AddEquipmentActivity.class);
				startActivity(eqipment);
				dialog_factory.dismiss();
			}
		});
		btn_fctory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_factory.dismiss();
			}
		});
	}
	
	private DeviceMissReceiver mDeviceMissReceiver;
	/*
	 * 广播注册
	 */
	public void RegistBroadcast(){
		mDeviceMissReceiver = new DeviceMissReceiver();
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		mIntentFilter.addAction(AppConfig.DEVICEMISS);
		mIntentFilter.addAction(AppConfig.DEVICEONLINE);
		mIntentFilter.addAction(AppConfig.DEVICEFIND);
		registerReceiver(mDeviceMissReceiver, mIntentFilter);
		
	}
	
	/*
	 * 广播接收
	 */
	private class DeviceMissReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(AppConfig.DEVICEMISS.equals(intent.getAction())){
				handler.sendEmptyMessage(4);
				
			}else if(AppConfig.DEVICEONLINE.equals(intent.getAction())){
				handler.sendEmptyMessage(5);
				
			}else if(AppConfig.DEVICEFIND.equals(intent.getAction())){
				if(getGiveLocationState()){
					//取消task，同时获取定位信息，判断时间是否一致，一致Toast设备获取定位失败。
					handler.sendEmptyMessage(6);
				}
			}
		}
	}

	
	//10秒之类收到定位广播
	private boolean mGiveLocationState = true;
	public boolean setGiveLocationState(){
		if(mGiveLocationState){
			this.mGiveLocationState = false;
		}else{
			this.mGiveLocationState = true;
		}
		
		return mGiveLocationState;
	}
	
	
	public boolean getGiveLocationState(){
		
		return mGiveLocationState;
		
	}
	
	
	/*
	 * 无论设备是否查找成功，都需定位一次
	 */
	public void agineLocation(){
		if(trackThread != null){
			if(trackThread.isAlive()){
				stopTask();
				trackThread.interrupt();
				list.clear();
				mBaiduMap.clear();
			}
			list.clear();
		}
		list.clear();
		mBaiduMap.clear();
		initViews();
		getloct(GETLOCATIONTYPE);
	}
}
