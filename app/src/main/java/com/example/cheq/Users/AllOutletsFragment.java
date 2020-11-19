package com.example.cheq.Users;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllOutletsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllOutletsFragment extends Fragment implements ViewAllOutletsListAdapter.onRestaurantListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "test";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // UI Elements
    EditText searchEditText;
    TextView noResultsTextView;
    RecyclerView viewAllOutletsList;
    Button backArrow;

    RecyclerView.Adapter mAdapter;

    // Restaurant Info
    HashMap<String, HashMap<String, String>> allRestaurants;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllOutletsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllOutletsFragment newInstance(String param1, String param2) {
        AllOutletsFragment fragment = new AllOutletsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AllOutletsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_all_outlets, container, false);

        // Initialise UI elements
        searchEditText = view.findViewById(R.id.searchEditText);
        noResultsTextView = view.findViewById(R.id.noResultsTextView);
        backArrow = view.findViewById(R.id.backArrow);

        // Retrieve restaurants hashmap data
        allRestaurants = new HashMap<>();
        Bundle b = this.getArguments();
        if (b.getSerializable("hashmap") != null) {
            allRestaurants = (HashMap<String, HashMap<String, String>>) b.getSerializable(("hashmap"));
        }

        for (String id: allRestaurants.keySet()) {
            // TODO: adjust to queue length after this works
            allRestaurants.get(id).put("waitingTime", "20 mins");
        }

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        // setting up the list to display all restaurants in database
        // it will only be appeared if the users has completed queues
        if (this.getContext() != null) {
            viewAllOutletsList = (RecyclerView) view.findViewById(R.id.viewAllOutletsList);
            viewAllOutletsList.setLayoutManager(new LinearLayoutManager(AllOutletsFragment.this.getContext()));
            mAdapter = new com.example.cheq.Users.ViewAllOutletsListAdapter(allRestaurants, AllOutletsFragment.this, getContext());
            viewAllOutletsList.setAdapter(mAdapter);
            viewAllOutletsList.setVisibility(View.VISIBLE);
        }

        return view;
    }

    // Opening up the restaurant information page when user clicks on the restaurant
    @Override
    public void onRestaurantClick(String id) {
        // TODO: change the MainActivity to the correct Activity name
        Intent intent = new Intent(getActivity(), RestaurantPageActivity.class);
        intent.putExtra("restaurantID", id);
        getActivity().startActivity(intent);
    }

}