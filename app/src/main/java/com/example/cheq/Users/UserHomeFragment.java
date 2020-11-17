package com.example.cheq.Users;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.cheq.Users.UserHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // variables required for the View Outlets Near You Segment
    RecyclerView outletsList;
    RecyclerView queueAgainList;
    RecyclerView.Adapter mAdapter;
    String[] restaurantNames;
    String[] categories;
    Integer[] restaurantImages;

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
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        // retrieving the string arrays
        Resources res = getResources();
        restaurantNames = res.getStringArray(R.array.restaurant_names);
        categories = res.getStringArray(R.array.categories);

        // manually adding images
        restaurantImages = new Integer[2];
        restaurantImages[0] = R.drawable.bulbasaur;
        restaurantImages[1] = R.drawable.snorlax;

        // setting up the Outlets Near You list
        outletsList = (RecyclerView) view.findViewById(R.id.outletsList);
        outletsList.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        mAdapter = new com.example.cheq.Users.ViewOutletsListAdapter(restaurantNames, categories, restaurantImages);
        outletsList.setAdapter(mAdapter);
        outletsList.setHasFixedSize(true);

        // setting up the Outlets Near You list
        queueAgainList = (RecyclerView) view.findViewById(R.id.queueAgainList);
        queueAgainList.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        mAdapter = new com.example.cheq.Users.ViewOutletsListAdapter(restaurantNames, categories, restaurantImages);
        queueAgainList.setAdapter(mAdapter);
        queueAgainList.setHasFixedSize(true);

        return view;
    }
}