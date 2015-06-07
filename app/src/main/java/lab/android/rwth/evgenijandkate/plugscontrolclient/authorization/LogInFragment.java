package lab.android.rwth.evgenijandkate.plugscontrolclient.authorization;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import lab.android.rwth.evgenijandkate.plugscontrolclient.PlugsControlActivity;
import lab.android.rwth.evgenijandkate.plugscontrolclient.R;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.User;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.AddPlugRequest;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.CheckUserTask;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.OnResponseListener;

/**
 * Created by ekaterina on 07.06.2015.
 */
public class LogInFragment extends Fragment {
    public static final String USER_TAG = "user";
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String login = "loginKey";
    public static final String password = "passwordKey";
    public static final String ip = "ipKey";
    public static final String port = "portKey";
    private static SharedPreferences sharedpreferences;

    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.login_fragment, container, false);
        this.loginButton = (Button) fragmentView.findViewById(R.id.login_button);
        this.loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        attemptToLogin(fragmentView.findViewById(R.id.email_input), fragmentView.findViewById(R.id.password_input),
                                fragmentView.findViewById(R.id.ip_address), fragmentView.findViewById(R.id.port_number));
                    }
                }
        );
        return fragmentView;
    }

    private void attemptToLogin(View emailInput, View passwordInput, View ipInput, View portInput) {
        if (emailInput instanceof EditText && passwordInput instanceof EditText &&
                ipInput instanceof EditText && portInput instanceof EditText) {
            final String emailAddress = ((EditText) emailInput).getText().toString();
            final String passwordValue = ((EditText) passwordInput).getText().toString();
            final String ipValue = ((EditText) ipInput).getText().toString();
            final String portValue = ((EditText) portInput).getText().toString();

            CheckUserTask checkUserTask = new CheckUserTask();
            checkUserTask.setOnResponseListener(new OnResponseListener<Boolean>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onResponse(Boolean responseOK) {
                    if (responseOK) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(login, emailAddress);
                        editor.putString(password, passwordValue);
                        editor.putString(ip, ipValue);
                        editor.putString(port, portValue);
                        editor.commit();

                        Intent plugsControlActivityIntent = new Intent(getActivity(), PlugsControlActivity.class);
                        plugsControlActivityIntent.putExtra(USER_TAG, new User(emailAddress, passwordValue));
                        startActivity(plugsControlActivityIntent);
                    }
                }

                @Override
                public void onError(String errorMessage) {

                }
            });
            checkUserTask.send(new User(emailAddress, passwordValue, ipValue, portValue));
        }
    }

    public static SharedPreferences getSharedPreferenceFile() {
        return sharedpreferences;
    }

    public static String getB64Auth() {
        if (sharedpreferences != null) {
            String pass = sharedpreferences.getString(password, "");
            String userLogin = sharedpreferences.getString(login, "");
            String source = userLogin + ":" + pass;
            String ret = "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
            return ret;
        }
        return null;
    }

    public static String getB64Auth(String login, String pass) {
        String source = login + ":" + pass;
        String ret = "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
        return ret;
    }

    public static User getConnectedUser() {
        if (sharedpreferences != null) {
            String pass = sharedpreferences.getString(password, "");
            String userLogin = sharedpreferences.getString(login, "");
            String ipValue = sharedpreferences.getString(ip, "");
            String hostValue = sharedpreferences.getString(port, "");

            return new User(userLogin, pass, ipValue, hostValue);
        }
        return null;
    }
}