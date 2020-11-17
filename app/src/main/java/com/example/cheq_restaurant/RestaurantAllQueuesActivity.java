package com.example.cheq_restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class RestaurantAllQueuesActivity extends AppCompatActivity {
    Button backBtn;
    RecyclerView allQueueRecyclerView;
    ArrayList<Seats> seats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_all_queues);
        backBtn = findViewById(R.id.restaurantAllQueuesBackBtn);
        allQueueRecyclerView = findViewById(R.id.allQueueRecyclerView);
        









        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}