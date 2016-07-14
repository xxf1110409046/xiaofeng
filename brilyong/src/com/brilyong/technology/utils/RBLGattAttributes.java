/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.brilyong.technology.utils;

/**
 * This class includes a small subset of standard GATT attributes for
 * demonstration purposes.
 */
public class RBLGattAttributes {
	public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
	public static String BLE_SHIELD_TX = "713d0003-503e-4c75-ba94-3148f18d941e";
	public static String BLE_SHIELD_RX = "713d0002-503e-4c75-ba94-3148f18d941e";
	public static String BLE_SHIELD_SERVICE = "713d0000-503e-4c75-ba94-3148f18d941e";
	public static String IMMEDIATE_ALERT = "00001802-0000-1000-8000-00805f9b34fb";
	public static String ALERT_LEVEL = "00002a06-0000-1000-8000-00805f9b34fb";
	public final static String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";
	public final static String SEDCONNT = "SEDCONNT";
	public final static String STARTALARMANIMATION = "STARTALARMANIMATION";
	public final static String STOPALARMANIMATION = "STOPALARMANIMATION";
	public final static String SCANNING = "SCANNING";
	public final static String DISCONNECT = "DISCONNECT";
	public final static String LISTVIEWCHANGED = "LISTVIEWCHANGED";
	public final static String ACTION_GATT_DISCONNECTED = "ACTION_GATT_DISCONNECTED";
	public final static String ACTION_GATT_SERVICES_DISCOVERED = "ACTION_GATT_SERVICES_DISCOVERED";
	public final static String ACTION_GATT_RSSI = "ACTION_GATT_RSSI";
	public final static String ACTION_DATA_AVAILABLE = "ACTION_DATA_AVAILABLE";
	public final static String EXTRA_DATA = "EXTRA_DATA";
	public static boolean falg = true;
	public static boolean alarmState;
	public static boolean isAnima = false;
	// public static boolean STAT = false;
	// public static int progress = 30;
}
