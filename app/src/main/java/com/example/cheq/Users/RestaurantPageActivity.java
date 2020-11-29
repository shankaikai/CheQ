package com.example.cheq.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cheq.R;

public class RestaurantPageActivity extends AppCompatActivity {

    String restaurantID;
    TextView restaurantPageIDTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

        restaurantID = getIntent().getStringExtra("restaurantID");
        restaurantPageIDTextView = findViewById(R.id.restaurantPageIDTextView);
        restaurantPageIDTextView.setText(restaurantID);

    }
}