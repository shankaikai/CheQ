package com.example.cheq_restaurant;

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


import org.w3c.dom.Text;

import java.net.Inet4Address;
import java.util.ArrayList;

public class RestaurantHomeFragment extends Fragment {

    Button viewAllQueuesBtn;
    ListView viewAllQueueListView;
    TextView preorderTextView;
    String[] xPax = {"2 Pax", "3 Pax","4 Pax","5 Pax","6 Pax++"};
//    Integer[] x = {1,2,3,4,5};
    String[] xWaiting = {"2 waiting", "3 waiting", "2 waiting", "1 waiting", "1 waiting"};
    Integer preorder = 9;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_home, container, false);
        viewAllQueueListView = view.findViewById(R.id.viewAllQueueListView);
        viewAllQueuesBtn = view.findViewById(R.id.viewAllQueueBtn);

        RestaurantHomeQueueAdapter arrayAdapterQ = new RestaurantHomeQueueAdapter(this.getContext() , xPax, xWaiting);
        viewAllQueueListView.setAdapter(arrayAdapterQ);

        preorderTextView = view.findViewById(R.id.preorderTextView);
        String preorderString = preorder.toString();
        preorderTextView.setText(preorderString);

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
        Integer[] x;
        String[] xWaiting;

        RestaurantHomeQueueAdapter(Context context, String[] xPax, String[] xWaiting) {
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
            xWaitingRestaurant.setText(xWaiting[position]);

            return row;
        }
    }
}
