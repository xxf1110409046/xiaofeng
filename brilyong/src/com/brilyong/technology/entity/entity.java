package com.brilyong.technology.entity;

import com.brilyong.technology.httputils.JSONHelper;

public class entity {
	private static final long serialVersionUID = 1L;
	private String DeviceId;
	private int Type;
	private String Data;
	private double radius;
	private String name;

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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {

		return JSONHelper.toJSON(this);
	}

}
