package com.example.dinhtho.testclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by dinhtho on 28/03/2017.
 */

public class BootUpReceiver extends BroadcastReceiver {
    private static final String TAG = "BootUpReceiver";
    private SharedPreferences prefs;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("", "onReceive: boot ok");
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        long time_off=prefs.getLong("time_off", 0);
        long time_on= System.currentTimeMillis();
        long duration= ((time_on-time_off)/1000);
        Log.i(TAG, "onReceive: "+time_on);
        Log.i(TAG, "onReceive: "+time_off);
        Log.i(TAG, "onReceive: "+duration);

        long time= prefs.getLong("time", 0);
        time+=duration;

        SharedPreferences.Editor editPrefs = prefs.edit();
        editPrefs.putLong("time", time);
        editPrefs.commit();

        /***** For start Service  ****/
        Intent myIntent = new Intent(context, ClockService.class);
        context.startService(myIntent);
    }

}

