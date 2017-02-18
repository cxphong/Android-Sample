package com.example.dinhtho.demo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

    final Context context = this;
    private Button button;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog);



        Display display=getWindowManager().getDefaultDisplay();


        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int)(0.9*display.getWidth());
        lp.height = (int)(0.7*display.getHeight());
        Log.i("abc", "showDialogListView: "+0.9*display.getWidth());
        Log.i("abc", "showDialogListView: "+lp.width);
        lp.x=0;
        lp.y=10;
        dialog.getWindow().setAttributes(lp);

    }

}