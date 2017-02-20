package com.example.dinhtho.outgoingcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public final static int REQUEST_CODE = 0x11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OutgoingCallReceiver outgoingCallReceiver=new OutgoingCallReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(outgoingCallReceiver, intentFilter);



        String[] permissions = {"android.permission.PROCESS_OUTGOING_CALLS"};
        ActivityCompat.requestPermissions(MainActivity.this,permissions, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else {
                Toast.makeText(getApplicationContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

class OutgoingCallReceiver extends BroadcastReceiver {
    private static final String TAG = "OutgoingCallReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: "+intent.getAction());
        Log.d(OutgoingCallReceiver.class.getSimpleName(), intent.toString());
        Toast.makeText(context, "Outgoing call catched!", Toast.LENGTH_LONG).show();
        //TODO: Handle outgoing call event here
    }
}