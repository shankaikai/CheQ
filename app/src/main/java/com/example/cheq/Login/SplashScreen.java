package com.example.cheq.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cheq.MainActivity;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.Restaurant.RestaurantActivity;

public class SplashScreen extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSession();
    }

    /**
     * This method checks if the user is already logged in by calling the session manager class
     * @return a boolean value
     */
    private void checkSession() {
        // Instantiate SessionManagement and get current session
        SessionManager sessionManager = SessionManager.getSessionManager(SplashScreen.this);

        // If user is logged in then move to MainActivity
        if (sessionManager.isLoggedIn()){
            Intent intent;
            if (sessionManager.getUserType().equals("Customer")) {
                intent = new Intent(SplashScreen.this, MainActivity.class);
            } else {
                intent = new Intent(SplashScreen.this, RestaurantActivity.class);
            }
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
