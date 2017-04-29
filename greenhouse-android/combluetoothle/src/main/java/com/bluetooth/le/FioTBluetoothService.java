package com.bluetooth.le;

import android.bluetooth.BluetoothGattService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoxuanphong on    7/25/16.
 */
public class FioTBluetoothService {
    private BluetoothGattService service;
    private String uuid;
    private List<FioTBluetoothCharacteristic> characteristics = new ArrayList<>();

    public FioTBluetoothService(String uuid, List<FioTBluetoothCharacteristic> characteristics) {
        this.uuid = uuid;
        this.characteristics = characteristics;
    }

    public String getUuid() {
        return uuid;
    }

    public List<FioTBluetoothCharacteristic> getCharacteristics() {
        return characteristics;
    }
}
