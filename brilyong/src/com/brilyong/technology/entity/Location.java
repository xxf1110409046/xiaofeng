package com.brilyong.technology.entity;

import java.util.Date;

import com.brilyong.technology.httputils.JSONHelper;

public class Location {
	private Date GpsTime;
	private int LocationType;
	private double Lat;
	private double Lng;
	private double BLat;
	private double BLng;
	private double GLat;
	private double GLng;
	private int LocationState;
	private String DeviceId;
	private double Speed;
	private double Source;
	private int Battery;
	private int Signal;
	private int State;
	private int AlarmType;
	private double Altitude;
	private double HDOP;
	private double Mileage;
	private int Mcc;
	private int Mnc;
	private int Lac;
	private int Cid;
	private int DBIndex;
	private String Address;

	public Date getGpsTime() {
		return GpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		GpsTime = gpsTime;
	}

	public int getLocationType() {
		return LocationType;
	}

	public void setLocationType(int locationType) {
		LocationType = locationType;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		Lat = lat;
	}

	public double getLng() {
		return Lng;
	}

	public void setLng(double lng) {
		Lng = lng;
	}

	public double getBLat() {
		return BLat;
	}

	public void setBLat(double bLat) {
		BLat = bLat;
	}

	public double getBLng() {
		return BLng;
	}

	public void setBLng(double bLng) {
		BLng = bLng;
	}

	public double getGLat() {
		return GLat;
	}

	public void setGLat(double gLat) {
		GLat = gLat;
	}

	public double getGLng() {
		return GLng;
	}

	public void setGLng(double gLng) {
		GLng = gLng;
	}

	public int getLocationState() {
		return LocationState;
	}

	public void setLocationState(int locationState) {
		LocationState = locationState;
	}

	public String getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}

	public double getSpeed() {
		return Speed;
	}

	public void setSpeed(double speed) {
		Speed = speed;
	}

	public double getSource() {
		return Source;
	}

	public void setSource(double source) {
		Source = source;
	}

	public int getBattery() {
		return Battery;
	}

	public void setBattery(int battery) {
		Battery = battery;
	}

	public int getSignal() {
		return Signal;
	}

	public void setSignal(int signal) {
		Signal = signal;
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public int getAlarmType() {
		return AlarmType;
	}

	public void setAlarmType(int alarmType) {
		AlarmType = alarmType;
	}

	public double getAltitude() {
		return Altitude;
	}

	public void setAltitude(double altitude) {
		Altitude = altitude;
	}

	public double getHDOP() {
		return HDOP;
	}

	public void setHDOP(double hDOP) {
		HDOP = hDOP;
	}

	public double getMileage() {
		return Mileage;
	}

	public void setMileage(double mileage) {
		Mileage = mileage;
	}

	public int getMcc() {
		return Mcc;
	}

	public void setMcc(int mcc) {
		Mcc = mcc;
	}

	public int getMnc() {
		return Mnc;
	}

	public void setMnc(int mnc) {
		Mnc = mnc;
	}

	public int getLac() {
		return Lac;
	}

	public void setLac(int lac) {
		Lac = lac;
	}

	public int getCid() {
		return Cid;
	}

	public void setCid(int cid) {
		Cid = cid;
	}

	public int getDBIndex() {
		return DBIndex;
	}

	public void setDBIndex(int dBIndex) {
		DBIndex = dBIndex;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}

}
