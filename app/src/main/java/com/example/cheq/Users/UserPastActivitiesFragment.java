package com.example.cheq.Users;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cheq.R;

/**
 * A fragment representing a list of Items.
 */
public class UserPastActivitiesFragment extends Fragment {

    String names[], dates[], groupsizes[];
    int images[] = {R.drawable.snorlax, R.drawable.bulbasaur,R.drawable.snorlax, R.drawable.bulbasaur,R.drawable.snorlax, R.drawable.bulbasaur,R.drawable.snorlax, R.drawable.bulbasaur,R.drawable.snorlax, R.drawable.bulbasaur,R.drawable.snorlax};


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
        View view = inflater.inflate(R.layout.fragment_past_activities_list, container, false);

        // Get lists from string.xml
        names = getResources().getStringArray(R.array.past_activities);
        dates = getResources().getStringArray(R.array.past_activities_date);
        groupsizes = getResources().getStringArray(R.array.past_activities_groupsize);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new UserPastActivitiesRecyclerViewAdapter(names, dates, groupsizes, images));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        }
        return view;
    }
}