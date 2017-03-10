package android.jurisicjosip.com.androidespresso.base;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by josipjurisic on 5/24/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected void replaceFragment(@IdRes int layout, @NonNull BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(layout, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.commit();
    }
}