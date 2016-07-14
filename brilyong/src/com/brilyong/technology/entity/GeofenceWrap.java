package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.brilyong.technology.httputils.JSONHelper;

public class GeofenceWrap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int total;

	private ArrayList<Geofence> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ArrayList<Geofence> getRows() {
		return rows;
	}

	public void setRows(ArrayList<Geofence> rows) {
		this.rows = rows;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return JSONHelper.toJSON(this);
	}

}
