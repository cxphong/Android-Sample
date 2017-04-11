package com.example.dinhtho.fragmentexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private Button btn_fr1,btn_fr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_fr1=(Button)findViewById(R.id.fr1);
        btn_fr2=(Button)findViewById(R.id.fr2);
        callFragment(new Fragment1());

        btn_fr1.setOnClickListener(this);
        btn_fr2.setOnClickListener(this);
    }
    public void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //Khi được goi, fragment truyền vào sẽ thay thế vào vị trí FrameLayout trong Activity chính
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fr1:
                callFragment(new Fragment1());
                break;
            case R.id.fr2:
                callFragment(new Fragment2());
                break;
        }

    }
}
