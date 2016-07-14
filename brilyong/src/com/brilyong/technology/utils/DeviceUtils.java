package com.brilyong.technology.utils;
import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;

public class DeviceUtils {

	public static BluetoothGattCharacteristic a ;
	public static List<BluetoothDevice> mDevice = new ArrayList<BluetoothDevice>();
	public static List<BluetoothGattService> BluetoothGattService = new ArrayList<BluetoothGattService>(); 
	public static List<BluetoothGattService> getBluetoothGattService() {
		return BluetoothGattService;
	}

	public static BluetoothGattCharacteristic getA() {
		return a;
	}

	public static void setA(BluetoothGattCharacteristic a) {
		DeviceUtils.a = a;
	}

	public static void setBluetoothGattService(
			List<BluetoothGattService> bluetoothGattService) {
		BluetoothGattService = bluetoothGattService;
	}

	public static List<BluetoothDevice> getmDevice() {
		return mDevice;
	}

	public static void setmDevice(List<BluetoothDevice> mDevice) {
		DeviceUtils.mDevice = mDevice;
	}

}
