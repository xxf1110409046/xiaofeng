package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;

public class DevHunterDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int Battery;

	public boolean GpsState;

	public boolean Acc;

	public boolean Charging;

	public int Gsm;

	public String ListenNum;

	public int PedometerState;

	public int WorkMode;

	public boolean BluetoothState;

	public int timeZone;

	public String TimeRest;

	public String sos1;
	public String sos2;
	public String sos3;
	public String sos4;
	public String sos5;
	public String sos6;
	public String sos7;
	public String sos8;

	public String getSos1() {
		return sos1;
	}

	public void setSos1(String sos1) {
		this.sos1 = sos1;
	}

	public String getSos2() {
		return sos2;
	}

	public void setSos2(String sos2) {
		this.sos2 = sos2;
	}

	public String getSos3() {
		return sos3;
	}

	public void setSos3(String sos3) {
		this.sos3 = sos3;
	}

	public String getSos4() {
		return sos4;
	}

	public void setSos4(String sos4) {
		this.sos4 = sos4;
	}

	public String getSos5() {
		return sos5;
	}

	public void setSos5(String sos5) {
		this.sos5 = sos5;
	}

	public String getSos6() {
		return sos6;
	}

	public void setSos6(String sos6) {
		this.sos6 = sos6;
	}

	public String getSos7() {
		return sos7;
	}

	public void setSos7(String sos7) {
		this.sos7 = sos7;
	}

	public String getSos8() {
		return sos8;
	}

	public void setSos8(String sos8) {
		this.sos8 = sos8;
	}

	public int getBattery() {
		return Battery;
	}

	public void setBattery(int battery) {
		Battery = battery;
	}

	public boolean isGpsState() {
		return GpsState;
	}

	public void setGpsState(boolean gpsState) {
		GpsState = gpsState;
	}

	public boolean isAcc() {
		return Acc;
	}

	public void setAcc(boolean acc) {
		Acc = acc;
	}

	public boolean isCharging() {
		return Charging;
	}

	public void setCharging(boolean charging) {
		Charging = charging;
	}

	public int getGsm() {
		return Gsm;
	}

	public void setGsm(int gsm) {
		Gsm = gsm;
	}

	public String getListenNum() {
		return ListenNum;
	}

	public void setListenNum(String listenNum) {
		ListenNum = listenNum;
	}

	public int getPedometerState() {
		return PedometerState;
	}

	public void setPedometerState(int pedometerState) {
		PedometerState = pedometerState;
	}

	public int getWorkMode() {
		return WorkMode;
	}

	public void setWorkMode(int workMode) {
		WorkMode = workMode;
	}

	public boolean isBluetoothState() {
		return BluetoothState;
	}

	public void setBluetoothState(boolean bluetoothState) {
		BluetoothState = bluetoothState;
	}

	public int getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(int timeZone) {
		this.timeZone = timeZone;
	}

	public String getTimeRest() {
		return TimeRest;
	}

	public void setTimeRest(String timeRest) {
		TimeRest = timeRest;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;

	}

	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}

}
