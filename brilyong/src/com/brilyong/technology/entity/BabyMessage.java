package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.Date;

import android.provider.ContactsContract.Data;

public class BabyMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private String Id;
	 private String Name;
	 private int relationship;
	 private Date Birthday;
	 private double Height;
	 private double weight;
	 private int sex;
	 private int Grade;
	 private boolean IsAgree;
	 
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		this.Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
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
		this.Birthday = birthday;
	}
	public double getHeight() {
		return Height;
	}
	public void setHeight(double height) {
		this.Height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getGrade() {
		return Grade;
	}
	public void setGrade(int grade) {
		this.Grade = grade;
	}
	public boolean isAgree() {
		return IsAgree;
	}
	public void setAgree(boolean isAgree) {
		this.IsAgree = isAgree;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
