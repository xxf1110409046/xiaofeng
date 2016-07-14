package com.brilyong.technology.httputils;

public class AppConfig {
	public static final String URL = "http://gps.szwearable.com/";// 112.74.95.236    192.168.0.17 gps.szwearable.com/ 
	public static final String APPKEY = "4AA5238C-C02B-46BD-9737-8285F27B9995";
	public static final String GETEPEDOMETERDATA = "/GetStepDataByDeviceId";//计步器任务下发获取数据
	public static final String GETDINTONGALARM = "/Dev_dingtong_SendAlarm";// 提交闹钟
	public static final String GETALARM = "/GetDevStuAlarmEntityByDeviceId";// 获取闹钟
	public static final String FORGETPASSWORD = "/GetUserForgotPassword";
	public static final String BLUETOOTH = "/DevHunterBluetoothSetting";//设置蓝牙开关
	public static final String UPDATEBABY = "/UpdateDeviceDetailInfo";//更新宝贝详情
	public static final String DEVSENDPED = "/Dev_dingtong_SendPedometer";//计步器任务下发
	public static final String DEVBLUETOOTH = "/Dev_dingtong_BluetoothSetting";//设置蓝牙开关2
	public static final String GETDEMUTETIME = "/GetMuteTimeByDeviceId";// 获取静音时段的内容
	public static final String DEVFIND = "/Dev_dingtong_DevFind";//查找设备
	public static final String DEVTEXT = "/Dev_dingtong_send_text";//语音播报
	public static final String GETFALL = "/GetXDingtongFallStat";//获取脱落警报状态
	public static final String SETFALL = "/UpdateXDingtongFallStat";//保存脱落警报状态
	public static final String SAVETOKEN = "/SaveToken";//保存登录状态
	public static final String FACTORY = "/DevHunterFactory";//恢复出厂设置
	public static final String DEVFACTORY = "/Dev_dingtong_Factory";//恢复出厂设置2
	public static final String SILENTTIME = "/DevHunterSilentTime";//静音时段
	public static final String DEVSILENTTIME = "/Dev_dingtong_SchoolTime";//静音时段
	public static final String DELETEDEVICE = "/DeleteDeviceAttention";//删除设备关注者
	public static final String LISTENSETTING = "/DevHunterListenSetting";//设置监听号码
	public static final String GETDEVICEBAINFO = "/GetDeviceBaInfo";// 宝贝详情
	public static final String GETFIR = "/GetRFIDRecord";// 考勤数据
	
	public static final String DEVGETFIR = "/GetAndroidUserMessageList";// 消息中心
	public static final String GETANDROIDVOICEMESSAGELIST = "/GetAndroidVoiceMessageList";// 获取录音消息
	public static final String LISTENDEV = "/Dev_dingtong_SetListen";//设置监听号码2
	public static final String CLEARDEVICE = "/ClearDevHunterPhoneNumners";
	public static final String LOGIN = "/Login";// 登录
	public static final String GETDEVICEBYID = "/GetDeviceById";// 获取设备信息
	public static final String GETCHECKCODE = "/GetUserRegCheckCode";// 获取验证码
	public static final String USERREG = "/UserReg";// 用户注册
	public static final String GETHISTORYDATA = "/GetHistoryData";// 获取历史的轨迹数据
	public static final String FINDPASSWORD = "/FindPassword";// 忘记密码
	public static final String ALLDEVICE = "/GetDeviceListByLoginName";// 获取用户下所有设备
	public static final String BINDDEVICE = "/BindDevice";// 绑定设备
	public static final String AGREEATTATION = "/AgreeDeviceAttention1";// 用户关注
	public static final String AGREEATTION = "/AgreeAttion";//主动申请关注
	public static final String FINISHBINDDEVICE = "/FinishBindDevice";// 绑定设备
	public static final String FIRWALLSETTING = "/DevHunterFirwallSetting";// 来电防火墙

	public static final String FIRWALDEV = "/Dev_dingtong_FirewallSetting";// 来电防火墙type-2
	public static final String PEDOMETERSETTING = "/DevHunterPedometerSetting";// 开启、关闭、清零计步器
	public static final String DEVSENDPEDOMETER = "/Dev_dingtong_PometerSetting";// 开启、关闭、清零计步器2
	public static final String TIMEREST = "/DevHunterTimeRest";// 定时休眠
	public static final String DEVTIMEREST = "/Dev_dingtong_dormancy";// 定时休眠2
	
	public static final String WORLMODE = "/Dev_dingtong_DevWorkMode";// 工作模式设置
	public static final String DEVWORLMODE = "/Dev_dingtong_WorkModeSetting";// 工作模式设置2
	
	public static final String PHONENUMBERS = "/DevHunterPhoneNumbersSetting8";// 设置亲情号码
	public static final String PHONEDEV520 = "/Dev_dongtong_SetPhoneNumbers";// 设置亲情号码type为2
	public static final String ADDGEOFENCE = "/AddGeofence";// 添加电子围栏
	public static final String GETGEOFENCE = "/GetGeofenceByDeviceId";// 查询电子围栏
	public static final String INVITEATTENTION = "/InviteAttention";// 邀请关注
	public static final String DELETEGEOFENCEBYID = "/DeleteGeofenceById";// 删除电子围栏
	public static final String UNBINDDEVICE = "/UnBindDevice";// 解除设备绑定或者删除关注者
	public static final String GETDEVICEATTENTION = "/GetDeviceAttention";// 解除设备绑定或者删除关注者
	public static final String FEERDBACK = "/Feedback";// 意见反馈
	public static final String CHECKUPDATE = "/CheckUpdate";// 意见反馈
	public static final String DEVSETTING = "/dev_activity_push_setting";// 设置运动提醒
	public static final String DEVSESTATICTTING = "/dev_static_push_setting";// 禁止运动提醒
	public static final String STARTRECORD = "/DevHunterStartRecord";// 录音设置
	public static final String STARTRECORDEV = "/Dev_dingtong_Recording";// 录音设置
	
	public static final String LOCATIONQUERY = "/DevHunterLocationQuery";// 单点地址查询
	public static final String LOCATIONDEV = "/Dev_dingtong_LocationQuery";// 单点地址查询2
	public static final String FILE_NOTIFICATION = "file_notification"; //发送广播
	public static final String MESSAGENOTIFICATION = "message_notification"; //发送Type为17的防脱落广播
	public static final String INTERVAL = "/Dev_dingtong_SettingInterval";//设置SETFIRD
	public static final String DEVICEMISS = "/devicemiss";//设备离线在线广播action
	public static final String DEVICEONLINE = "/deviceonline";//设备上线在线广播action
	public static final String DEVICEFIND = "/devicefind";//定位查找是否成功action
	public static final String FAMILY = "/GetXdtFamilyNumbers";//获取情亲列表
	public static final String FAMLIYNUMBER = "/XdtSettingFamilyNumber";//设置亲情号码
}
