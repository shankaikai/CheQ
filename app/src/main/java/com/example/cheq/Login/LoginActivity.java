package com.example.cheq.Login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cheq.FirebaseManager;
import com.example.cheq.MainActivity;
import com.example.cheq.R;

public class LoginActivity extends AppCompatActivity {

    // Temporary validation
    String validuser = "93821490";

    // Views
    EditText inputPhone;
    Button phoneContinueBtn;
    TextView loginMsg;
    TextView loginPrompt;


    // Firebase
    FirebaseManager firebaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hooks
        inputPhone = (EditText) findViewById(R.id.inputPhone);
        loginMsg = findViewById(R.id.titleMsg);
        loginPrompt = findViewById(R.id.loginPrompt);
        phoneContinueBtn = (Button) findViewById(R.id.phoneContinueBtn);
        phoneContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

        firebaseManager = new FirebaseManager();
    }

    // Check if new or existing user
    public void checkUser() {

        // Get user phone input
        String userPhone = inputPhone.getText().toString();

        // Check if the phone number input is valid
        if (isValidNumber(userPhone)) {

            Intent intent;

            // TODO: Check database for userName
            if (firebaseManager.userExist(userPhone)) {
                // Move to PasswordActivity
                moveToActivity("password",userPhone);
            } else {
                // Move to RegisterActivity
                moveToActivity("register",userPhone);
            }
        }
        else {
            Toast.makeText(LoginActivity.this, "Invalid phone number", Toast.LENGTH_SHORT);
        }
    }

    public void moveToActivity(String activity, String userPhone) {
        Intent intent;
        if (activity == "password") {
            intent = new Intent(LoginActivity.this, PasswordActivity.class);
            intent.putExtra("source", "existing");
        } else {
            intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        }
        intent.putExtra("userPhone", userPhone);

        // Add Transition
        Pair transition = new Pair<View, String>(phoneContinueBtn, "transitionContinueBtn");

        // Check if SDK version is high enough for animation
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, transition);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    // Check phone number is valid Singapore number
    public boolean isValidNumber(String s) {
        if (s.length() == 8 && (s.charAt(0) == '6' | s.charAt(0) == '8' | s.charAt(0) == '9')) {
            try {
                int num = Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Number!");
            }
        }
        return false;
    }

    private void checkSession() {

        // Instantiate SessionManagement and get current session
        SessionManager sessionManager = new SessionManager(LoginActivity.this);

        // If user is logged in then move to MainActivity
        if (sessionManager.isLoggedIn()){
            // TODO: Check for user type and redirect to Restaurant Activity if they are
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
    }
}
