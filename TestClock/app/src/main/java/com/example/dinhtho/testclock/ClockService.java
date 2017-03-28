package com.example.dinhtho.testclock;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class ClockService extends Service {
    private long time=0;
    private SharedPreferences prefs;
    private long progress;
    private AsyncTask asyncTaskRunner ;
    private boolean isCancelled =false;
    private static final String TAG = "ClockService";

    public ClockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        progress=prefs.getLong("time", 0);
        Log.i(TAG, "onCreate: "+progress);
        if(time!=progress){
            time=progress;
        }

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId){



        asyncTaskRunner= new  AsyncTaskRunner().execute();

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        isCancelled=true;
        super.onDestroy();
    }

        private class AsyncTaskRunner extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... params) {
            while (true) {
                if(isCancelled){
                    break;
                }

                try {

                    Thread.sleep(1000);
                    //resp = "Slept for " + params[0] + " seconds";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //resp = e.getMessage();
                }
                time++;
                Log.i(TAG, "doInBackground: "+time);
                SharedPreferences.Editor editPrefs = prefs.edit();
                editPrefs.putLong("time", time);
                editPrefs.commit();

            }
            return null;

        }

    }

}
