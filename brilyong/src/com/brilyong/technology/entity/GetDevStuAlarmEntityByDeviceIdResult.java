package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.brilyong.technology.httputils.JSONHelper;


public class GetDevStuAlarmEntityByDeviceIdResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8094398727210822196L;
	String DeviceId;
	ArrayList<DevStuAlarmConfig> Data;
	public String getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}
	public ArrayList<DevStuAlarmConfig> getData() {
		return Data;
	}
	public void setData(ArrayList<DevStuAlarmConfig> data) {
		Data = data;
	}
	
	public String toString(){
		return JSONHelper.toJSON(this);
		
	}
}
