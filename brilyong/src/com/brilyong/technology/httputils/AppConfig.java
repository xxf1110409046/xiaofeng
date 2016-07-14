package com.brilyong.technology.httputils;

public class AppConfig {
	public static final String URL = "http://gps.szwearable.com/";// 112.74.95.236    192.168.0.17 gps.szwearable.com/ 
	public static final String APPKEY = "4AA5238C-C02B-46BD-9737-8285F27B9995";
	public static final String GETEPEDOMETERDATA = "/GetStepDataByDeviceId";//�Ʋ��������·���ȡ����
	public static final String GETDINTONGALARM = "/Dev_dingtong_SendAlarm";// �ύ����
	public static final String GETALARM = "/GetDevStuAlarmEntityByDeviceId";// ��ȡ����
	public static final String FORGETPASSWORD = "/GetUserForgotPassword";
	public static final String BLUETOOTH = "/DevHunterBluetoothSetting";//������������
	public static final String UPDATEBABY = "/UpdateDeviceDetailInfo";//���±�������
	public static final String DEVSENDPED = "/Dev_dingtong_SendPedometer";//�Ʋ��������·�
	public static final String DEVBLUETOOTH = "/Dev_dingtong_BluetoothSetting";//������������2
	public static final String GETDEMUTETIME = "/GetMuteTimeByDeviceId";// ��ȡ����ʱ�ε�����
	public static final String DEVFIND = "/Dev_dingtong_DevFind";//�����豸
	public static final String DEVTEXT = "/Dev_dingtong_send_text";//��������
	public static final String GETFALL = "/GetXDingtongFallStat";//��ȡ���侯��״̬
	public static final String SETFALL = "/UpdateXDingtongFallStat";//�������侯��״̬
	public static final String SAVETOKEN = "/SaveToken";//�����¼״̬
	public static final String FACTORY = "/DevHunterFactory";//�ָ���������
	public static final String DEVFACTORY = "/Dev_dingtong_Factory";//�ָ���������2
	public static final String SILENTTIME = "/DevHunterSilentTime";//����ʱ��
	public static final String DEVSILENTTIME = "/Dev_dingtong_SchoolTime";//����ʱ��
	public static final String DELETEDEVICE = "/DeleteDeviceAttention";//ɾ���豸��ע��
	public static final String LISTENSETTING = "/DevHunterListenSetting";//���ü�������
	public static final String GETDEVICEBAINFO = "/GetDeviceBaInfo";// ��������
	public static final String GETFIR = "/GetRFIDRecord";// ��������
	
	public static final String DEVGETFIR = "/GetAndroidUserMessageList";// ��Ϣ����
	public static final String GETANDROIDVOICEMESSAGELIST = "/GetAndroidVoiceMessageList";// ��ȡ¼����Ϣ
	public static final String LISTENDEV = "/Dev_dingtong_SetListen";//���ü�������2
	public static final String CLEARDEVICE = "/ClearDevHunterPhoneNumners";
	public static final String LOGIN = "/Login";// ��¼
	public static final String GETDEVICEBYID = "/GetDeviceById";// ��ȡ�豸��Ϣ
	public static final String GETCHECKCODE = "/GetUserRegCheckCode";// ��ȡ��֤��
	public static final String USERREG = "/UserReg";// �û�ע��
	public static final String GETHISTORYDATA = "/GetHistoryData";// ��ȡ��ʷ�Ĺ켣����
	public static final String FINDPASSWORD = "/FindPassword";// ��������
	public static final String ALLDEVICE = "/GetDeviceListByLoginName";// ��ȡ�û��������豸
	public static final String BINDDEVICE = "/BindDevice";// ���豸
	public static final String AGREEATTATION = "/AgreeDeviceAttention1";// �û���ע
	public static final String AGREEATTION = "/AgreeAttion";//���������ע
	public static final String FINISHBINDDEVICE = "/FinishBindDevice";// ���豸
	public static final String FIRWALLSETTING = "/DevHunterFirwallSetting";// �������ǽ

	public static final String FIRWALDEV = "/Dev_dingtong_FirewallSetting";// �������ǽtype-2
	public static final String PEDOMETERSETTING = "/DevHunterPedometerSetting";// �������رա�����Ʋ���
	public static final String DEVSENDPEDOMETER = "/Dev_dingtong_PometerSetting";// �������رա�����Ʋ���2
	public static final String TIMEREST = "/DevHunterTimeRest";// ��ʱ����
	public static final String DEVTIMEREST = "/Dev_dingtong_dormancy";// ��ʱ����2
	
	public static final String WORLMODE = "/Dev_dingtong_DevWorkMode";// ����ģʽ����
	public static final String DEVWORLMODE = "/Dev_dingtong_WorkModeSetting";// ����ģʽ����2
	
	public static final String PHONENUMBERS = "/DevHunterPhoneNumbersSetting8";// �����������
	public static final String PHONEDEV520 = "/Dev_dongtong_SetPhoneNumbers";// �����������typeΪ2
	public static final String ADDGEOFENCE = "/AddGeofence";// ��ӵ���Χ��
	public static final String GETGEOFENCE = "/GetGeofenceByDeviceId";// ��ѯ����Χ��
	public static final String INVITEATTENTION = "/InviteAttention";// �����ע
	public static final String DELETEGEOFENCEBYID = "/DeleteGeofenceById";// ɾ������Χ��
	public static final String UNBINDDEVICE = "/UnBindDevice";// ����豸�󶨻���ɾ����ע��
	public static final String GETDEVICEATTENTION = "/GetDeviceAttention";// ����豸�󶨻���ɾ����ע��
	public static final String FEERDBACK = "/Feedback";// �������
	public static final String CHECKUPDATE = "/CheckUpdate";// �������
	public static final String DEVSETTING = "/dev_activity_push_setting";// �����˶�����
	public static final String DEVSESTATICTTING = "/dev_static_push_setting";// ��ֹ�˶�����
	public static final String STARTRECORD = "/DevHunterStartRecord";// ¼������
	public static final String STARTRECORDEV = "/Dev_dingtong_Recording";// ¼������
	
	public static final String LOCATIONQUERY = "/DevHunterLocationQuery";// �����ַ��ѯ
	public static final String LOCATIONDEV = "/Dev_dingtong_LocationQuery";// �����ַ��ѯ2
	public static final String FILE_NOTIFICATION = "file_notification"; //���͹㲥
	public static final String MESSAGENOTIFICATION = "message_notification"; //����TypeΪ17�ķ�����㲥
	public static final String INTERVAL = "/Dev_dingtong_SettingInterval";//����SETFIRD
	public static final String DEVICEMISS = "/devicemiss";//�豸�������߹㲥action
	public static final String DEVICEONLINE = "/deviceonline";//�豸�������߹㲥action
	public static final String DEVICEFIND = "/devicefind";//��λ�����Ƿ�ɹ�action
	public static final String FAMILY = "/GetXdtFamilyNumbers";//��ȡ�����б�
	public static final String FAMLIYNUMBER = "/XdtSettingFamilyNumber";//�����������
}
