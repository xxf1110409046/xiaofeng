package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.brilyong.technology.httputils.JSONHelper;



public class Data implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8930579672885531732L;
	private String __type;
	private String DeviceId;
	private ArrayList<FamilyConfig> FamilyList;
	
	   public String getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}


	public String toString(){
			return JSONHelper.toJSON(this);
    }

	public ArrayList<FamilyConfig> getFamilyList() {
		return FamilyList;
	}

	public void setFamilyList(ArrayList<FamilyConfig> familyList) {
		FamilyList = familyList;
	}

	public String get__type() {
		return __type;
	}

	public void set__type(String __type) {
		this.__type = __type;
	}
}
