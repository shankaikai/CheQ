package com.example.cheq.Users;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.R;
import com.example.cheq.Restaurant.RestaurantActivity;
import com.example.cheq.RestaurantInfo.RestaurantInfoActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AllOutletsFragment extends Fragment implements ViewAllOutletsListAdapter.onRestaurantListener, TextWatcher {

    FirebaseManager firebaseManager;

    // UI Elements
    EditText searchEditText;
    TextView noResultsTextView;
    RecyclerView viewAllOutletsList;
    Button backArrow;

    RecyclerView.Adapter mAdapter;

    // Restaurant Info
    HashMap<String, HashMap<String, String>> allRestaurants;
    HashMap<String, String> restaurantNamesIDs;

    public AllOutletsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_all_outlets, container, false);

        firebaseManager = new FirebaseManager();

        DatabaseReference rootRef = firebaseManager.rootRef;

        // Initialise UI elements
        searchEditText = view.findViewById(R.id.searchEditText);
        noResultsTextView = view.findViewById(R.id.noResultsTextView);
        backArrow = view.findViewById(R.id.backArrow);

        // Retrieve restaurants hashmap data
        Bundle b = this.getArguments();
        if (b.getSerializable("hashmap") != null) {
            allRestaurants = new HashMap<>();
            allRestaurants = (HashMap<String, HashMap<String, String>>) b.getSerializable(("hashmap"));
        }
        if (b.getSerializable("restaurantNames") != null) {
            restaurantNamesIDs = new HashMap<>();
            restaurantNamesIDs = (HashMap<String, String>) b.getSerializable(("restaurantNames"));
        }

        // validating all search inputs to control the recyclerview
        searchEditText.addTextChangedListener(this);

        for (String id: allRestaurants.keySet()) {
            // TODO: adjust to queue length after this works
            allRestaurants.get(id).put("waitingTime", "20 mins");
        }

        // setting up the list to display all restaurants in database
        // it will only be appeared if the users has completed queues
        if (this.getContext() != null) {
            viewAllOutletsList = (RecyclerView) view.findViewById(R.id.viewAllOutletsList);
            viewAllOutletsList.setLayoutManager(new LinearLayoutManager(AllOutletsFragment.this.getContext()));
            mAdapter = new com.example.cheq.Users.ViewAllOutletsListAdapter(allRestaurants, AllOutletsFragment.this, getContext());
            viewAllOutletsList.setAdapter(mAdapter);
            viewAllOutletsList.setVisibility(View.VISIBLE);
        }

//        rootRef.child("Queues"). addValueEventListener(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//               // search through the queues DB by each restaurant to calculate average waiting time
//               for (String id: allRestaurants.keySet()) {
//
//                   for (Iterator<DataSnapshot> size = snapshot.child(id).getChildren().iterator(); size.hasNext();) {
//                       String key = size.next().getKey();
//                       int count = (int) snapshot.child(id).child(key).getChildrenCount();
//                   }
//
//                   allRestaurants.get(id).put("waitingTime", "20 mins");
//               }
//
//               snapshot.child("Queues");
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//           }
//        });

        return view;
    }

    // Opening up the restaurant information page when user clicks on the restaurant
    @Override
    public void onRestaurantClick(String id) {
        Intent intent = new Intent(getActivity(), RestaurantInfoActivity.class);
        intent.putExtra("restaurantID", id);
        getActivity().startActivity(intent);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String userInput = searchEditText.getText().toString().toLowerCase().trim();
        updateView(userInput);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String userInput = charSequence.toString().trim();
        if (userInput.equals("")) {
            updateView(userInput);
        }
    }

    void updateView(String userInput) {
        if (!userInput.equals("")) {
            HashMap<String, HashMap<String, String>> searchedRestaurantInfo = new HashMap<>();
            // run the user input with the keyset of the data
            for (String id: restaurantNamesIDs.keySet()) {
                // if the user input is part of a restaurant name, the restaurant will be displayed
                // if the user input equals to a restaurant name, the restaurant will also be displayed
                if (restaurantNamesIDs.get(id).contains(userInput) || restaurantNamesIDs.get(id).equals(userInput)) {
                    HashMap<String, String> temp = allRestaurants.get(id);
                    searchedRestaurantInfo.put(id, temp);
                }
            }
            if (searchedRestaurantInfo.size() != 0) {
                viewAllOutletsList.setAdapter(new com.example.cheq.Users.ViewAllOutletsListAdapter(searchedRestaurantInfo, AllOutletsFragment.this, getContext()));
                noResultsTextView.setVisibility(View.INVISIBLE);
                viewAllOutletsList.setVisibility(View.VISIBLE);
            } else {
                viewAllOutletsList.setVisibility(View.INVISIBLE);
                noResultsTextView.setVisibility(View.VISIBLE);
            }
        } else if (userInput.equals("")) {
            noResultsTextView.setVisibility(View.INVISIBLE);
            viewAllOutletsList.setAdapter(new com.example.cheq.Users.ViewAllOutletsListAdapter(allRestaurants, AllOutletsFragment.this, getContext()));
            viewAllOutletsList.setVisibility(View.VISIBLE);
        }
    }
}