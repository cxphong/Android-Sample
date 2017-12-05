package android.fiot.databinding;

import android.databinding.DataBindingUtil;
import android.fiot.databinding.databinding.ActivityMainBinding;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user = new User();
        MainActivityHandler myHandler = new MainActivityHandler();

        binding.setUser(user);
        binding.setHandlers(myHandler);
    }
}
