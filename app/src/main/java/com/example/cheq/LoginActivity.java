package com.example.cheq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp.R;

public class LoginActivity extends AppCompatActivity {

    // Temporary validation
    String validation = "93821490";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    private void checkSession() {
        //check if user is logged in
        //if user is logged in go to MainActivity
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userID = sessionManagement.getSession();

        // check if user is logged in then move to MainActivity else do nothing
        if (userID != -1){
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

    // Login function to save session of user
    public void login(View view) {

        EditText inputPhone = (EditText) findViewById(R.id.inputPhone);
        String userPhone = inputPhone.getText().toString();

        // Error Message
        TextView errorMsg = (TextView) findViewById(R.id.errorMsg);

        if (isValidNumber(userPhone)) {

            if (userPhone.equals(validation)) {

                // Create a new User object (ID generation to be done)
                User user = new User(1, userPhone);

                // Save Session of User
                SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                sessionManagement.saveSession(user);
                moveToMainActivity();

            } else {

                // Go to Registration Activity
                errorMsg.setText("Trigger Registration Activity");
            }
        }
        else {
            errorMsg.setText("Please enter a valid phone number");
        }

    }

    private void moveToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("userID", userPhone);
        startActivity(intent);
    }

}
