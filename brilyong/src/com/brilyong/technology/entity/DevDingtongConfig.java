package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;

public class DevDingtongConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String phone1;
	public String phone2;
	public String phone3;

	public String sosPhone;

	public String listenerPhone;

	public String VERSION;

	public String APN;

	public String TIMEZON;

	public String WORKMODE;

	public String FIREWALL;

	public String BT;

	public String RFID;
	public String PEDOMETER;

	public int FinishStepCount;
	public int ToTalStepCount;

	public int RFIDNoti;

	public int stepTask;

	public boolean Bluetooth;
 
	public String dormancy;
	
	public String getDormancy() {
		return dormancy;
	}

	public void setDormancy(String dormancy) {
		this.dormancy = dormancy;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getSosPhone() {
		return sosPhone;
	}

	public void setSosPhone(String sosPhone) {
		this.sosPhone = sosPhone;
	}

	public String getListenerPhone() {
		return listenerPhone;
	}

	public void setListenerPhone(String listenerPhone) {
		this.listenerPhone = listenerPhone;
	}

	public String getVERSION() {
		return VERSION;
	}

	public void setVERSION(String vERSION) {
		VERSION = vERSION;
	}

	public String getAPN() {
		return APN;
	}

	public void setAPN(String aPN) {
		APN = aPN;
	}

	public String getTIMEZON() {
		return TIMEZON;
	}

	public void setTIMEZON(String tIMEZON) {
		TIMEZON = tIMEZON;
	}

	public String getWORKMODE() {
		return WORKMODE;
	}

	public void setWORKMODE(String wORKMODE) {
		WORKMODE = wORKMODE;
	}

	public String getFIREWALL() {
		return FIREWALL;
	}

	public void setFIREWALL(String fIREWALL) {
		FIREWALL = fIREWALL;
	}

	public String getBT() {
		return BT;
	}

	public void setBT(String bT) {
		BT = bT;
	}

	public String getRFID() {
		return RFID;
	}

	public void setRFID(String rFID) {
		RFID = rFID;
	}

	public String getPEDOMETER() {
		return PEDOMETER;
	}

	public void setPEDOMETER(String pEDOMETER) {
		PEDOMETER = pEDOMETER;
	}

	public int getFinishStepCount() {
		return FinishStepCount;
	}

	public void setFinishStepCount(int finishStepCount) {
		FinishStepCount = finishStepCount;
	}

	public int getToTalStepCount() {
		return ToTalStepCount;
	}

	public void setToTalStepCount(int toTalStepCount) {
		ToTalStepCount = toTalStepCount;
	}

	public int getRFIDNoti() {
		return RFIDNoti;
	}

	public void setRFIDNoti(int rFIDNoti) {
		RFIDNoti = rFIDNoti;
	}

	public int getStepTask() {
		return stepTask;
	}

	public void setStepTask(int stepTask) {
		this.stepTask = stepTask;
	}

	public boolean isBluetooth() {
		return Bluetooth;
	}

	public void setBluetooth(boolean bluetooth) {
		Bluetooth = bluetooth;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}

}
