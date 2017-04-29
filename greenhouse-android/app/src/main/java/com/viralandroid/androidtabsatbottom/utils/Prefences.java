package com.viralandroid.androidtabsatbottom.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by caoxuanphong on 4/9/17.
 */

public class Prefences {
    private static final String IMPERIAL = "IMPERIAL";
    private static final String NOTIFY = "NOTIFY";
    private static final String BATTERY_CCA = "BATTERY_CCA";
    private static final String BATTERY_STATE_OF_HEALTH = "BATTERY_STATE_OF_HEALTH";
    private static final String BATTERY_STATE_OF_HEALTH_TIME = "BATTERY_STATE_OF_HEALTH_TIME";

    public static void saveImperial(Context context, boolean val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(IMPERIAL, val);
        editor.apply();
    }

    public static boolean getImperial(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(IMPERIAL, true);
    }

    public static void saveNotify(Context context, boolean val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(NOTIFY, val);
        editor.apply();
    }

    public static boolean getNotify(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(NOTIFY, false);
    }

    public static void saveBatteryCCA(Context context, int val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(BATTERY_CCA, val);
        editor.apply();
    }

    public static int getBatteryCCA(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(BATTERY_CCA, 550);
    }

    public static void saveBatteryStateOfHealth(Context context, String val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(BATTERY_STATE_OF_HEALTH, val);
        editor.apply();
    }

    public static String getBatteryStateOfHealth(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(BATTERY_STATE_OF_HEALTH, null);
    }

    public static void saveBatteryStateOfHealthTime(Context context, String val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(BATTERY_STATE_OF_HEALTH_TIME, val);
        editor.apply();
    }

    public static String getBatteryStateOfHealthTime(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(BATTERY_STATE_OF_HEALTH_TIME, null);
    }

}
