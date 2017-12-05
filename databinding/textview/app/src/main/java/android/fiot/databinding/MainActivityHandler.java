package android.fiot.databinding;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by caoxuanphong on 12/5/17.
 */

public class MainActivityHandler {
    private static final String TAG = "MainActivityHandler";

    public void onClickUserName(View view, User user) {
        Log.d(TAG, "onClickUser " + view + ", " + user.name.get());

        // Update model & view
        user.name.set(getSaltString());
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
