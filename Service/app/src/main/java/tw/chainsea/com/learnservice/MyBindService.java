package tw.chainsea.com.learnservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * tw.chainsea.com.learnservice
 * Created by sihuan on 2016/8/6.
 */
public class MyBindService extends Service {

    private static final String TAG = "MyBindService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: MyBindService");
    }

    public void play() {
        Log.i(TAG, "play: MyBindService");
        Toast.makeText(MyBindService.this, "播放", Toast.LENGTH_SHORT).show();
    }

    public void pause() {
        Log.i(TAG, "pause: MyBindService");
        Toast.makeText(MyBindService.this, "暂停", Toast.LENGTH_SHORT).show();
    }

    public void previous() {
        Log.i(TAG, "previous: MyBindService");
        Toast.makeText(MyBindService.this, "上一首", Toast.LENGTH_SHORT).show();
    }

    public void next() {
        Log.i(TAG, "next: MyBindService");
        Toast.makeText(MyBindService.this, "下一首", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: MyBindService");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: MyBindService");
        return super.onUnbind(intent);
    }

    private final Mybinder mBinder = new Mybinder();

    public class Mybinder extends Binder {

        public MyBindService getService() {
            return MyBindService.this;
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: MyBindService");
        return mBinder;
    }
}
