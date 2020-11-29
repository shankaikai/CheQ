package com.example.cheq.Restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestaurantAllQueuesActivity extends AppCompatActivity {
    Button backBtn;
    RecyclerView allQueueRecyclerView;
    ArrayList<Seat> seats;
    // Firebase
    FirebaseManager firebaseManager;
    String restaurantId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_all_queues);
        backBtn = findViewById(R.id.restaurantAllQueuesBackBtn);

        seats = new ArrayList<>();

        firebaseManager = new FirebaseManager();
        restaurantId = "88888888";

        DatabaseReference rootRef = firebaseManager.rootRef;
        DatabaseReference restaurantQueueNoRef = rootRef.child("Queues").child(restaurantId);
        restaurantQueueNoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //The queue no. is always "AXX" where the first "X" is the no. of pax, the second "X" is the index of the queue in that category of no. of pax.
                for (DataSnapshot noOfPax : dataSnapshot.getChildren()) {
                    for (DataSnapshot seatNo2: noOfPax.getChildren()){
                        Log.i("test222", String.valueOf(seatNo2));
                        seats.add(new Seat("A" + noOfPax.getKey() + seatNo2.getKey(), Integer.parseInt(noOfPax.getKey())));
                    }
                }
                //Setting the SeatsAdapter up to display the queues in a Recyclerview.
                allQueueRecyclerView = findViewById(R.id.allQueueRecyclerView);
                SeatsAdapter adapter = new SeatsAdapter(seats);
                allQueueRecyclerView.setAdapter(adapter);
                allQueueRecyclerView.setLayoutManager(new LinearLayoutManager(RestaurantAllQueuesActivity.this));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        // seats.add(new Seat("A11", 2));



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}