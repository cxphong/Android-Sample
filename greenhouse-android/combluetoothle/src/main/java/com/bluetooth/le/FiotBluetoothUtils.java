package com.bluetooth.le;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import java.util.Set;

/**
 * Created by caoxuanphong on 4/10/17.
 */

public class FiotBluetoothUtils {

    public static Set<BluetoothDevice> getBondedDevices(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        return adapter.getBondedDevices();
    }

}
