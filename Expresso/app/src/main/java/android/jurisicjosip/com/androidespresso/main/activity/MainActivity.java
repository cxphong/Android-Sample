package android.jurisicjosip.com.androidespresso.main.activity;

import android.content.Context;
import android.content.Intent;
import android.jurisicjosip.com.androidespresso.R;
import android.jurisicjosip.com.androidespresso.base.BaseActivity;
import android.jurisicjosip.com.androidespresso.main.fragment.MainFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by jurisicJosip.
 */
public class MainActivity extends BaseActivity {

    public static Intent getLaunchIntent(@NonNull Context from){
        return new Intent(from, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        if(savedInstanceState == null){
            replaceFragment(R.id.fragment_container, MainFragment.newInstance(), false);
        }
    }
}