package com.brilyong.technology.httputils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.BabyMessage;
import com.brilyong.technology.entity.BindDeviceResult;
import com.brilyong.technology.entity.BodyData;
import com.brilyong.technology.entity.CheckUpdateResult;
import com.brilyong.technology.entity.DataWa;
import com.brilyong.technology.entity.DevStuAlarmConfig;
import com.brilyong.technology.entity.Device;
import com.brilyong.technology.entity.DeviceAttention;
import com.brilyong.technology.entity.Dormancy;
import com.brilyong.technology.entity.Feedback;
import com.brilyong.technology.entity.GeofenceWrap;
import com.brilyong.technology.entity.GetDevStuAlarmEntityByDeviceIdResult;
import com.brilyong.technology.entity.Location;
import com.brilyong.technology.entity.Message;
import com.brilyong.technology.entity.Option;
import com.brilyong.technology.entity.Options;
import com.brilyong.technology.entity.SchoolData;
import com.brilyong.technology.entity.SearchOption;
import com.brilyong.technology.entity.TimeSpan;
import com.brilyong.technology.entity.UserMessageTable;
import com.brilyong.technology.entity.entity;
import com.brilyong.technology.httputils.AppConfig;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.httputils.JSONHelper;
import com.google.gson.Gson;

public class HttpHelperUtils {
	public static CookieStore cook;

