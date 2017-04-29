package com.viralandroid.androidtabsatbottom.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by caoxuanphong on    4/29/16.
 */
public class DateTimeUtils {
    private static final String TAG = "DateTimeUtils";

    public static long currentTimeToEpoch() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        int offsetFromUtc = tz.getOffset(now.getTime()) / 1000;

        Log.i(TAG, "currentTimeToEpoch: " + offsetFromUtc +  ", " + System.currentTimeMillis());
        return (System.currentTimeMillis() / 1000 + offsetFromUtc);
    }

    public static int getTimezone() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        int offsetFromUtc = tz.getOffset(now.getTime());

        return offsetFromUtc/3600000;
    }

    public static String epochToStringNoTimeZone (long epoch, String format) {
        Date date = new Date((epoch));
        SimpleDateFormat s = new SimpleDateFormat(format);
        return s.format(date);
    }

    public static String epochToString(long epoch, String format) {
//        TimeZone tz = TimeZone.getDefault();
//        Date now = new Date();
//        int offsetFromUtc = tz.getOffset(now.getTime()) / 1000;

        //Date date = new Date((epoch - offsetFromUtc) * 1000);
        Date date = new Date((epoch) * 1000);
        SimpleDateFormat s = new SimpleDateFormat(format);
        return s.format(date);
    }

    public static Date epochToDate(long epoch) {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        int offsetFromUtc = tz.getOffset(now.getTime()) / 1000;

        Date date = new Date((epoch - offsetFromUtc) * 1000);
        return date;
    }

    public static boolean isSameDate(long epoch1, long epoch2) {
        Date date1 = epochToDate(epoch1);
        Date date2 = epochToDate(epoch2);

        if (date1.getDate() == date2.getDate() &&
                date1.getMonth() == date2.getMonth() &&
                date1.getYear() == date2.getYear()) {
            return true;
        }

        return false;
    }

    public static String getDateTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

}
