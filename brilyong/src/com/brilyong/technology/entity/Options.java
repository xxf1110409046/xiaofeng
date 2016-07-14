package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;

public class Options implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8270178953035134554L;
	private String LoginName;
	private int MsgIndex;
	private int pageSize;
	public String getLoginName() {
		return LoginName;
	}
	public void setLoginName(String loginName) {
		LoginName = loginName;
	}
	public int getMsgIndex() {
		return MsgIndex;
	}
	public void setMsgIndex(int msgIndex) {
		MsgIndex = msgIndex;
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
