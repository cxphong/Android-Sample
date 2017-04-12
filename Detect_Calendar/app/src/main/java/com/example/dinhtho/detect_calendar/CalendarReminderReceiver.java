package com.example.dinhtho.detect_calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;

/**
 * Created by dinhtho on 12/04/2017.
 */

public class CalendarReminderReceiver extends BroadcastReceiver {
    private static final String TAG = "CalendarReminderReceive";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(CalendarContract.ACTION_EVENT_REMINDER)) {
            Log.i(TAG, "onReceive: ");


        }

    }

}