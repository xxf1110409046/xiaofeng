package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.Date;

import com.brilyong.technology.httputils.JSONHelper;
@SuppressWarnings("serial")
public class UserMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Id;
	private String UserId;
	private Date AddDate;
	private String MsgData;
	private int SendCount;
	

	private int Type;

	private double Lat;
	private double Lng;
	private double GLat;
	private double GLng;
	private double BLat;
	private double BLng;
	private int LocationType;
	private Date GpsTime;
	private int Battery;
	private int agre_stat;
	public int getLocationType() {
		return LocationType;
	}

	public void setLocationType(int locationType) {
		LocationType = locationType;
	}

	public Date getGpsTime() {
		return GpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		GpsTime = gpsTime;
	}

	public int getBattery() {
		return Battery;
	}

	public void setBattery(int battery) {
		Battery = battery;
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

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public Date getAddDate() {
		return AddDate;
	}

	public void setAddDate(Date addDate) {
		AddDate = addDate;
	}

	public String getMsgData() {
		return MsgData;
	}

	public void setMsgData(String msgData) {
		MsgData = msgData;
	}

	public int getSendCount() {
		return SendCount;
	}

	public void setSendCount(int sendCount) {
		SendCount = sendCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public String toString(){
		return JSONHelper.toJSON(this);	
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getAgre_stat() {
		return agre_stat;
	}

	public void setAgre_stat(int agre_stat) {
		this.agre_stat = agre_stat;
	}
}
