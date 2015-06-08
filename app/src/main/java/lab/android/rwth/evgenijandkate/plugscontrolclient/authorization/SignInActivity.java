package lab.android.rwth.evgenijandkate.plugscontrolclient.authorization;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import lab.android.rwth.evgenijandkate.plugscontrolclient.R;

/**
 * Created by ekaterina on 07.06.2015.
 */
public class SignInActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "login_fragment";
    private LogInFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        loginFragment = (LogInFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (loginFragment == null) {
            // add the fragment
            loginFragment = new LogInFragment();
            fragmentManager.beginTransaction().add(R.id.login_fragment_container, loginFragment, FRAGMENT_TAG).commit();
        }
    }
}