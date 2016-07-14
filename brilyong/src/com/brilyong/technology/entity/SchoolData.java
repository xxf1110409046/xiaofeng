package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.Date;

import com.brilyong.technology.httputils.JSONHelper;

import android.provider.ContactsContract.Data;

public class SchoolData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8868293119347306093L;
	
	 private String id;
	 private String DeviceId;
	 private int Type;
	 private String RFIDCode;
	 private Date Time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public String getRFIDCode() {
		return RFIDCode;
	}
	public void setRFIDCode(String rFIDCode) {
		RFIDCode = rFIDCode;
	}
	public Date getTime() {
		return Time;
	}
	public void setTime(Date time) {
		Time = time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String toString(){
		return JSONHelper.toJSON(this);
	}
}
