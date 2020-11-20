package com.example.cheq.Users;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.R;

public class ViewOutletsList extends AppCompatActivity {

    RecyclerView myList;
    RecyclerView.Adapter mAdapter;
    String[] restaurantNames;
    String[] categories;
    Integer[] restaurantImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_home);

        // retrieving the string arrays
        Resources res = getResources();
        restaurantNames = res.getStringArray(R.array.restaurant_names);
        categories = res.getStringArray(R.array.categories);

        // setting up the list
        myList = (RecyclerView) findViewById(R.id.outletsList);
        myList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new com.example.cheq.Users.ViewOutletsListAdapter(restaurantNames, categories, restaurantImages);
        myList.setAdapter(mAdapter);
        myList.setHasFixedSize(true);
    }
}
