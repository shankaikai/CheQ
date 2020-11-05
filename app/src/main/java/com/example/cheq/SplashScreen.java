package com.example.cheq;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cheq.Login.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Once LoginActivity is loaded, switch to it
        startActivity(new Intent(this, LoginActivity.class));
    }

}
