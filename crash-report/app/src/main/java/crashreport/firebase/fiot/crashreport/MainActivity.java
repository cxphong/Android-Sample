package crashreport.firebase.fiot.crashreport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.crash.FirebaseCrash;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseCrash.log("hello this log");
        FirebaseCrash.logcat(0, "abc", "123");
        FirebaseCrash.report(new Throwable(""));
    }
}
