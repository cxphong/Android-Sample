package info.androidhive.recyclerview;

import com.idevicesinc.sweetblue.BleManager;

/**
 * Created by dinhtho on 21/12/2016.
 */


public class Device {

    private String rssi;
    private boolean isConnect;
    private long lastTimeUpdateRssi;
    private BleManager.DiscoveryListener.DiscoveryEvent event;

    public Device(BleManager.DiscoveryListener.DiscoveryEvent event) {
        this.rssi = String.valueOf(event.device().getRssi());
        this.isConnect = true;
        this.event = event;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public void setEvent(BleManager.DiscoveryListener.DiscoveryEvent event) {
        this.event = event;
    }

    public BleManager.DiscoveryListener.DiscoveryEvent getEvent() {
        return event;
    }

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
}
