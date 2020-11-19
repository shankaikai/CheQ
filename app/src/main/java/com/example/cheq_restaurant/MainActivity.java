package com.example.cheq_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    Button restBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restBtn = findViewById(R.id.toResBtn);
        restBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRestaurantActivity();
            }
        });

    }
    private void moveToRestaurantActivity() {
        Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
        startActivity(intent);
    }
}