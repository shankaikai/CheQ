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
import android.widget.Toast;

import com.example.cheq.Entities.CurrentQueue;
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
    SharedPreferences sharedPreferences;
    ArrayList<String> resIDsArray = new ArrayList<>();
    ArrayList<String> datesArray = new ArrayList<>();
    ArrayList<String> groupSizesArray = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Uri> images = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> groupsizes = new ArrayList<>();



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
        sharedPreferences = getContext().getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(USERPHONEKEY, null); // get phone number from sharedpreference

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        if (userID != null) {

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snapshot1 : snapshot.child("Users").child(userID).child("pastQueues").getChildren()) {
                        //Log.i("What are you", snapshot1.toString());
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
                            Log.i("Snapshot is: ", snapshot.toString());
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                if (resIDsArray.contains(snapshot1.getKey())) {
                                    Log.i("restName", snapshot1.child("restName").getValue().toString());
                                    names.add(snapshot1.child("restName").getValue().toString());
                                    images.add(Uri.parse(snapshot1.child("restImageUri").getValue().toString()));
                                }
                            }
                            if (view instanceof RecyclerView) {
                                Context context = view.getContext();
                                RecyclerView recyclerView = (RecyclerView) view;
                                if (mColumnCount <= 1) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                } else {
                                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                }
                                recyclerView.setAdapter(new UserPastActivitiesRecyclerViewAdapter(names, dates, groupsizes, images, context));
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                    names = namesArray;
//                    for (String i : imagesArray) {
//                        images.add(Uri.parse(i));
//                    }
                    //Log.i("Names: ", names.toString());
                    //Log.i("Images: ", images.toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Service is unavailable", Toast.LENGTH_SHORT).show();
                }
            });
        }


        // Get lists from string.xml
//        names = getResources().getStringArray(R.array.past_activities);
//        dates = getResources().getStringArray(R.array.past_activities_date);
//        groupsizes = getResources().getStringArray(R.array.past_activities_groupsize);

        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new UserPastActivitiesRecyclerViewAdapter(names, dates, groupsizes, images));
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        }
        return view;
    }
}