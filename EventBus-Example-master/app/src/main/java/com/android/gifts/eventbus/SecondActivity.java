package com.android.gifts.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SecondActivity extends AppCompatActivity {

    TextView userStatus;

    private EventBus bus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        userStatus = (TextView) findViewById(R.id.user_status);
    }

    /**
     * Receiving Login event when it happens,
     * Using sticky = true telling the activity please go and get the last MessageEvent has been posted
     * */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLoginEvent(MessageEvent event){
        userStatus.setText("User Status : Logged in, message: " + event.message);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this); // registering the bus
    }

    @Override
    public void onStop() {
        bus.unregister(this); // un-registering the bus
        super.onStop();
    }
}