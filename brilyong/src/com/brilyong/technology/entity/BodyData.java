package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;

public class BodyData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2371950850518766436L;
	private int pageSize;
	private int msgIndex;
	private String deviceId;
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public int getMsgIndex() {
		return msgIndex;
	}
	public void setMsgIndex(int msgIndex) {
		this.msgIndex = msgIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String toString(){
	    return JSONHelper.toJSON(this);
	}
}
