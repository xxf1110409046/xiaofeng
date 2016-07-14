package com.brilyong.technology.activity;

import java.util.ArrayList;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class DeviceScanActivity extends ListActivity{

	    private BluetoothAdapter mBluetoothAdapter;
	    private boolean mScanning;
	    private Handler mHandler;

	    // Stops scanning after 10 seconds.
	    private static final long SCAN_PERIOD = 10000;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    final BluetoothManager bluetoothManager = 
				(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();
	    }
}
