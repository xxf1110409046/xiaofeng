package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.Date;

public class DeviceAttention implements Serializable {

	private static final long serialVersionUID = 1L;
	private String Id;
	private String DeviceId;
	private String UserId;
	private boolean IsAdmin;
	private String Name;
	private int relationship;
	private Date Birthday;
	private double Height;
	private double weight;
	private int Grade;
	private boolean IsAgree;
	private boolean static_push;
	private boolean activity_push;

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

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public boolean isIsAdmin() {
		return IsAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		IsAdmin = isAdmin;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getRelationship() {
		return relationship;
	}

	public void setRelationship(int relationship) {
		this.relationship = relationship;
	}

	public Date getBirthday() {
		return Birthday;
	}

	public void setBirthday(Date birthday) {
		Birthday = birthday;
	}

	public double getHeight() {
		return Height;
	}

	public void setHeight(double height) {
		Height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getGrade() {
		return Grade;
	}

	public void setGrade(int grade) {
		Grade = grade;
	}

	public boolean isIsAgree() {
		return IsAgree;
	}

	public void setIsAgree(boolean isAgree) {
		IsAgree = isAgree;
	}

	public boolean isStatic_push() {
		return static_push;
	}

	public void setStatic_push(boolean static_push) {
		this.static_push = static_push;
	}

	public boolean isActivity_push() {
		return activity_push;
	}

	public void setActivity_push(boolean activity_push) {
		this.activity_push = activity_push;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
