package com.bluetooth.le;

import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Created by caoxuanphong on    7/25/16.
 */
public class FioTBluetoothCharacteristic {
    private String uuid;
    private BluetoothGattCharacteristic characteristic;
    private boolean notify;

    public FioTBluetoothCharacteristic(String uuid, boolean notify) {
        this.uuid = uuid;
        this.notify = notify;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BluetoothGattCharacteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }
}
