package com.example.dinhtho.parsejson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetTokenManager getTokenManager=new GetTokenManager(MainActivity.this);
        getTokenManager.getToken("MNCTR70Y161200058", "21", new GetTokenManager.GetTokenManagerListener() {
            @Override
            public void onGetToken(int result) {
                Log.i(TAG, "onGetToken: "+result);
            }
        });
    }
}
