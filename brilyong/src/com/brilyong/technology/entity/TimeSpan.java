package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;

public class TimeSpan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int StartHour;
	private int StartMinute;
	private int EndHour;
	private int EndMinute;
	private boolean IsOpen1;
	private boolean IsOpen2;

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

	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}

	public boolean isIsOpen1() {
		return IsOpen1;
	}

	public void setIsOpen1(boolean isOpen1) {
		IsOpen1 = isOpen1;
	}

	public boolean isIsOpen2() {
		return IsOpen2;
	}

	public void setIsOpen2(boolean isOpen2) {
		IsOpen2 = isOpen2;
	}

}
