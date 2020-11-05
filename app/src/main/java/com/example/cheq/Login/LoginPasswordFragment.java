package com.example.cheq.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cheq.R;
import com.example.cheq.User;


public class LoginPasswordFragment extends Fragment {

    // Views
    EditText inputPassword;
    TextView errorMsg;
    Button passwordContinueBtn;

    // Temporary validation
    String validpassword = "password";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputPassword = (EditText) view.findViewById(R.id.inputPassword);
        errorMsg = (TextView) view.findViewById(R.id.errorMsg);
        passwordContinueBtn = (Button) view.findViewById(R.id.passwordContinueBtn);

        passwordContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();
            }
        });
    }

    // Check if new or existing user
    public void checkPassword() {

        String userPassword = inputPassword.getText().toString();

        // TODO: Check database for password
        if (userPassword.equals(validpassword)) {

            // TODO: Pass phone number to here and grab data from Firebase
            // Create a new User object (ID generation to be done by SQL)
             User user = new User(1, "93821490", "Shan Kai", "tiongshankai97@gmail.com", "customer");

            // Save Session of User
            SessionManager sessionManagement = new SessionManager(LoginActivity.this);
            sessionManagement.saveSession(user);
            ((LoginActivity) getActivity()).moveToMainActivity();
        }
        else {
            errorMsg.setText("Wrong password!");
        }
    }
}