package com.example.cheq.Restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.R;

import java.util.ArrayList;
import java.util.List;

public class SeatsAdapter extends RecyclerView.Adapter<SeatsAdapter.ViewHolder> {
    private ArrayList<Seat> seats;

    public SeatsAdapter(ArrayList<Seat> seats){this.seats = seats;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantAllQueuePax,restaurantAllQueueSeatNo;
        Button restaurantAllQueueSeatButton,restaurantAllQueueCancelButton;

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
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View restaurantSeatsView = inflater.inflate(R.layout.row_restaurant_allqueue_recycleview, parent, false);

        // Return a new holder instance
        ViewHolder restaurantSeatsViewHolder = new ViewHolder(restaurantSeatsView);
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
        Button restaurantAllQueueCancelButton = holder.restaurantAllQueueCancelButton;
        restaurantAllQueuePax.setText(seat.getNoOfPax());
        restaurantAllQueueSeatNo.setText(seat.getSeatNo());

        restaurantAllQueueCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seats.remove(position);
                notifyItemRemoved(position);
            }
        });

//        restaurantAllQueueSeatButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                int a = holder.getAdapterPosition();
//                Intent intent = new Intent(v.getContext(), RestaurantSeatCustomerActivity.class);
//                Bundle b = new Bundle();
//                b.putSerializable("seat", seat);
//                intent.putExtras(b);
//                v.getContext().startActivity(intent);
//
////                intent.putExtra("noOfPax", seats.get(position).getNoOfPax());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }
}