package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;


public class DevStuAlarmConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6522243007197226101L;
	private boolean IsOpen;
	private String Hour;
	private String Minute;
	public boolean getIsOpen() {
		return IsOpen;
	}
	public void setIsOpen(boolean isOpen) {
		IsOpen = isOpen;
	}
	public String getHour() {
		return Hour;
	}
	public void setHour(String hour) {
		Hour = hour;
	}
	public String getMinute() {
		return Minute;
	}
	public void setMinute(String minute) {
		Minute = minute;
	}
	public String toString(){
		return JSONHelper.toJSON(this);
		
	}
}
