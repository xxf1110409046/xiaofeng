package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.Date;

import com.brilyong.technology.httputils.JSONHelper;

public class Geofence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String Id;
	public String DeviceId;
	public int Type;
	public String Data;

	public double radius;
	public String Name;
	public Date CreateTime;
	public boolean IsIn;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public boolean isIsIn() {
		return IsIn;
	}

	public void setIsIn(boolean isIn) {
		IsIn = isIn;
	}

	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}

}
