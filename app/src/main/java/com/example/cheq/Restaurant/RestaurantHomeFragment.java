package com.example.cheq.Restaurant;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.cheq.R;

import java.net.Inet4Address;

public class RestaurantHomeFragment extends Fragment {
    String[] xPax = {"2 Pax", "3 Pax","4 Pax","5 Pax","6 Pax++"};
    Integer[] x = {1,2,3,4,5};
    String xWaiting = " waiting";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_home, container, false);
        ListView viewAllQueueListView = view.findViewById(R.id.viewAllQueueListView);


        return view;
    }
    class RestaurantHomeAdapter extends ArrayAdapter<String>{
        Context c;
        String[] xPax;
        Integer[] x;
        String xWaiting;


        public RestaurantHomeAdapter(@NonNull Context context, String[] xPax, Integer[] x, ) {
            super(context, R.layout.row_restaurant_home_queue, R.id.xPaxRestaurant,
        }
    }
}
