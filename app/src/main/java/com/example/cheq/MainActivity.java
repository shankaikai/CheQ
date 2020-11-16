package com.example.cheq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cheq.Login.LoginActivity;
import com.example.cheq.Managers.SessionManager;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        }

        public void logout(View view) {
                // Remove session and open login screen
                SessionManager sessionManager = SessionManager.getSessionManager(MainActivity.this);
                sessionManager.removeSession();
                moveToLoginActivity();
        }

        private void moveToLoginActivity() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

}