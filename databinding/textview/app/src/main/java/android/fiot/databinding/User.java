package android.fiot.databinding;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

/**
 * Created by caoxuanphong on 12/5/17.
 */

public class User {
    private static final String TAG = "User";
    private String name = "Phong Cao";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
