package com.example.cheq.Restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.cheq.R;

import org.w3c.dom.Text;

public class RestaurantSeatCustomerActivity extends AppCompatActivity {
    Button restaurantSeatCustomerBackButton, restaurantSeatCustomerSendOrderButton;
    TextView restaurantSeatCustomerSeatNoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_customer);
        restaurantSeatCustomerBackButton = findViewById(R.id.restaurantSeatCustomerBackBtn);
        restaurantSeatCustomerSendOrderButton = findViewById(R.id.restaurantSeatCustomerSendOrderBtn);
        restaurantSeatCustomerSeatNoTextView = findViewById(R.id.restaurantSeatCustomerSeatNoTextView);

        Intent intent = getIntent();
        Seat seat = (Seat) intent.getSerializableExtra("seat");

        restaurantSeatCustomerSeatNoTextView.setText(seat.getSeatNo());


        restaurantSeatCustomerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    class RestaurantSeatCustomerAdapter extends ArrayAdapter<String> {
        Context context;
        Integer[] noOfDish;
        String[] dishName;

        RestaurantSeatCustomerAdapter(Context context, Integer[] noOfDish, String[] dishName) {
            super(context, R.layout.row_restaurant_home_queue, R.id.xPaxRestaurant, dishName);
            this.context = context;
            this.noOfDish = noOfDish;
            this.dishName = dishName;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_restaurant_seat_customer, parent, false);
            TextView restaurantSeatCustomerDishNoTextView = row.findViewById(R.id.restaurantSeatCustomerDishNoTextView);
            TextView restaurantSeatCustomerDishNameTextView =row.findViewById(R.id.restaurantSeatCustomerDishNameTextView);

            restaurantSeatCustomerDishNoTextView.setText(noOfDish[position].toString());
            restaurantSeatCustomerDishNameTextView.setText(dishName[position]);

            return row;
        }
    }
}
