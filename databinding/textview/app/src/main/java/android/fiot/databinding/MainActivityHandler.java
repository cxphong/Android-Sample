package android.fiot.databinding;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

/**
 * Created by caoxuanphong on 12/5/17.
 */

public class MainActivityHandler {
    private static final String TAG = "MainActivityHandler";

    public void onClickUserName(View view, User user) {
        Log.d(TAG, "onClickUser " + view + ", " + user.getName());
    }

}
