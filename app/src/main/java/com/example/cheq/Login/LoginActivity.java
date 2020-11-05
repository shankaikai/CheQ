package com.example.cheq.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cheq.MainActivity;
import com.example.cheq.R;
import com.example.cheq.Restaurant.RestaurantActivity;

public class LoginActivity extends AppCompatActivity {

    // Temporary Btn for Restaurant Activity
    Button restBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Temporary
        restBtn = findViewById(R.id.toResBtn);
        restBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRestaurantActivity();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.loginFragmentFrame, new LoginPhoneFragment()).commit();

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

    // Replace loginFragment with the next fragment
    public void changeFragment(String type) {
        Fragment fragment;

        if (type == "existing") {
            fragment = new LoginPasswordFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.loginFragmentFrame, fragment).commit();
        }
        if (type == "new") {
            fragment = new RegisterFragment();
            FragmentManager fm = getSupportFragmentManager();
            getSupportFragmentManager().beginTransaction().replace(R.id.loginFragmentFrame, fragment).commit();
        }
    }

    void moveToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void moveToRestaurantActivity() {
        Intent intent = new Intent(LoginActivity.this, RestaurantActivity.class);
        startActivity(intent);
    }

}
