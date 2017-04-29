package com.viralandroid.androidtabsatbottom.utils;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by caoxuanphong on    8/16/16.
 */
public class GPSUtils {
    public static boolean isGPSEnabled (Context mContext){
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}


