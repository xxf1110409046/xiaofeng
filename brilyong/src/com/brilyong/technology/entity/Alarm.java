package com.brilyong.technology.entity;

import java.io.Serializable;

public class Alarm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6972656146616819072L;
	private String text_content;
	private boolean alarm_checked;
	public String getText_content() {
		return text_content;
	}
	public void setText_content(String text_content) {
		this.text_content = text_content;
	}
	public boolean getAlarm_checked() {
		return alarm_checked;
	}
	public void setAlarm_checked(boolean alarm_checked) {
		this.alarm_checked = alarm_checked;
	}
	
}
