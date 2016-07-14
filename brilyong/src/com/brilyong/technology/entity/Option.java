package com.brilyong.technology.entity;

import com.brilyong.technology.httputils.JSONHelper;

public class Option {
	private static final long serialVersionUID = 1L;
	private String DeviceId;
	public int PageSize;
	public int PageIndex;



	public static long getSerialversionuid() {
		return serialVersionUID;

	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public int getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		PageIndex = pageIndex;
	}

	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}


	public String getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}

}
