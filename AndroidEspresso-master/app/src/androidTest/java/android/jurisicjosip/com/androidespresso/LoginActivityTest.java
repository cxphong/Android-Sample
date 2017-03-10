package android.jurisicjosip.com.androidespresso;

import android.jurisicjosip.com.androidespresso.login.activity.LoginActivity;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by jurisicJosip.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        getActivity();
    }

    public void testLoginActivity() throws Exception {
        onView(withId(R.id.email)).perform(typeText("test@test.com"));
        onView(withId(R.id.password)).perform(typeText("test"));
        onView(withId(R.id.submit)).perform(click());
    }
}