	// ��¼ϵͳ
	public static boolean Login(String LoginName, String PassWord)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.LOGIN);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("LoginName", LoginName);
		obj.put("Password", PassWord);
		obj.put("AppKey", AppConfig.APPKEY);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		// ����ʱ
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		// ��ȡ��ʱ
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		cook = client.getCookieStore();
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getBoolean("d");
		}
		return false;
	}

	// �������ע
	public static int AgreeDeviceAttention1(String AttentionId,
			boolean IsAgree, String u_msgId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.AGREEATTATION);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("AttentionId", AttentionId);
		obj.put("IsAgree", IsAgree);
		obj.put("u_msgId", u_msgId);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ���������ע
	public static int AgreeAttion(String LoginName, String DeviceId,
			boolean IsAgree) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.AGREEATTION);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("LoginName", LoginName);
		obj.put("DeviceId", DeviceId);
		obj.put("IsAgree", IsAgree);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ���豸
	public static BindDeviceResult BindDevice(String PhoneNumber,
			String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.BINDDEVICE);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("PhoneNumber", PhoneNumber);
		obj.put("DeviceId", DeviceId);
		obj.put("AppKey", AppConfig.APPKEY);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			// result = paseString(result);
			JSONObject json = new JSONObject(result);
			String aa = json.getString("d");
			BindDeviceResult results = JSONHelper.parseObject(aa,
					BindDeviceResult.class);
			return results;
		}
		return null;
	}

	// ����豸�󶨻���ɾ����ע��
	public static int UnBindDevice(String LoginName, String DeviceId)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.UNBINDDEVICE);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("LoginName", LoginName);
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// APP������
	public static CheckUpdateResult CheckUpdate(String packeName, int Vcode)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.CHECKUPDATE);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("packeName", packeName);
		obj.put("Vcode", Vcode);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			JSONObject json = new JSONObject(result);
			String aa = json.getString("d");
			CheckUpdateResult aCheckUpdateResult = JSONHelper.parseObject(aa,
					CheckUpdateResult.class);
			return aCheckUpdateResult;
		}
		return null;
	}

	// �������رա�����Ʋ���
	public static int DevHunterPedometerSetting(int type, String DeviceId)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.PEDOMETERSETTING);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("type", type);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �����Ʋ�������2
	public static int Dev_dingtong_SendPedometer(int type, String DeviceId)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.DEVSENDPEDOMETER);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("type", type);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �����������
	public static int DevHunterPhoneNumbersSetting8(String DeviceId,
			String sos1, String sos2, String sos3, String sos4, String sos5,
			String sos6, String sos7, String sos8) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.PHONENUMBERS);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("sos1", sos1);
		obj.put("sos2", sos2);
		obj.put("sos3", sos3);
		obj.put("sos4", sos4);
		obj.put("sos5", sos5);
		obj.put("sos6", sos6);
		obj.put("sos7", sos7);
		obj.put("sos8", sos8);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �������׺��룬typeΪ1
	public static int Dev_Dingtong_SetPhoneNumbers(String DeviceId, String sos,
			String num1, String num2, String num3, String listen_num)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.PHONEDEV520);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("sos", sos);
		obj.put("num1", num1);
		obj.put("num2", num2);
		obj.put("num3", num3);
		obj.put("listen_num", listen_num);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �豸�˶����ѿ���
	public static int DeviceSeting(String DeviceId, String LoginName,
			boolean isOpen) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.DEVSETTING);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("LoginName", LoginName);
		obj.put("isOpen", isOpen);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ��ֹ���ѿ���
	public static int DeviceStaticSeting(String DeviceId, String LoginName,
			boolean isOpen) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.DEVSESTATICTTING);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("LoginName", LoginName);
		obj.put("isOpen", isOpen);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �������
	public static int Feedback(String LoginName, String Phone, String Email,
			String Conent) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.FEERDBACK);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		Feedback fee = new Feedback();
		fee.setAppName("com.blyang.technology");
		fee.setConent(Conent);
		fee.setEmail(Email);
		fee.setLoginName(LoginName);
		fee.setPhone(Phone);
		String Str = "{Feedback:" + fee + "}";
		HttpEntity bodyEntity = new StringEntity(Str, "utf-8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ����ģʽ����
	public static int Dev_dingtong_DevWorkMode( String DeviceId,int work)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.WORLMODE);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("work", work);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ����ģʽ����2
	public static int Dev_dingtong_WorkModeSetting(int type, String DeviceId)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.DEVWORLMODE);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("type", type);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �����ע
	public static int InviteAttention(String LoginName, String DeviceId)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.INVITEATTENTION);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("LoginName", LoginName);
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �������ǽ
	public static int DevHunterFirwallSetting(boolean IsOpen, String DeviceId)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.FIRWALLSETTING);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("IsOpen", IsOpen);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �������ǽ2
	public static int Dev_Dingtong_FirewallSetting(String DeviceId, boolean IsOpen)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.FIRWALDEV);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("IsOpen", IsOpen);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ��ɰ��豸
	public static int FinishBindDevice(String CheckCode, String PhoneNumber,
			String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.FINISHBINDDEVICE);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("CheckCode", CheckCode);
		obj.put("PhoneNumber", PhoneNumber);
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ��ȡ�豸��Ϣ
	public static Device GetDeviceById(String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.GETDEVICEBYID);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			// result = paseString(result);
			JSONObject json = new JSONObject(result);
			String aa = json.getString("d");
			Device aDevice = JSONHelper.parseObject(aa, Device.class);
			return aDevice;
		}
		return null;
	}

	// ����Χ����ѯ
	public static GeofenceWrap GetGeofenceByDeviceId(String DeviceId,
			int PageSize, int PageIndex) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.GETGEOFENCE);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		Option opt = new Option();
		opt.setDeviceId(DeviceId);
		opt.setPageIndex(PageIndex);
		opt.setPageSize(PageSize);
		String Str = "{Option:" + opt + "}";
		HttpEntity bodyEntity = new StringEntity(Str, "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);

		String result = EntityUtils.toString(httpResponse.getEntity());
		// result = paseString(result);
		JSONObject json = new JSONObject(result);
		String aa = json.getString("d");
		return JSONHelper.parseObject(aa, GeofenceWrap.class);
	}

	// �û�ע��
	public static int UserReg(String LoginName, String CheckCode,
			String Password) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.USERREG);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("LoginName", LoginName);
		obj.put("CheckCode", CheckCode);
		obj.put("Password", Password);
		obj.put("AppKey", AppConfig.APPKEY);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ¼������
	public static int DevHunterStartRecord(String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.STARTRECORD);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ¼������2
	public static int Dev_dingtong_Recording(String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.STARTRECORDEV);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �����ַ��ѯ
	public static int DevHunterLocationQuery(String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.LOCATIONQUERY);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �����ַ��ѯtype����2
	public static int Dev_Dingtong_LocationQuery(String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.LOCATIONDEV);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ��ȡ��ǰ�û������豸��Ϣ
	public static ArrayList<Device> GetDeviceListByLoginName(String LoginName)
			throws Exception {
		// HttpPost request = new
		// HttpPost("http://61.191.50.35:1234/AppData"+"/GetDeviceListByLoginName");
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.ALLDEVICE);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("LoginName", LoginName);
		HttpEntity bodyEntity;

		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			// result = paseString(result);
			JSONObject json = new JSONObject(result);
			String js = json.getString("d");
			ArrayList<Device> list = (ArrayList<Device>) JSONHelper
					.parseCollection(js, ArrayList.class, Device.class);
			if (list != null) {
				return list;
			}
		}
		return null;
	}

	// ��ȡ�豸��ע��
	public static ArrayList<DeviceAttention> GetDeviceAttention(String DeviceId)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.GETDEVICEATTENTION);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			// result = paseString(result);
			JSONObject json = new JSONObject(result);
			String js = json.getString("d");
			ArrayList<DeviceAttention> list = (ArrayList<DeviceAttention>) JSONHelper
					.parseCollection(js, ArrayList.class, DeviceAttention.class);
			if (list != null) {
				DeviceAttention a = list.get(0);
				String aa = a.toString();
				return list;
			}
		}
		return null;
	}

	// ��ȡ��ʷ�켣����
	public static ArrayList<Location> GetHistoryData(String DeviceId)
			throws Exception {
		HttpPost httpPost = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.GETHISTORYDATA);
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");

		SearchOption opt = new SearchOption();
		opt.setDeviceId(DeviceId);
		opt.setStartDate(new Date(2015 - 1900, 6 - 1, 6, 0, 0));
		opt.setEndDate(new Date(2015 - 1900, 6 - 1, 7, 0, 0));
		opt.setLoadLBS(true);
		String Str = "{SearchOption:" + opt + "}";
		HttpEntity bodyEntity = new StringEntity(Str, "utf8");
		httpPost.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			// result = paseString(result);
			JSONObject json = new JSONObject(result);
			String js = json.getString("d");
			ArrayList<Location> list = (ArrayList<Location>) JSONHelper
					.parseCollection(js, ArrayList.class, Location.class);
			if (list != null) {
				return list;
			}
		}
		return null;
	}

	// ��ӵ���Χ��
	public static int AddGeofence(String DeviceId, String name, int radius,
			String date, int Type) throws Exception {
		HttpPost httpPost = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.ADDGEOFENCE);
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
		entity ent = new entity();
		ent.setDeviceId(DeviceId);
		ent.setName(name);
		ent.setRadius(radius);
		ent.setData(date);
		ent.setType(Type);
		String Str = "{entity:" + ent + "}";
		HttpEntity bodyEntity = new StringEntity(Str, "utf8");
		httpPost.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ɾ������Χ��
	public static int DeleteGeofenceById(String Id) throws Exception {
		HttpPost httpPost = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.DELETEGEOFENCEBYID);
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject js = new JSONObject();
		js.put("Id", Id);
		HttpEntity bodyEntity = new StringEntity(js.toString(), "utf8");
		httpPost.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ��ʱ��������
	public static int DevHunterTimeRest(String DeviceId, Date Start, Date End)
			throws Exception {
		HttpPost httpPost = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.TIMEREST);
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
		SimpleDateFormat dateformat1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startime = dateformat1.format(Start);
		String endtime = dateformat1.format(End);
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("Start", startime);
		obj.put("End", endtime);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf-8");
		httpPost.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ��ʱ��������2
	public static int Dev_dingtong_dormancy(Dormancy time) throws Exception {
		HttpPost httpPost = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.DEVTIMEREST);
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		String Str = "{time:" + time + "}";
		HttpEntity bodyEntity = new StringEntity(Str, "utf-8");
		httpPost.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �����û���ע

	// ��ȡ��֤��
	// public static int GetUserRegCheckCode(String LoginName) throws Exception
	// {
	// HttpPost request = new HttpPost(AppConfig.URL
	// + "OpenAPI/MobileService.asmx" + AppConfig.GETCHECKCODE);
	// request.addHeader("Content-Type", "application/json; charset=utf-8");
	// JSONObject obj = new JSONObject();
	// obj.put("LoginName", LoginName);
	// obj.put("AppKey", AppConfig.APPKEY);
	// HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf-8");
	// request.setEntity(bodyEntity);
	// DefaultHttpClient client = new DefaultHttpClient();
	// client.setCookieStore(HttpHelperUtils.cook);
	// HttpResponse httpResponse = client.execute(request);
	// if (httpResponse.getStatusLine().getStatusCode() != 404) {
	// String result;
	// result = EntityUtils.toString(httpResponse.getEntity());
	// return new JSONObject(result).getInt("d");
	// }
	// return 0;
	// }
	public static int GetUserRegCheckCode(String LoginName) throws Exception {
		HttpPost httpPost = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.GETCHECKCODE);
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject js = new JSONObject();
		js.put("LoginName", LoginName);
		js.put("AppKey", AppConfig.APPKEY);
		HttpEntity bodyEntity = new StringEntity(js.toString(), "utf8");
		httpPost.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ɾ�������������
	public static int ClearDevHunterPhoneNumners(String DeviceId)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.CLEARDEVICE);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ��ȡ��ʷ�켣����
	public static ArrayList<Location> GetHistoryData(String DeviceId,
			Date StartDate, Date EndDate) throws Exception {
		HttpPost httpPost = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.GETHISTORYDATA);
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");

		SearchOption opt = new SearchOption();
		opt.setDeviceId(DeviceId);
		opt.setStartDate(StartDate);
		opt.setEndDate(EndDate);
		opt.setLoadLBS(true);
		String Str = "{SearchOption:" + opt + "}";
		HttpEntity bodyEntity = new StringEntity(Str, "utf8");
		httpPost.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			// result = paseString(result);
			JSONObject json = new JSONObject(result);
			String js = json.getString("d");
			ArrayList<Location> list = (ArrayList<Location>) JSONHelper
					.parseCollection(js, ArrayList.class, Location.class);
			if (list != null) {
				return list;
			}
		}
		return null;
	}

	// ���ü�������
	public static int DevHunterListenSetting(String DeviceId, String ListenNum)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.LISTENSETTING);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("ListenNum", ListenNum);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ���ü�������type2
	public static int Dev_Dingtong_SetListen(String DeviceId, String Phone)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.LISTENDEV);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("Phone", Phone);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �ָ���������
	public static int DevHunterFactory(String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.FACTORY);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// �ָ���������2
	public static int Dev_dingtong_Factory(String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.DEVFACTORY);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ����ʱ���
	public static int DevHunterSilentTime(String DeviceId, TimeSpan T1,
			TimeSpan T2, int Repeat) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.SILENTTIME);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		// JSONObject obj = new JSONObject();
		// obj.put("DeviceId", DeviceId);
		// obj.put("T1", T1);
		// obj.put("T2", T2);
		// obj.put("Repeat", Repeat);
		DataWa data = new DataWa();
		data.setDeviceId(DeviceId);
		data.setRepeat(Repeat);
		data.setEndHour1(T1.getEndHour());
		data.setEndMinute1(T1.getEndMinute());
		data.setStartHour1(T1.getStartHour());
		data.setEndMinute1(T1.getStartMinute());
		data.setEndHour2(T2.getEndHour());
		data.setEndMinute2(T2.getEndMinute());
		data.setStartHour2(T2.getStartHour());
		data.setEndMinute2(T2.getStartMinute());
		data.setIsOpen1(T1.isIsOpen1());
		data.setIsOpen2(T2.isIsOpen2());
		String Str = "{data:" + data + "}";
		HttpEntity bodyEntity = new StringEntity(Str, "utf-8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ����ʱ���
	public static int Dev_dingtong_SchoolTime(DataWa data) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.DEVSILENTTIME);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		String Str = "{data:" + data + "}";
		HttpEntity bodyEntity = new StringEntity(Str, "utf-8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ��ȡ��������code����֤��
	public static int GetUserForgotPassword(String LoginName) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.FORGETPASSWORD);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("LoginName", LoginName);
		obj.put("AppKey", AppConfig.APPKEY);
		HttpEntity bodyEntity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ����������
	public static int DevHunterBluetoothSetting(String DeviceId, boolean IsOpen)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "/OpenAPI/MobileService.asmx" + AppConfig.BLUETOOTH);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("IsOpen", IsOpen);
		// ������ת��ΪUTF-8
		HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(entity);
		// ��ȡClient���󣬻�ȡ������Ӧ
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;

	}

	// ����������2
	public static int Dev_dingtong_BluetoothSetting(String DeviceId,
			boolean IsOpen) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "/OpenAPI/MobileService.asmx" + AppConfig.DEVBLUETOOTH);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("IsOpen", IsOpen);
		// ������ת��ΪUTF-8
		HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(entity);
		// ��ȡClient���󣬻�ȡ������Ӧ
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;

	}

	// ��ȡ��������
	public static BabyMessage GetDeviceBaInfo(String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.GETDEVICEBAINFO);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			// result = paseString(result);
			JSONObject json = new JSONObject(result);
			String aa = json.getString("d");
			BabyMessage results = JSONHelper.parseObject(aa, BabyMessage.class);
			return results;
		}
		return null;
	}

	// �����豸
	public static int Dev_dingtong_DevFind(String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "/OpenAPI/MobileService.asmx" + AppConfig.DEVFIND);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		// ������ת��ΪUTF-8
		HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(entity);
		// ��ȡClient���󣬻�ȡ������Ӧ
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;

	}

	// ��������
	public static int Dev_dingtong_send_text(String DeviceId, String text)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "/OpenAPI/MobileService.asmx" + AppConfig.DEVTEXT);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("text", text);
		// ������ת��ΪUTF-8
		HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(entity);
		// ��ȡClient���󣬻�ȡ������Ӧ
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;

	}

	// �����¼״̬
	public static int SaveToken(String LoginName) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "/OpenAPI/MobileService.asmx" + AppConfig.SAVETOKEN);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("LoginName", LoginName);
		obj.put("Lang", MyApplication.getInstance().getLanguage());
		obj.put("AppKey", AppConfig.APPKEY);
		obj.put("IMEI", MyApplication.getInstance().getImei());
		obj.put("CType", 1);
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(obj.toString(), "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		// ����ʱ
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		// ��ȡ��ʱ
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ��ȡ��������
	public static ArrayList<SchoolData> GetRFIDRecord(BodyData body)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.GETFIR);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		String Str = "{Option:" + body + "}";
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(Str, "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			// result = paseString(result);
			JSONObject json = new JSONObject(result);
			String aa = json.getString("d");
			ArrayList<SchoolData> list = (ArrayList<SchoolData>) JSONHelper
					.parseCollection(aa, ArrayList.class, SchoolData.class);
			if (list != null) {
				return list;
			}
		}
		return null;
	}

	// ��ȡ��Ϣ�����б�
	public static UserMessageTable GetAndroidUserMessageList(Options bodys)
			throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx" + AppConfig.DEVGETFIR);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		String Str = "{Option:" + bodys + "}";
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(Str, "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			// result = paseString(result);
			JSONObject json = new JSONObject(result);
			String aa = json.getString("d");
			UserMessageTable table = JSONHelper.parseObject(aa,
					UserMessageTable.class);
			if (table != null) {
				return table;
			}
		}
		return null;
	}

	// ��ȡ¼����¼
	public static UserMessageTable GetAndroidVoiceMessageList(String loginName,
			int msgIndex) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "OpenAPI/MobileService.asmx"
				+ AppConfig.GETANDROIDVOICEMESSAGELIST);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		Options bodys = new Options();
		bodys.setLoginName(loginName);
		bodys.setMsgIndex(msgIndex);
		bodys.setPageSize(0);
		String Str = "{Option:" + bodys + "}";
		HttpEntity bodyEntity;
		bodyEntity = new StringEntity(Str, "utf8");
		request.setEntity(bodyEntity);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			// result = paseString(result);
			JSONObject json = new JSONObject(result);
			String aa = json.getString("d");
			UserMessageTable table = JSONHelper.parseObject(aa,
					UserMessageTable.class);
			if (table != null) {
				return table;
			}
		}
		return null;
	}

	// ɾ����Ϣ���ĵ�ĳ����¼
	public static int DeleteUserMessageById(String Id) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "/OpenAPI/MobileService.asmx" + AppConfig.DEVTEXT);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("Id", Id);
		// ������ת��ΪUTF-8
		HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(entity);
		// ��ȡClient���󣬻�ȡ������Ӧ
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}

	// ���±�������
	public static int UpdateDeviceDetailInfo(String DeviceId, String Name,
			int relationship, double weight, double Height, Date Birthday,
			int sex, String DevicePhone) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "/OpenAPI/MobileService.asmx" + AppConfig.UPDATEBABY);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		SimpleDateFormat dateformat1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startime = dateformat1.format(Birthday);
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("Name", Name);
		obj.put("relationship", relationship);
		obj.put("weight", weight);
		obj.put("Height", Height);
		obj.put("Birthday", startime);
		obj.put("sex", sex);
		obj.put("DevicePhone", DevicePhone);
		// ������ת��ΪUTF-8
		HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(entity);
		// ��ȡClient���󣬻�ȡ������Ӧ
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}
	
	// ��ȡ���侯��״̬
	public static boolean GetXDingtongFallStat(String DeviceId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "/OpenAPI/MobileService.asmx" + AppConfig.GETFALL);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		// ������ת��ΪUTF-8
		HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(entity);
		// ��ȡClient���󣬻�ȡ������Ӧ
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getBoolean("d");
		}
		return false;
	}
	
	// �������侯��״̬
	public static int UpdateXDingtongFallStat(String DeviceId, Boolean IsOpen) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "/OpenAPI/MobileService.asmx" + AppConfig.SETFALL);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
		obj.put("DeviceId", DeviceId);
		obj.put("IsOpen", IsOpen);
		// ������ת��ΪUTF-8
		HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(entity);
		// ��ȡClient���󣬻�ȡ������Ӧ
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}
	
	// ɾ����ע�б�
	public static int DeleteDeviceAttention(String DeviceAttentionId) throws Exception {
		HttpPost request = new HttpPost(AppConfig.URL
				+ "/OpenAPI/MobileService.asmx" + AppConfig.DELETEDEVICE);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		JSONObject obj = new JSONObject();
        obj.put("DeviceAttentionId", DeviceAttentionId);
		// ������ת��ΪUTF-8
		HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
		request.setEntity(entity);
		// ��ȡClient���󣬻�ȡ������Ӧ
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(HttpHelperUtils.cook);
		HttpResponse httpResponse = client.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != 404) {
			String result;
			result = EntityUtils.toString(httpResponse.getEntity());
			return new JSONObject(result).getInt("d");
		}
		return 0;
	}
	
	//��ȡ����ʱ������
			public static DataWa GetMuteTimeByDeviceId(String DeviceId) throws Exception {
				HttpPost request = new HttpPost(AppConfig.URL
						+ "OpenAPI/MobileService.asmx" + AppConfig.GETDEMUTETIME);
				request.addHeader("Content-Type", "application/json; charset=utf-8");
				JSONObject obj = new JSONObject();
				obj.put("DeviceId", DeviceId);
				HttpEntity bodyEntity;
				bodyEntity = new StringEntity(obj.toString(), "utf8");
				request.setEntity(bodyEntity);
				DefaultHttpClient client = new DefaultHttpClient();
				client.setCookieStore(HttpHelperUtils.cook);
				HttpResponse httpResponse = client.execute(request);
				if (httpResponse.getStatusLine().getStatusCode() != 404) {
					String result = EntityUtils.toString(httpResponse.getEntity());
					JSONObject json = new JSONObject(result);
					String aa = json.getString("d");
					return JSONHelper.parseObject(aa, DataWa.class);
				}
				return null;
			}
			// �Ʋ��������·�
			public static int Dev_dingtong_SendPedometer(String DeviceId, int type)
					throws Exception {
				HttpPost request = new HttpPost(AppConfig.URL
						+ "/OpenAPI/MobileService.asmx" + AppConfig.DEVSENDPED);
				request.addHeader("Content-Type", "application/json; charset=utf-8");
				JSONObject obj = new JSONObject();
				obj.put("DeviceId", DeviceId);
				obj.put("type", type);
				// ������ת��ΪUTF-8
				HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
				request.setEntity(entity);
				// ��ȡClient���󣬻�ȡ������Ӧ
				DefaultHttpClient client = new DefaultHttpClient();
				client.setCookieStore(HttpHelperUtils.cook);
				HttpResponse httpResponse = client.execute(request);
				if (httpResponse.getStatusLine().getStatusCode() != 404) {
					String result;
					result = EntityUtils.toString(httpResponse.getEntity());
					return new JSONObject(result).getInt("d");
				}
				return 0;
			}
			
			// �Ʋ��������·���ȡ����
			public static String GetStepDataByDeviceId(String DeviceId)
											throws Exception {
				HttpPost request = new HttpPost(AppConfig.URL
						+ "/OpenAPI/MobileService.asmx" + AppConfig.GETEPEDOMETERDATA);
				request.addHeader("Content-Type", "application/json; charset=utf-8");
				JSONObject obj = new JSONObject();
		        obj.put("DeviceId", DeviceId);
				// ������ת��ΪUTF-8
				HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
				request.setEntity(entity);
				// ��ȡClient���󣬻�ȡ������Ӧ
				DefaultHttpClient client = new DefaultHttpClient();
				client.setCookieStore(HttpHelperUtils.cook);
				HttpResponse httpResponse = client.execute(request);
				if (httpResponse.getStatusLine().getStatusCode() != 404) {
					String result;
					result = EntityUtils.toString(httpResponse.getEntity());
					return new JSONObject(result).getString("d");
				}
				return null;
			}
			
			//�ύ��������
			public static int Dev_dingtong_SendAlarm(String DeviceId,ArrayList<DevStuAlarmConfig> Data) throws Exception {
				try {
					HttpPost request = new HttpPost(AppConfig.URL
							+ "OpenAPI/MobileService.asmx"
							+ AppConfig.GETDINTONGALARM);
					request.addHeader("Content-Type", "application/json; charset=utf-8");
					JSONObject obj = new JSONObject();

					GetDevStuAlarmEntityByDeviceIdResult asd=new GetDevStuAlarmEntityByDeviceIdResult();
					asd.setDeviceId(DeviceId);
					asd.setData(Data);
					Gson ggg = new Gson();
					
					String Str = "{entity:" + ggg.toJson(asd) + "}";
					HttpEntity bodyEntity;
					bodyEntity = new StringEntity(Str, "utf8");
					request.setEntity(bodyEntity);
					DefaultHttpClient client = new DefaultHttpClient();
					client.setCookieStore(HttpHelperUtils.cook);
					HttpResponse httpResponse = client.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						String result;
						result = EntityUtils.toString(httpResponse.getEntity());
						return new JSONObject(result).getInt("d");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				return 0;
			}
			
			// ��ȡ��������
			public static GetDevStuAlarmEntityByDeviceIdResult GetDevStuAlarmEntityByDeviceId(String DeviceId)
					throws Exception {
				HttpPost request = new HttpPost(AppConfig.URL
						+ "OpenAPI/MobileService.asmx" + AppConfig.GETALARM);
				request.addHeader("Content-Type", "application/json; charset=utf-8");
				JSONObject obj = new JSONObject();
				obj.put("DeviceId", DeviceId);
				HttpEntity bodyEntity;
				bodyEntity = new StringEntity(obj.toString(), "utf8");
				request.setEntity(bodyEntity);
				DefaultHttpClient client = new DefaultHttpClient();
				client.setCookieStore(HttpHelperUtils.cook);
				HttpResponse httpResponse = client.execute(request);
				if (httpResponse.getStatusLine().getStatusCode() != 404) {
					String result = EntityUtils.toString(httpResponse.getEntity());
					// result = paseString(result);
					JSONObject json = new JSONObject(result);
					String aa = json.getString("d");
					GetDevStuAlarmEntityByDeviceIdResult table = JSONHelper.parseObject(aa,
							GetDevStuAlarmEntityByDeviceIdResult.class);
					if (table != null) {
						return table;
					}
				}
				return null;
			}
			
			//����load_time
			public static int Dev_dingtong_SettingInterval(String DeviceId, int val) throws Exception {
				HttpPost request = new HttpPost(AppConfig.URL
						+ "/OpenAPI/MobileService.asmx" + AppConfig.INTERVAL);
				request.addHeader("Content-Type", "application/json; charset=utf-8");
				JSONObject obj = new JSONObject();
				obj.put("DeviceId", DeviceId);
				obj.put("val", val);
				// ������ת��ΪUTF-8
				HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
				request.setEntity(entity);
				// ��ȡClient���󣬻�ȡ������Ӧ
				DefaultHttpClient client = new DefaultHttpClient();
				client.setCookieStore(HttpHelperUtils.cook);
				HttpResponse httpResponse = client.execute(request);
				if (httpResponse.getStatusLine().getStatusCode() != 404) {
					String result;
					result = EntityUtils.toString(httpResponse.getEntity());
					return new JSONObject(result).getInt("d");
				}
				return 0;
			}
			
			// ��ȡ��������б�
			public static String GetXdtFamilyNumbers(String DeviceId)
					throws Exception {
				HttpPost request = new HttpPost(AppConfig.URL
						+ "/OpenAPI/MobileService.asmx" + AppConfig.FAMILY);
				request.addHeader("Content-Type", "application/json; charset=utf-8");
				JSONObject obj = new JSONObject();
				obj.put("DeviceId", DeviceId);
				// ������ת��ΪUTF-8
				HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
				request.setEntity(entity);
				// ��ȡClient���󣬻�ȡ������Ӧ
				DefaultHttpClient client = new DefaultHttpClient();
				client.setCookieStore(HttpHelperUtils.cook);
				HttpResponse httpResponse = client.execute(request);
				if (httpResponse.getStatusLine().getStatusCode() != 404) {
					String result;
					result = EntityUtils.toString(httpResponse.getEntity());
					return new JSONObject(result).getString("d");
				}
				return null;
			}
			
			// ����������
			public static int XdtSettingFamilyNumber(String DeviceId, String PhoneNum, int num_index) throws Exception {
				HttpPost request = new HttpPost(AppConfig.URL
						+ "/OpenAPI/MobileService.asmx" + AppConfig.FAMLIYNUMBER);
				request.addHeader("Content-Type", "application/json; charset=utf-8");
				JSONObject obj = new JSONObject();
				obj.put("DeviceId", DeviceId);
				obj.put("PhoneNum", PhoneNum);
				obj.put("num_index", num_index);
				// ������ת��ΪUTF-8
				HttpEntity entity = new StringEntity(obj.toString(), "utf-8");
				request.setEntity(entity);
				// ��ȡClient���󣬻�ȡ������Ӧ
				DefaultHttpClient client = new DefaultHttpClient();
				client.setCookieStore(HttpHelperUtils.cook);
				HttpResponse httpResponse = client.execute(request);
				if (httpResponse.getStatusLine().getStatusCode() != 404) {
					String result;
					result = EntityUtils.toString(httpResponse.getEntity());
					return new JSONObject(result).getInt("d");
				}
				return 0;
			}								
}
