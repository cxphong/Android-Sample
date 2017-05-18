package android.jurisicjosip.com.androidespresso.main.fragment;

import android.jurisicjosip.com.androidespresso.R;
import android.jurisicjosip.com.androidespresso.base.BaseFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jurisicJosip.
 */
public class MainFragment extends BaseFragment {

    public static BaseFragment newInstance(){
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    protected void prepareUi(@NonNull View view) {
        //ok nothing for now
    }
}
