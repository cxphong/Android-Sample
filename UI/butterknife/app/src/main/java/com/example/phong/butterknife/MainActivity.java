package com.example.phong.butterknife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// Tutorial https://kipalog.com/posts/Butter-Knife--thu-vien-ho-tro-bind-View-va-callback-nhanh-chong
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.tv_hello)
    TextView tvHello;

    @BindView(R.id.tv_goodebye)
    TextView tvGoodBye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tvHello.setText("ACB");
        tvGoodBye.setText("DEF");
    }

    @OnClick({R.id.tv_hello, R.id.tv_goodebye})
    public void onClickTextView(View view) {
        Log.d(TAG, "onClickTextView: " + view);
    }
}
