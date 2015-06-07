package lab.android.rwth.evgenijandkate.plugscontrolclient.authorization;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lab.android.rwth.evgenijandkate.plugscontrolclient.R;

/**
 * Created by ekaterina on 07.06.2015.
 */
public class SignUpFragment extends Fragment {
    private Button signupButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.signup_fragment, container, false);
        this.signupButton = (Button) fragmentView.findViewById(R.id.signup_button);
        this.signupButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText emailEditView = (EditText) fragmentView.findViewById(R.id.email_signup_input);
                        EditText fullNameEditView = (EditText) fragmentView.findViewById(R.id.full_name_input);
                        EditText passwordEditView = (EditText) fragmentView.findViewById(R.id.password_signup_input);

                        performSignUp(emailEditView.getText().toString(),
                                fullNameEditView.getText().toString(), passwordEditView.getText().toString());
                    }
                }
        );
        return fragmentView;
    }

    private void performSignUp(String emailAddress, String fullName, String password) {
        //TODO: change to signing up
        Toast.makeText(getActivity(), "Welcome to Quiz, " + fullName + "!", Toast.LENGTH_SHORT).show();
    }
}