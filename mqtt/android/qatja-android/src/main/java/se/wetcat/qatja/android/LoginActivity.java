package se.wetcat.qatja.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import se.wetcat.qatja.messages.MQTTPublish;

import static se.wetcat.qatja.MQTTConstants.PUBLISH;
import static se.wetcat.qatja.android.MQTTConnectionConstants.STATE_CHANGE;
import static se.wetcat.qatja.android.MQTTConnectionConstants.STATE_CONNECTED;
import static se.wetcat.qatja.android.MQTTConnectionConstants.STATE_CONNECTING;
import static se.wetcat.qatja.android.MQTTConnectionConstants.STATE_CONNECTION_FAILED;
import static se.wetcat.qatja.android.MQTTConnectionConstants.STATE_NONE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    private static final String TAG = "LoginActivity";
    QatjaService client;
    boolean isBound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent service;
        service = new Intent(this, QatjaService.class);
        bindService(service, connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            client = ((QatjaService.QatjaBinder) binder).getService();
            isBound = true;

            client.setHandler(mHandler);

            client.setHost("test.mosquitto.org");
            client.setPort(1883);
            client.setKeepAlive(3000);
            client.connect();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    Handler mHandler = new Handler(new MQTTCallback());

    private class MQTTCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_CHANGE:
                    switch (msg.arg1) {
                        case STATE_NONE:
                            Toast.makeText(LoginActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                            return true;
                        case STATE_CONNECTING:
                            Toast.makeText(LoginActivity.this, "Trying to connect...", Toast.LENGTH_SHORT).show();
                            return true;
                        case STATE_CONNECTED:
                            Toast.makeText(LoginActivity.this, "Yay! Connected!", Toast.LENGTH_SHORT).show();
                            client.subscribe("abc");
                            client.publish("abc", "xxx");
                            return true;
                        case STATE_CONNECTION_FAILED:
                            Toast.makeText(LoginActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return true;
                case PUBLISH:
                    MQTTPublish publish = (MQTTPublish) msg.obj;
                    String topic = publish.getTopicName();
                    byte[] payload = publish.getPayload();

                    Log.i(TAG, "handleMessage: " + topic + ", " + new String(payload));
                    return true;
                default:
                    return false;
            }
        }
    };
}

