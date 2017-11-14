package android.jurisicjosip.com.androidespresso.base;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by josipjurisic on 5/24/15.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract void prepareUi(@NonNull View view);
}