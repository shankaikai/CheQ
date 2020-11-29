package com.example.cheq.Users;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.MainActivity;
import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.cheq.Users.UserHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeFragment extends Fragment implements ViewOutletsListAdapter.onRestaurantListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "test";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // variables required for the View All Outlets Segment
    RecyclerView outletsList;
    RecyclerView queueAgainList;
    RecyclerView.Adapter mAdapter;

    // Firebase
    FirebaseManager firebaseManager;

    // User Info
    String userPhone;

    // Lists to store restaurants to display
    ArrayList<String> restaurantsQueueAgainList;
    HashMap<String, HashMap<String, String>> restaurantInfo;
    HashMap<String, HashMap<String, String>> allRestaurants;

    // UI Elements
    LinearLayout queueAgainLayout;
    LinearLayout allOutletsLayout;
    Button viewOutletsBtn;
    ImageView homeLoading;

    public UserHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.cheq.Users.UserHomeFragment newInstance(String param1, String param2) {
        com.example.cheq.Users.UserHomeFragment fragment = new com.example.cheq.Users.UserHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        final View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        // Initialise firebaseManager
        firebaseManager = new FirebaseManager();

        final DatabaseReference rootRef = firebaseManager.rootRef;
        MainActivity activity = (MainActivity) getActivity();
        userPhone = activity.getUserPhone();

        // Linking the variables to UI elements
        queueAgainLayout = view.findViewById(R.id.queueAgainLayout);
        allOutletsLayout = view.findViewById(R.id.allOutletsLayout);
        viewOutletsBtn = view.findViewById(R.id.viewOutletsBtn);
        homeLoading = view.findViewById(R.id.homeLoading);

        // Initialise variables
        allRestaurants = new HashMap<>();

        // Retrieving the user's name and email from firebase and
        // Store these info locally in the Main Activity
        rootRef.child("Users").child(userPhone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("pastQueues").exists()) {
                    restaurantsQueueAgainList = new ArrayList<>();
                    Query query = rootRef.child("Users").child(userPhone).child("pastQueues").orderByValue().limitToLast(5);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (Iterator<DataSnapshot> iter = snapshot.getChildren().iterator(); iter.hasNext();) {
                                String restaurantID = iter.next().getKey();
                                restaurantsQueueAgainList.add(restaurantID);
                            }
                            if (restaurantsQueueAgainList.size() > 1) {
                                Collections.reverse(restaurantsQueueAgainList);
                            }

                            restaurantInfo = new HashMap<>();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }

                Query restaurantQuery = rootRef.child("Restaurants");
                restaurantQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (Iterator<DataSnapshot> iter = snapshot.getChildren().iterator(); iter.hasNext();) {
                            String id = iter.next().getKey();
                            String category = snapshot.child(id).child("restCategory").getValue().toString();
                            String name = snapshot.child(id).child("restName").getValue().toString();
                            String imageURL = snapshot.child(id).child("restImageUri").getValue().toString();
                            allRestaurants.put(id, new HashMap<String, String>());
                            allRestaurants.get(id).put("category", category);
                            allRestaurants.get(id).put("name", name);
                            allRestaurants.get(id).put("image", imageURL);
                            if (restaurantsQueueAgainList != null && restaurantsQueueAgainList.contains(id)) {
                                restaurantInfo.put(id, new HashMap<String, String>());
                                restaurantInfo.get(id).put("category", category);
                                restaurantInfo.get(id).put("name", name);
                                restaurantInfo.get(id).put("image", imageURL);
                            }
                        }

                        // setting up the Queue Again list
                        // it will only be appeared if the users has completed queues
                        if (restaurantsQueueAgainList != null) {
                            queueAgainList = (RecyclerView) view.findViewById(R.id.queueAgainList);
                            queueAgainList.setLayoutManager(new LinearLayoutManager(UserHomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false));
                            mAdapter = new com.example.cheq.Users.ViewOutletsListAdapter(restaurantInfo, UserHomeFragment.this, getContext());
                            queueAgainList.setAdapter(mAdapter);
                            queueAgainList.setHasFixedSize(true);
                            queueAgainLayout.setVisibility(View.VISIBLE);
                        }

                        // setting up the All Outlets list
                        outletsList = (RecyclerView) view.findViewById(R.id.outletsList);
                        outletsList.setLayoutManager(new LinearLayoutManager(UserHomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false));
                        mAdapter = new com.example.cheq.Users.ViewOutletsListAdapter(allRestaurants,UserHomeFragment.this, getContext());
                        outletsList.setAdapter(mAdapter);
                        outletsList.setHasFixedSize(true);
                        // set the visibility of the UI elements to visible when the data is loaded
                        allOutletsLayout.setVisibility(View.VISIBLE);

                        // set the loading screen to invisible when the views are done loading
                        homeLoading.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        viewOutletsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send the data in allRestaurants to the AllOutletsFragment
                AllOutletsFragment fragment = new AllOutletsFragment();
                Bundle restaurants = new Bundle();
                restaurants.putSerializable("hashmap", allRestaurants);
                fragment.setArguments(restaurants);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, "home").addToBackStack("home").commit();
            }
        });

        return view;
    }

    // Opening up the restaurant information page when user clicks on the restaurant
    @Override
    public void onRestaurantClick(String id) {
        // TODO: change the MainActivity to the correct Activity name
//        Intent intent = new Intent(getActivity(), RestaurantPageActivity.class);
//        intent.putExtra("restaurantID", id);
//        getActivity().startActivity(intent);
        EmptyBasketFragment fragment = new EmptyBasketFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}