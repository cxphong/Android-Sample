package com.example.dinhtho.app_language;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView txv,txv1,txv2,txv3,txv4,txv5,txv6,txv7,txv8,txv9,txv10,txv11,txv12,txv13,txv14,txv15,txv16;
    String TAG="lang";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
//        String lang = settings.getString(getString(R.string.login), "");
//
        String mess = getResources().getString(R.string.buy_token);
        txv=(TextView)findViewById(R.id.textView);
        txv.setText(mess);

        txv1=(TextView)findViewById(R.id.textView4);
        txv2=(TextView)findViewById(R.id.textView5);
        txv3=(TextView)findViewById(R.id.textView6);
        txv4=(TextView)findViewById(R.id.textView7);
        txv5=(TextView)findViewById(R.id.textView8);
        txv6=(TextView)findViewById(R.id.textView9);
        txv7=(TextView)findViewById(R.id.textView10);
        txv8=(TextView)findViewById(R.id.textView11);
        txv9=(TextView)findViewById(R.id.textView12);
        txv10=(TextView)findViewById(R.id.textView13);
        txv11=(TextView)findViewById(R.id.textView14);
        txv12=(TextView)findViewById(R.id.textView15);
        txv13=(TextView)findViewById(R.id.textView16);
        txv14=(TextView)findViewById(R.id.textView17);
        txv15=(TextView)findViewById(R.id.textView18);
        txv16=(TextView)findViewById(R.id.textView19);

        txv1.setText(getResources().getString(R.string.buy));
        txv2.setText(getResources().getString(R.string.customer_id));
        txv3.setText(getResources().getString(R.string.device_id));
        txv4.setText(getResources().getString(R.string.are_you_sure_you_want_to_buy_token));
        txv5.setText(getResources().getString(R.string.buying_token));
        txv6.setText(getResources().getString(R.string.history));
        txv7.setText(getResources().getString(R.string.no));
        txv8.setText(getResources().getString(R.string.yes));
        txv9.setText(getResources().getString(R.string.version));
        txv10.setText(getResources().getString(R.string.type));
        txv11.setText(getResources().getString(R.string.token));
        txv12.setText(getResources().getString(R.string.thirty_days));
        txv13.setText(getResources().getString(R.string.serial_number));
        txv14.setText(getResources().getString(R.string.promotion_offer));
        txv15.setText(getResources().getString(R.string.seven_days));
        txv16.setText(getResources().getString(R.string.time));

    }



}