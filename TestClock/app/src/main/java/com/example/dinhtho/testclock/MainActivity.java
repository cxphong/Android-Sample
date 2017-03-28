package com.example.dinhtho.testclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private int progress;
    private int i=0;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Start();




//        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        progress=prefs.getInt("time", 0);
//        Log.i(TAG, "onCreate: "+progress);
//        if(i!=progress){
//            i=progress;
//        }
//
//       new AsyncTaskRunner().execute();



    }

    private void Start(){
        Intent intent=new Intent(this,ClockService.class);
        this.startService(intent);
    }
    private void Stop(){
        Intent intent=new Intent(this,ClockService.class);
        this.stopService(intent);
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        SharedPreferences.Editor editPrefs = prefs.edit();
//        Log.i(TAG, "onPause: "+i);
//        editPrefs.putInt("time", i);
//        editPrefs.commit();
//    }
//
//    private class AsyncTaskRunner extends AsyncTask<Void, Integer, String> {
//
//        int a=i;
//        private String resp;
//
//
//        @Override
//        protected String doInBackground(Void... params) {
//            while (true) {
//                a += 1;
//                publishProgress(a); // Calls onProgressUpdate()
//                try {
//                    //int time = Integer.parseInt(params[0]) * 1000;
//
//                    Thread.sleep(1000);
//                    //resp = "Slept for " + params[0] + " seconds";
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    //resp = e.getMessage();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    resp = e.getMessage();
//                }
//            }
//
//        }
//
//
//
//
//        @Override
//        protected void onProgressUpdate(Integer... text) {
//            Log.i(TAG, "onProgressUpdate: "+text[0]);
//            super.onProgressUpdate(text);
//
//            i=text[0];
////            Toast.makeText(ViewerActivity.this,text[0],Toast.LENGTH_SHORT);
////            Log.i(TAG, "onProgressUpdate: ");
//
//
//        }
//    }
}
