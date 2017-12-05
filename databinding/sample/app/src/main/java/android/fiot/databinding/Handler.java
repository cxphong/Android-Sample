package android.fiot.databinding;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by caoxuanphong on 12/5/17.
 */

public class Handler {
    private static final String TAG = "Handler";

    public void onClickUserName(View view, User user) {
        Log.d(TAG, "onClickUser " + view + ", " + user.name.get());

        // Update model & view
        user.name.set(getSaltString());
    }

    public void onClickButton(View view, User user) {
        Log.d(TAG, "onClickButton: " + user.name.get());

        user.name.set(getSaltString());
    }

    public TextWatcher onTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.d(TAG, "onTextChanged: " + charSequence);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private String getSaltString() {
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
