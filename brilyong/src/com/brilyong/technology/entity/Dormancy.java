package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;

public class Dormancy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3543415218786988977L;
 
	 private String DeviceId;
	 private boolean IsOpen;
	 private int StartHour;
	 private int StartMinute;
	 private int EndHour;
	 private int EndMinute;
	public String getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}
	public boolean getIsOpen() {
		return IsOpen;
	}
	public void setIsOpen(boolean isOpen) {
		IsOpen = isOpen;
	}
	public int getStartHour() {
		return StartHour;
	}
	public void setStartHour(int startHour) {
		StartHour = startHour;
	}
	public int getStartMinute() {
		return StartMinute;
	}
	public void setStartMinute(int startMinute) {
		StartMinute = startMinute;
	}
	public int getEndHour() {
		return EndHour;
	}
	public void setEndHour(int endHour) {
		EndHour = endHour;
	}
	public int getEndMinute() {
		return EndMinute;
	}
	public void setEndMinute(int endMinute) {
		EndMinute = endMinute;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String toString(){
		return JSONHelper.toJSON(this);
	}
}
