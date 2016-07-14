package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;

public class CheckUpdateResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private boolean update;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}
}
