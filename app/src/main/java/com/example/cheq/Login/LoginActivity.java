package com.example.cheq.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cheq.MainActivity;
import com.example.cheq.R;

public class LoginActivity extends AppCompatActivity {

    // Temporary validation
    String validuser = "93821490";
    String validpassword = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Create the onClickListener for the Continue Button
        Button phoneContinueBtn = (Button) findViewById(R.id.phoneContinueBtn);
        phoneContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser(v);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession(); // Check if user is logged in
    }

    private void checkSession() {

        // Instantiate SessionManagement and get current session
        SessionManager sessionManager = new SessionManager(LoginActivity.this);
        int userID = sessionManager.getSession();

        // If user is logged in then move to MainActivity
        if (userID != -1){

            // TODO: Check for user type and redirect to Restaurant Activity if they are
            moveToMainActivity();
        }
    }

    public boolean isValidNumber(String s) {
        if (s.length() == 8) {
            try {
                int num = Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Number!");
            }
        }
        return false;
    }

    // Check if new or existing user
    public void checkUser(View view) {

        EditText inputPhone = (EditText) findViewById(R.id.inputPhone);
        String userPhone = inputPhone.getText().toString();

        // Error Message
        TextView errorMsg = (TextView) findViewById(R.id.errorMsg);

        // Check if the phone number input is valid
        if (isValidNumber(userPhone)) {

            // TODO: Check database for userName
            if (userPhone.equals(validuser)) {

                // Call PasswordFragment
                changeFragment("existing");

                // Create a new User object (ID generation to be done by SQL)
                // User user = new User(1, userPhone, );

                // Save Session of User
//                SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
//                sessionManagement.saveSession(user);
//                moveToMainActivity();

            } else {

                // Call RegistrationFragment
                changeFragment("new");
            }
        }
        else {
            errorMsg.setText("Please enter a valid phone number");
        }

    }

    // Check if new or existing user
    public void checkPassword(View view) {

        EditText inputPassword = (EditText) findViewById(R.id.inputPassword);
        String userPassword = inputPassword.getText().toString();

        // Error Message
        TextView errorMsg = (TextView) findViewById(R.id.errorMsg);

        // TODO: Check database for password
        if (userPassword.equals(validuser)) {
            moveToMainActivity();
        }
        else {
            errorMsg.setText("Wrong password!");
        }

    }

    // Replace loginFragment with the next fragment
    public void changeFragment(String type) {
        Fragment fragment;

        if (type == "existing") {
            fragment = new LoginPasswordFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.loginFragment, fragment);
            ft.commit();
        }
        if (type == "new") {
            fragment = new RegisterFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.loginFragment, fragment);
            ft.commit();
        }
    }



    private void moveToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
