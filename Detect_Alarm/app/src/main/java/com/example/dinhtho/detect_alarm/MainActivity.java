package com.example.dinhtho.detect_alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String ALARM_ALERT_ACTION = "com.android.deskclock.ALARM_ALERT";
    public static final String ALARM_SNOOZE_ACTION = "com.android.deskclock.ALARM_SNOOZE";
    public static final String ALARM_DISMISS_ACTION = "com.android.deskclock.ALARM_DISMISS";
    public static final String ALARM_DONE_ACTION = "com.android.deskclock.ALARM_DONE";

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ALARM_ALERT_ACTION))
            {
                // for play/pause mediaplayer
                Log.i(TAG, "onReceive: alert");
            }
            if (action.equals(ALARM_DISMISS_ACTION)) {
                Log.i(TAG, "onReceive: dismiss");
            }
            if( action.equals(ALARM_SNOOZE_ACTION)){
                Log.i(TAG, "onReceive: snooze");
            }
            if( action.equals(ALARM_DONE_ACTION)){
                Log.i(TAG, "onReceive: done");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        IntentFilter filter = new IntentFilter(ALARM_ALERT_ACTION);
        filter.addAction(ALARM_DISMISS_ACTION);
        filter.addAction(ALARM_SNOOZE_ACTION);
        filter.addAction(ALARM_DONE_ACTION);
        registerReceiver(mReceiver, filter);
    }
}
