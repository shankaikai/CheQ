package com.example.cheq.Restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.example.cheq.Users.Queue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SeatsAdapter extends RecyclerView.Adapter<SeatsAdapter.ViewHolder> {
    private ArrayList<Seat> seats;
    FirebaseManager firebaseManager = new FirebaseManager();
    Context context;


    public SeatsAdapter(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantAllQueuePax, restaurantAllQueueSeatNo;
        Button restaurantAllQueueSeatButton, restaurantAllQueueCancelButton;

        public ViewHolder(View itemView) {
            super(itemView);
            restaurantAllQueuePax = itemView.findViewById(R.id.restaurantAllQueuePax);
            restaurantAllQueueSeatNo = itemView.findViewById(R.id.restaurantAllQueueSeatNo);
            restaurantAllQueueSeatButton = itemView.findViewById(R.id.restaurantAllQueuesSeatButton);
            restaurantAllQueueCancelButton = itemView.findViewById(R.id.restaurantAllQueuesCancelButton);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View restaurantSeatsView = inflater.inflate(R.layout.row_restaurant_allqueue_recycleview, parent, false);

        // Return a new holder instance
        ViewHolder restaurantSeatsViewHolder = new ViewHolder(restaurantSeatsView);
        context = parent.getContext();
        return restaurantSeatsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        // Get the data model based on position
        final Seat seat = seats.get(position);


        // Set item views based on your views and data model
        TextView restaurantAllQueuePax = holder.restaurantAllQueuePax;
        TextView restaurantAllQueueSeatNo = holder.restaurantAllQueueSeatNo;
        Button restaurantAllQueueSeatButton = holder.restaurantAllQueueSeatButton;
        final Button restaurantAllQueueCancelButton = holder.restaurantAllQueueCancelButton;
        restaurantAllQueuePax.setText(seat.getNoOfPax());
        restaurantAllQueueSeatNo.setText(seat.getUserId());

        final DatabaseReference rootRef = firebaseManager.rootRef;
        final DatabaseReference restaurantQueueNoRef = rootRef.child("Queues").child(seat.getRestaurantId());
        final DatabaseReference restaurantUserRef = rootRef.child("Users").child(seat.getUserId());
        final DatabaseReference restaurantPreordersRef = rootRef.child("Preorders").child(seat.getRestaurantId());

        restaurantAllQueueCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //remove from "Queues"
                Log.i("restId", seat.getRestaurantId());
                restaurantQueueNoRef.child(seat.getNoOfPaxInt().toString()).child(seat.getSeatId()).removeValue();

                //remove from "Users/currentQueue"
                  Log.i("restUserId", restaurantUserRef.child("currentQueue").toString());
                  restaurantUserRef.child("currentQueue").removeValue();

                  //remove preorders
                if (restaurantPreordersRef.child(seat.getUserId()) != null){
                    restaurantPreordersRef.child(seat.getUserId()).removeValue();
                }

                Toast.makeText(context, "Customer Removed", Toast.LENGTH_SHORT).show();
            }
        });

        restaurantAllQueueSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference restaurantUserCurrentQueueRef = restaurantUserRef.child("currentQueue");

                //remove from "Users/currentQueue"
                if(restaurantUserCurrentQueueRef.child("userId")!=null){
                    restaurantUserCurrentQueueRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("groupSize").getValue()!=null){
                            Log.i("userId", seat.getUserId());
                            Log.i("groupSize", String.valueOf(snapshot.child("groupSize").getValue()));
                            Log.i("date", snapshot.child("date").getValue().toString());
                            Log.i("restaurantID",snapshot.child("restaurantID").getValue().toString());

                            Queue queue = new Queue(Integer.parseInt(snapshot.child("groupSize").getValue().toString()), snapshot.child("restaurantID").getValue().toString(), snapshot.child("date").getValue().toString());


                            firebaseManager.addToPastQueues(queue, seat.getUserId(), seat.getRestaurantId());
                            restaurantUserRef.child("currentQueue").removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });}
                //remove from "Queues"
                Log.i("hello", restaurantQueueNoRef.child(seat.getNoOfPaxInt().toString()).child(seat.getUserId()).toString());
                restaurantQueueNoRef.child(seat.getNoOfPaxInt().toString()).child(seat.getSeatId()).removeValue();

                //remove from "Preorders"
                if (restaurantPreordersRef.child(seat.getUserId()) != null){
                    restaurantPreordersRef.child(seat.getUserId()).removeValue();
                }

                Toast.makeText(context, "Customer Seated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }
}
