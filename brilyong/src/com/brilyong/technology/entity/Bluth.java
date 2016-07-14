package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.List;


public class Bluth implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4551025166341152889L;
	
	private List<String> bluth;

	public List<String> getBluth() {
		return bluth;
	}

	public void setBluth(List<String> bluth) {
		this.bluth = bluth;
	}
	
}
