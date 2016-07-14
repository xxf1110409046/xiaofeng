package com.brilyong.technology.entity;

import java.io.Serializable;

import com.brilyong.technology.httputils.JSONHelper;

public class DevStuConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String verno;
	public String interval;
	public String pwd;
	public String sos_num1;
	public String sos_num2;
	public String sos_num3;
	public String sos_num4;
	public String move_dis;
	public String listen_num;
	public String firewall;

	public String gps_state;

	public String PometerState;

	public String getVerno() {
		return verno;
	}

	public void setVerno(String verno) {
		this.verno = verno;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSos_num1() {
		return sos_num1;
	}

	public void setSos_num1(String sos_num1) {
		this.sos_num1 = sos_num1;
	}

	public String getSos_num2() {
		return sos_num2;
	}

	public void setSos_num2(String sos_num2) {
		this.sos_num2 = sos_num2;
	}

	public String getSos_num3() {
		return sos_num3;
	}

	public void setSos_num3(String sos_num3) {
		this.sos_num3 = sos_num3;
	}

	public String getSos_num4() {
		return sos_num4;
	}

	public void setSos_num4(String sos_num4) {
		this.sos_num4 = sos_num4;
	}

	public String getMove_dis() {
		return move_dis;
	}

	public void setMove_dis(String move_dis) {
		this.move_dis = move_dis;
	}

	public String getListen_num() {
		return listen_num;
	}

	public void setListen_num(String listen_num) {
		this.listen_num = listen_num;
	}

	public String getFirewall() {
		return firewall;
	}

	public void setFirewall(String firewall) {
		this.firewall = firewall;
	}

	public String getGps_state() {
		return gps_state;
	}

	public void setGps_state(String gps_state) {
		this.gps_state = gps_state;
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
