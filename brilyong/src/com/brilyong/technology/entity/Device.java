package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.brilyong.technology.httputils.JSONHelper;

public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String Id;
	public String Name;
	public String UserId;
	public String Path;
	public int State;
	public String Icon;
	public int TypeId;
	public String SOSPhone;
	public String SimPhone;
	public Date CreateTime;
	public int DbIndex;
	public Date LastOnlineTime;
	public Date GpsTime;
	public int Battery;
	public int Signal;
	public double Speed;
	public double Lat;
	public double Lng;
	public double BLat;
	public double BLng;
	public double GLat;
	public double GLng;
	public int PositionType;
	public String GroupId;
	public Date OutServiceTime;
	public String UserName;
	public DevStuConfig DevStuConfig;
	public DevDingtongConfig DevDingtongConfig;

	public DevHunterDetail DevHunterDetail;

	public String Admin;

	public List<String> AttentionUsers;
	public String PometerState;
	public int IsOnline;
	
	public int getIsOnline() {
		return IsOnline;
	}

	public void setIsOnline(int isOnline) {
		IsOnline = isOnline;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public String getIcon() {
		return Icon;
	}

	public void setIcon(String icon) {
		Icon = icon;
	}

	public int getTypeId() {
		return TypeId;
	}

	public void setTypeId(int typeId) {
		TypeId = typeId;
	}

	public String getSOSPhone() {
		return SOSPhone;
	}

	public void setSOSPhone(String sOSPhone) {
		SOSPhone = sOSPhone;
	}

	public String getSimPhone() {
		return SimPhone;
	}

	public void setSimPhone(String simPhone) {
		SimPhone = simPhone;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public int getDbIndex() {
		return DbIndex;
	}

	public void setDbIndex(int dbIndex) {
		DbIndex = dbIndex;
	}

	public Date getLastOnlineTime() {
		return LastOnlineTime;
	}

	public void setLastOnlineTime(Date lastOnlineTime) {
		LastOnlineTime = lastOnlineTime;
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

	public int getSignal() {
		return Signal;
	}

	public void setSignal(int signal) {
		Signal = signal;
	}

	public double getSpeed() {
		return Speed;
	}

	public void setSpeed(double speed) {
		Speed = speed;
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

	public int getPositionType() {
		return PositionType;
	}

	public void setPositionType(int positionType) {
		PositionType = positionType;
	}

	public String getGroupId() {
		return GroupId;
	}

	public void setGroupId(String groupId) {
		GroupId = groupId;
	}

	public Date getOutServiceTime() {
		return OutServiceTime;
	}

	public void setOutServiceTime(Date outServiceTime) {
		OutServiceTime = outServiceTime;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public DevStuConfig getDevStuConfig() {
		return DevStuConfig;
	}

	public void setDevStuConfig(DevStuConfig devStuConfig) {
		DevStuConfig = devStuConfig;
	}

	public DevDingtongConfig getDevDingtongConfig() {
		return DevDingtongConfig;
	}

	public void setDevDingtongConfig(DevDingtongConfig devdoConfig) {
		DevDingtongConfig = devdoConfig;
	}

	public DevHunterDetail getDevHunterDetail() {
		return DevHunterDetail;
	}

	public void setDevHunterDetail(DevHunterDetail devHunterDetail) {
		DevHunterDetail = devHunterDetail;
	}

	public String getAdmin() {
		return Admin;
	}

	public void setAdmin(String admin) {
		Admin = admin;
	}

	public List<String> getAttentionUsers() {
		return AttentionUsers;
	}

	public void setAttentionUsers(List<String> attentionUsers) {
		AttentionUsers = attentionUsers;
	}

	public String getPometerState() {
		return PometerState;
	}

	public void setPometerState(String pometerState) {
		PometerState = pometerState;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}

}
