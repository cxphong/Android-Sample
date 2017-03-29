package android.jurisicjosip.com.androidespresso.login.fragment;

import android.jurisicjosip.com.androidespresso.R;
import android.jurisicjosip.com.androidespresso.base.BaseFragment;
import android.jurisicjosip.com.androidespresso.main.activity.MainActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by jurisicJosip.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    public static BaseFragment newInstance() {
        return new LoginFragment();
    }

    //Ui widgets
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSubmitBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareUi(view);
    }

    @Override
    protected void prepareUi(@NonNull View view) {
        mEmailEditText = (EditText) view.findViewById(R.id.email);
        mPasswordEditText = (EditText) view.findViewById(R.id.password);
        mSubmitBtn = (Button) view.findViewById(R.id.submit);
        mSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mSubmitBtn) {
            handleSubmitBtnClick();
        }
    }

    private void handleSubmitBtnClick() {
        boolean errorFound = false;

        String email = mEmailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError(getString(R.string.field_required));
            errorFound = true;
        }

        String password = mPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError(getString(R.string.field_required));
            errorFound = true;
        }

        if (!errorFound) {
            startActivity(MainActivity.getLaunchIntent(getActivity()));
            getActivity().finish();
        }
    }
}