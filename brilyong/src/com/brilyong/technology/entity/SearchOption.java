package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.Date;

import com.brilyong.technology.httputils.JSONHelper;

public class SearchOption implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String DeviceId;
	private Date StartDate;
	private Date EndDate;
	private boolean LoadLBS;
	private double MinSpeed;

	public String getDeviceId() {
		return DeviceId;
	}

	@Override
	public String toString() {

		return JSONHelper.toJSON(this);
	}

	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public boolean getLoadLBS() {
		return LoadLBS;
	}

	public void setLoadLBS(boolean loadLBS) {
		LoadLBS = loadLBS;
	}

	public double isMinSpeed() {
		return MinSpeed;
	}

	public void setMinSpeed(double minSpeed) {
		MinSpeed = minSpeed;
	}

}
