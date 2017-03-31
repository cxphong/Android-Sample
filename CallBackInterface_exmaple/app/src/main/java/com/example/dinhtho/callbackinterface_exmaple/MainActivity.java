package com.example.dinhtho.callbackinterface_exmaple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test(100, new MyCallback() {
            @Override
            public void callbackCall(String result) {
                Log.i(TAG, "callbackCall: "+result);
            }
        });

        Log.i(TAG, "onCreate: "+s);



    }

    private void test(final int max, final MyCallback myCallback){
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (i<=max){
                    i++;
                    Log.i(TAG, "run: "+i);
                }
                s="done";
                myCallback.callbackCall("done");

            }
        });
        thread.start();
    }



    interface MyCallback {
        void callbackCall(String result);
    }
}


