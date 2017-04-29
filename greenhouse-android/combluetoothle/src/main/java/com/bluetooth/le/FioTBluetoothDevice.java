package com.bluetooth.le;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoxuanphong on    7/25/16.
 *
 * Information about bluetooth device
 */
public class FioTBluetoothDevice {
    private BluetoothDevice device;
    private List<FioTBluetoothService> services = new ArrayList<>();
    public enum Status {
        disconnected,
        connecting,
        connected
    }

    private Status status;

    public FioTBluetoothDevice(BluetoothDevice device, List<FioTBluetoothService> services) {
        this.device = device;
        this.services = services;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
