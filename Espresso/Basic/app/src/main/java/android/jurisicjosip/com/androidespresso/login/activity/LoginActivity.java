package android.jurisicjosip.com.androidespresso.login.activity;

import android.jurisicjosip.com.androidespresso.R;
import android.jurisicjosip.com.androidespresso.base.BaseActivity;
import android.jurisicjosip.com.androidespresso.login.fragment.LoginFragment;
import android.os.Bundle;

/**
 * Created by jurisicJosip.
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        if(savedInstanceState == null){
            replaceFragment(R.id.fragment_container, LoginFragment.newInstance(), false);
        }
    }
}