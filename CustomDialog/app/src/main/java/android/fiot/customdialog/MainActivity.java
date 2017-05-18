package android.fiot.customdialog;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_main);
        TextView tv = (TextView) dialog.findViewById(R.id.tv);
        tv.setText("Hello a Phong");
        dialog.show();
    }
}
