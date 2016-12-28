package info.androidhive.recyclerview;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by dinhtho on 21/12/2016.
 */

public class Device {

    private String name, mac;
    private String rssi;
    private double lastTimeUpdateRssi;

    public void setLastTimeUpdateRssi(double lastTimeUpdateRssi) {
        this.lastTimeUpdateRssi = lastTimeUpdateRssi;
        Log.i(TAG, "setLastTimeUpdateRssi: "+"giatrithaydoi");
    }

    public double getLastTimeUpdateRssi() {
        return lastTimeUpdateRssi;
    }



    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    private boolean isConnect;

    public boolean isConnect() {
        return isConnect;
    }

    public Device(String name, String mac, String rssi) {
        this.name = name;
        this.rssi = rssi;
        this.mac = mac;
        this.isConnect=true;



    }

    public String getName() {
        return name;
    }

    public String getMac() {
        return mac;
    }




    public String getRssi() {
        return rssi;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }



    public void setRssi(String rssi) {
        this.rssi = rssi;
    }
}
