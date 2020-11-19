package com.example.cheq.Restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cheq.R;

import java.util.ArrayList;

public class RestaurantAllQueuesActivity extends AppCompatActivity {
    Button backBtn;
    RecyclerView allQueueRecyclerView;
    ArrayList<Seat> seats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_all_queues);
        backBtn = findViewById(R.id.restaurantAllQueuesBackBtn);
        allQueueRecyclerView = findViewById(R.id.allQueueRecyclerView);

        ArrayList<Seat> seats = new ArrayList<Seat>();
        seats.add(new Seat("A444", 4));
        seats.add(new Seat("A422", 4));
        seats.add(new Seat("A411", 2));
        SeatsAdapter adapter = new SeatsAdapter(seats);
        allQueueRecyclerView.setAdapter(adapter);
        allQueueRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}