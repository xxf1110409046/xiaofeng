package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;

public class DataWa implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String DeviceId;
	private int StartHour1;
	private int StartMinute1;
	private int EndHour1;
	private int EndMinute1;
	private int StartHour2;
	private int StartMinute2;
	private int EndHour2;
	private int EndMinute2;
	private int Repeat;	
	private Boolean IsOpen1;
	private Boolean IsOpen2;
	  
	public Boolean getIsOpen1() {
		return IsOpen1;
	}
	public void setIsOpen1(Boolean isOpen1) {
		IsOpen1 = isOpen1;
	}
	public Boolean getIsOpen2() {
		return IsOpen2;
	}
	public void setIsOpen2(Boolean isOpen2) {
		IsOpen2 = isOpen2;
	}
	public String getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}
	
	public int getRepeat() {
		return Repeat;
	}
	public void setRepeat(int repeat) {
		Repeat = repeat;
	}
	
	public int getStartHour1() {
		return StartHour1;
	}
	public void setStartHour1(int startHour1) {
		StartHour1 = startHour1;
	}
	public int getStartMinute1() {
		return StartMinute1;
	}
	public void setStartMinute1(int startMinute1) {
		StartMinute1 = startMinute1;
	}
	public int getEndHour1() {
		return EndHour1;
	}
	public void setEndHour1(int endHour1) {
		EndHour1 = endHour1;
	}
	public int getEndMinute1() {
		return EndMinute1;
	}
	public void setEndMinute1(int endMinute1) {
		EndMinute1 = endMinute1;
	}
	public int getStartHour2() {
		return StartHour2;
	}
	public void setStartHour2(int startHour2) {
		StartHour2 = startHour2;
	}
	public int getStartMinute2() {
		return StartMinute2;
	}
	public void setStartMinute2(int startMinute2) {
		StartMinute2 = startMinute2;
	}
	public int getEndHour2() {
		return EndHour2;
	}
	public void setEndHour2(int endHour2) {
		EndHour2 = endHour2;
	}
	public int getEndMinute2() {
		return EndMinute2;
	}
	public void setEndMinute2(int endMinute2) {
		EndMinute2 = endMinute2;
	}
	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}
	
}
