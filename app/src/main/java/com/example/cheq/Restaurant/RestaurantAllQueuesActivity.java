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
        allQueueRecyclerView = findViewById(R.id.allQueueRecyclerView);

        firebaseManager = new FirebaseManager();
        restaurantId = "88888888";

        DatabaseReference rootRef = firebaseManager.rootRef;
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot restaurantQueueSnapshot = dataSnapshot.child("Queues").child(restaurantId);
                ArrayList<Seat> seats= new ArrayList<>();

                //The queue no. is always "AXX" where the first "X" is the no. of pax, the second "X" is the index of the queue in that category of no. of pax.
                for (DataSnapshot noOfPax : restaurantQueueSnapshot.getChildren()) {
                    for (DataSnapshot seatNo2: noOfPax.getChildren()){
                        seats.add(new Seat("A" + noOfPax.toString() + seatNo2.toString(), Integer.parseInt(noOfPax.toString())));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

//        seats.add(new Seat("A444", 4));
//        seats.add(new Seat("A422", 4));
//        seats.add(new Seat("A411", 2));

        //Setting the SeatsAdapter up to display the queues in a Recyclerview.
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