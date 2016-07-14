package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;


public class BindDeviceResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private String message_command;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage_command() {
		return message_command;
	}

	public void setMessage_command(String message_command) {
		this.message_command = message_command;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}

}

