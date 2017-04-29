package com.viralandroid.androidtabsatbottom;

import android.bluetooth.BluetoothDevice;

/**
 * Created by dinhtho on 06/04/2017.
 */

public class Device {
    private BluetoothDevice device;

    public Device(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothDevice getDevice() {
        return device;
    }
}
