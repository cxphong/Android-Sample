package android.fiot.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

/**
 * Created by caoxuanphong on 12/5/17.
 */

public class User extends BaseObservable {
    private static final String TAG = "User";
    public final ObservableField<String> name = new ObservableField<>();
}
