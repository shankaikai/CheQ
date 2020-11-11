package com.example.cheq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cheq.Login.LoginActivity;
import com.example.cheq.Login.SessionManager;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        }

        public void logout(View view) {
                //remove session and open login screen
                SessionManager sessionManager = new SessionManager(MainActivity.this);
                sessionManager.removeSession();
                moveToLogin();
        }

        private void moveToLogin() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

}