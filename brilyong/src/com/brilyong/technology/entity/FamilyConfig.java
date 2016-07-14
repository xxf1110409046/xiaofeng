package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;



public class FamilyConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5514092012631569539L;
	private int index;
    private String phone;
    private String Name;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String toString(){
		
		return JSONHelper.toJSON(this);
		
	}
}
