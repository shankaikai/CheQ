package com.example.cheq.Users;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheq.Entities.CurrentQueue;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.cheq.Constants.SharedPreferencesConstants.SHAREDPREFNAME;
import static com.example.cheq.Constants.SharedPreferencesConstants.USERPHONEKEY;

/**
 * A fragment representing a list of Items.
 */
public class UserPastActivitiesFragment extends Fragment {

    String userID;
    SessionManager sessionManager;
    ArrayList<String> resIDsArray = new ArrayList<>();
    ArrayList<String> datesArray = new ArrayList<>();
    ArrayList<String> groupSizesArray = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Uri> images = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> groupsizes = new ArrayList<>();

    // UI Elements
    TextView noPastActivitiesTextView;
    RecyclerView pastActivitiesRecyclerView;

    // TODO: Customize parameter argument names
    private static final String PAST_ACTIVITIES_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserPastActivitiesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UserPastActivitiesFragment newInstance(int columnCount) {
        UserPastActivitiesFragment fragment = new UserPastActivitiesFragment();
        Bundle args = new Bundle();
        args.putInt(PAST_ACTIVITIES_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(PAST_ACTIVITIES_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_past_activities_list, container, false);

        // set up Session Manager and
        // get phone number from Session Manager
        sessionManager = SessionManager.getSessionManager(getActivity());
        userID = sessionManager.getUserPhone();

        // Initialise database
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Initialise UI
        noPastActivitiesTextView = view.findViewById(R.id.noPastActivitiesTextView);
        pastActivitiesRecyclerView = view.findViewById(R.id.pastActivitiesList);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(userID).child("pastQueues").exists()) {
                    // Set the no Past Activities view to invisible
                    noPastActivitiesTextView.setVisibility(View.INVISIBLE);

                    // Retrieve past queues data
                    for (DataSnapshot snapshot1 : snapshot.child("Users").child(userID).child("pastQueues").getChildren()) {
                        String key = snapshot1.getKey().toString();
                        resIDsArray.add(key);
                        datesArray.add(snapshot.child("Users").child(userID).child("pastQueues").child(key).child("date").getValue().toString());
                        groupSizesArray.add(snapshot.child("Users").child(userID).child("pastQueues").child(key).child("groupSize").getValue().toString());
                    }

                    dates = datesArray;
                    groupsizes = groupSizesArray;
                    Query query = databaseReference.child("Restaurants");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                if (resIDsArray.contains(snapshot1.getKey())) {
                                    names.add(snapshot1.child("restName").getValue().toString());
                                    images.add(Uri.parse(snapshot1.child("restImageUri").getValue().toString()));
                                }
                            }

                            // Set up the recycler view
                            pastActivitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            pastActivitiesRecyclerView.setAdapter(new UserPastActivitiesRecyclerViewAdapter(names, dates, groupsizes, images, getContext()));
                            pastActivitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            // set the visibility of the recycler view to visible
                            pastActivitiesRecyclerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    noPastActivitiesTextView.setVisibility(View.VISIBLE);
                    pastActivitiesRecyclerView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Service is unavailable", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}