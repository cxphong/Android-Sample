package com.bluetooth.le;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by caoxuanphong on    7/23/16.
 */
public class FioTScanManager {
    private static final String TAG = "FioTScanManager";
    private String filter = "";
    private ScanManagerListener listener;
    private FioTBluetoothLE ble;
    private volatile boolean ignoreExist;
    private ArrayList<BLEDevice> list = new ArrayList<>();

    public interface ScanManagerListener {
        void onFoundDevice(BluetoothDevice device,
                           final int rssi);
    }

    public FioTScanManager(Context context) {
        ble = new FioTBluetoothLE(context);
        ble.setBluetoothLEScanListener(scanListener);
    }

    /**
     * Start scan ble device
     * @param filter
     * @param ignoreExist
     * @param listener
     */
    public void start(String filter, boolean ignoreExist, ScanManagerListener listener) {
        this.filter = filter;
        this.ignoreExist = ignoreExist;
        this.listener = listener;
        ble.startScanning();
    }

    /**
     * Stop scan ble device
     */
    public void stop() {
        ble.stopScanning();
        list.clear();
    }

    public void end() {
        Log.i(TAG, "Scan manager end");
        ble.end();
    }

    FioTBluetoothLE.BluetoothLEScanListener scanListener = new FioTBluetoothLE.BluetoothLEScanListener() {
        @Override
        public void onFoundDevice(BluetoothDevice device, int rssi) {
            synchronized (this) {
                if (device.getName() == null) return;

                if (device.getName().contains(filter)) {

                    if (!exist(device)) {
                        list.add(new BLEDevice(rssi, device));
                    } else if (ignoreExist) {
                        return;
                    }

                    if (listener != null) {
                        listener.onFoundDevice(device, rssi);
                    }
                }
            }
        }
    };

    private boolean exist(BluetoothDevice device) {
        for (BLEDevice d : list) {
            if (d.device.getAddress().equalsIgnoreCase(device.getAddress())) {
                return true;
            }
        }

        return false;
    }

    class BLEDevice {
        BluetoothDevice device;
        int rssi;

        public BLEDevice(int rssi, BluetoothDevice device) {
            this.rssi = rssi;
            this.device = device;
        }
    }

}

