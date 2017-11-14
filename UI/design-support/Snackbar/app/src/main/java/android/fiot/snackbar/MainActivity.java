package android.fiot.snackbar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Snackbar snackbar = Snackbar.make(findViewById(R.id.content), "Message", 3000);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ");
            }
        });
        snackbar.addCallback(callback);
        snackbar.setActionTextColor(Color.parseColor("#00ff00"));
        snackbar.show();
    }

    Snackbar.Callback callback = new Snackbar.Callback() {

        @Override
        public void onShown(Snackbar sb) {
            super.onShown(sb);
            Log.i(TAG, "onShown: ");
        }

        @Override
        public void onDismissed(Snackbar transientBottomBar, int event) {
            super.onDismissed(transientBottomBar, event);
            Log.i(TAG, "onDismissed: ");
        }
    };

}
