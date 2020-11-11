package com.example.cheq.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheq.FirebaseManager;
import com.example.cheq.MainActivity;
import com.example.cheq.R;

public class PasswordActivity extends AppCompatActivity {

    // Views
    EditText inputPassword;
    TextView errorMsg;
    Button passwordContinueBtn;
    ImageView passwordBackBtn;

    // Temporary validation
    String validpassword = "password";

    FirebaseManager firebaseManager;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        firebaseManager = new FirebaseManager();
        sessionManager = new SessionManager(PasswordActivity.this);

        // Hooks
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        passwordContinueBtn = (Button) findViewById(R.id.passwordContinueBtn);
        passwordContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();
            }
        });
        passwordBackBtn = findViewById(R.id.passwordBackBtn);
        passwordBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLoginActivity();
            }
        });

    }

    // Check if new or existing user
    public void checkPassword() {
        // Get userPhone from LoginActivity
        String userPhone = getIntent().getStringExtra("userPhone");

        String userPassword = inputPassword.getText().toString();

        // Check firebase for password match
        if (firebaseManager.correctPassword(userPhone, userPassword)) {

            // Save Session of User
            sessionManager.saveSession(userPhone);

            // Move to MainActivity
            moveToMainActivity();
        }
        else {
            // Display error toast
            Toast.makeText(PasswordActivity.this, "Wrong password!", Toast.LENGTH_SHORT);
        }
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void moveToLoginActivity() {
        Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}