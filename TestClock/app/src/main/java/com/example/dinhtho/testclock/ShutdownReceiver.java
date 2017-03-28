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

public class ShutdownReceiver extends BroadcastReceiver {
    private SharedPreferences prefs;
    private static final String TAG = "ShutdownReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("", "onReceive: off ok");
        Intent myIntent = new Intent(context, ClockService.class);
        context.stopService(myIntent);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editPrefs = prefs.edit();
        long time_off= System.currentTimeMillis();
        Log.i(TAG, "onReceive: "+time_off);
        editPrefs.putLong("time_off", time_off);
        editPrefs.commit();


    }

}
