package com.brilyong.technology.entity;

import java.util.ArrayList;

public class Acount {

	private static ArrayList<Device> devices;
	private static Device currentDevice;
	private static CheckUpdateResult aCheckUpdateResult;
	public static boolean unbindState = false;
	
	
	public static boolean getUnbindState() {
		return unbindState;
	}

	public static void setUnbindState(boolean unbindState) {
		Acount.unbindState = unbindState;
	}

	public static Device getCurrentDevice() {
		return currentDevice;
	}

	public static void setCurrentDevice(Device currentDevice) {
		Acount.currentDevice = currentDevice;
	}

	public static ArrayList<Device> getDevices() {
		return devices;
	}

	public static void setDevices(ArrayList<Device> devices) {
		Acount.devices = devices;
	}

	public static CheckUpdateResult getaCheckUpdateResult() {
		return aCheckUpdateResult;
	}

	public static void setaCheckUpdateResult(CheckUpdateResult aCheckUpdateResult) {
		Acount.aCheckUpdateResult = aCheckUpdateResult;
	}

}
