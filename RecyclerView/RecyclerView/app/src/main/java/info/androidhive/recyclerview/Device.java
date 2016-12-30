package info.androidhive.recyclerview;

/**
 * Created by dinhtho on 21/12/2016.
 */


public class Device {

    private String name, mac;
    private String rssi;
    private boolean isConnect;
    private long lastTimeUpdateRssi;

    public void setLastTimeUpdateRssi(long lastTimeUpdateRssi) {
        this.lastTimeUpdateRssi = lastTimeUpdateRssi;
    }

    public double getLastTimeUpdateRssi() {
        return lastTimeUpdateRssi;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

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
