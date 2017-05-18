package tw.chainsea.com.learnservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * tw.chainsea.com.learnservice
 * Created by sihuan on 2016/8/6.
 */
public class MyStartService extends Service {

    private static final String TAG = "MyStartService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: myStartService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: myStartService");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: myStartService");
    }
    public void play() {
        Log.i(TAG, "play: MyStartService");
        Toast.makeText(MyStartService.this, "播放", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
