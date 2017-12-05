package android.fiot.databinding;

import android.databinding.DataBindingUtil;
import android.fiot.databinding.databinding.ActivityMainBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Handler myHandler = new Handler();
        User user = new User();
        binding.setUser(user);
        binding.setHandlers(myHandler);
    }
}
