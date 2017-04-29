package com.viralandroid.androidtabsatbottom;

import com.bluetooth.le.FioTManager;

/**
 * Created by dinhtho on 01/04/2017.
 */

public class DeviceInfo {
    String gh;
    String model;
    String year;
    String pic;
    FioTManager manager;




    public DeviceInfo(String gh, String model, String year, String pic,FioTManager manager) {
        this.gh = gh;
        this.model = model;
        this.year = year;
        this.pic = pic;
        this.manager=manager;
    }

    public void setManager(FioTManager manager) {
        this.manager = manager;
    }

    public FioTManager getManager() {
        return manager;
    }

    public String getGh() {
        return gh;
    }

    public void setGh(String gh) {
        this.gh = gh;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getPic() {
        return pic;
    }
}
