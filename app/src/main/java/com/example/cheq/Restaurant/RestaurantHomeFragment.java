package com.example.cheq.Restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantHomeFragment extends Fragment {

    Button viewAllQueuesBtn;
    ListView viewAllQueueListView;
    FirebaseManager firebaseManager;
    String restaurantId;
    final String[] xPax = {"2 Pax", "3 Pax","4 Pax","5 Pax","6 Pax++"};
    long[] xWaiting = new long[xPax.length];



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_home, container, false);
        viewAllQueueListView = view.findViewById(R.id.viewAllQueueListView);
        viewAllQueuesBtn = view.findViewById(R.id.viewAllQueueBtn);


        firebaseManager = new FirebaseManager();
        //need to update this restaurantId
        restaurantId = "88888888";

        final DatabaseReference rootRef = firebaseManager.rootRef;
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot restaurantQueueSnapshot = dataSnapshot.child("Queues").child(restaurantId);
                xWaiting[0] = restaurantQueueSnapshot.child("1").getChildrenCount() + restaurantQueueSnapshot.child("2").getChildrenCount();
                xWaiting[1] = restaurantQueueSnapshot.child("3").getChildrenCount();
                xWaiting[2] = restaurantQueueSnapshot.child("4").getChildrenCount();
                xWaiting[3] = restaurantQueueSnapshot.child("5").getChildrenCount();
                xWaiting[4] = restaurantQueueSnapshot.child("6").getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //HashMap<type of no. of pax waiting, no. of each type>

        RestaurantHomeQueueAdapter arrayAdapterQ = new RestaurantHomeQueueAdapter(this.getContext() , xPax, xWaiting);
        viewAllQueueListView.setAdapter(arrayAdapterQ);

        viewAllQueuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRestaurantAllQueueActivity();
            }
        });

        return view;
    }
    private void moveToRestaurantAllQueueActivity() {
        Intent intent = new Intent(getActivity(), RestaurantAllQueuesActivity.class);
        startActivity(intent);
    }
    class RestaurantHomeQueueAdapter extends ArrayAdapter<String>{
        Context context;
        String[] xPax;
        long[] xWaiting;

        RestaurantHomeQueueAdapter(Context context, String[] xPax, long[] xWaiting) {
            super(context, R.layout.row_restaurant_home_queue, R.id.xPaxRestaurant, xPax);
            this.context = context;
            this.xPax = xPax;
            this.xWaiting = xWaiting;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_restaurant_home_queue, parent, false);
            TextView xPaxRestaurant = row.findViewById(R.id.xPaxRestaurant);
            TextView xWaitingRestaurant =row.findViewById(R.id.xWaitingRestaurant);

            xPaxRestaurant.setText(xPax[position]);
            xWaitingRestaurant.setText(xWaiting[position] + " waiting");

            return row;
        }
    }
}
