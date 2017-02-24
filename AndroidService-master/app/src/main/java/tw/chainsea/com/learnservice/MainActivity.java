package tw.chainsea.com.learnservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Intent intent;
    private Intent intent2;
    private MyBindService mService;
    private MyStartService myStartService=new MyStartService();
    private boolean mIsBind;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected: "+componentName);
            mService = ((MyBindService.Mybinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected: "+componentName);
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                intent = new Intent(MainActivity.this, MyStartService.class);
                startService(intent);
               // myStartService.play();
                break;
            case R.id.stop:
                stopService(intent);
                break;
            case R.id.bind:
                intent2 = new Intent(MainActivity.this, MyBindService.class);
                bindService(intent2, mConnection, BIND_AUTO_CREATE);
                mIsBind = true;
                //mService.play();
                break;
            case R.id.play:
                mService.play();
                break;
            case R.id.pause:
                mService.pause();
                break;
            case R.id.previous:
                mService.previous();
                break;
            case R.id.next:
                mService.next();
                break;
            case R.id.unbind:
                if (mIsBind) {
                    unbindService(mConnection);
                    mIsBind = false;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (intent != null) {
            stopService(intent);
        }
        if (mIsBind) {
            unbindService(mConnection);
            mIsBind = false;
        }
    }
}
