package com.viralandroid.androidtabsatbottom.utils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dinhtho on 20/04/2017.
 */

public class DeviceInfoDB extends RealmObject {
    private String make;
    private String model;
    private String year;
    private String pic;

    @PrimaryKey
    private String mac;

    public DeviceInfoDB(){};


    public DeviceInfoDB(String make, String model, String year, String pic,String mac) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.pic = pic;
        this.mac=mac;
    }

    public void setMac(String  mac) {
        this.mac = mac;
    }

    public String getMac() {
        return mac;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String gh) {
        this.make = gh;
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
