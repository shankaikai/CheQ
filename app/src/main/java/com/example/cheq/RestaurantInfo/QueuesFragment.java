package com.example.cheq.RestaurantInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.example.cheq.Users.ViewBasketFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class QueuesFragment extends Fragment {

    CardView basketCardView;
    ConstraintLayout basketCL;

    private RecyclerView recyclerView;

    // Session Manager
    SessionManager sessionManager;

    // Firebase
    FirebaseManager firebaseManager;

    // Restaurant ID
    String restaurantID;

    // Variables needed for info
    ArrayList<Integer> currentQInfo;

    public QueuesFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_queues, container, false);

        currentQInfo = new ArrayList<>();

        sessionManager = SessionManager.getSessionManager(getActivity());

        // Initialise firebaseManager
        firebaseManager = new FirebaseManager();

        final DatabaseReference rootRef = firebaseManager.rootRef;
        RestaurantInfoActivity activity = (RestaurantInfoActivity) getActivity();
        restaurantID = activity.getRestaurantID();

        basketCardView = getActivity().findViewById(R.id.basketCardView);
        basketCL = getActivity().findViewById(R.id.basketCL);
        basketCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewBasketFragment fragment = new ViewBasketFragment();

                // Toggle visibility of the restaurant info to toggle to the basket view
                View restInfo = getActivity().findViewById(R.id.restInfoLayout);
                restInfo.setVisibility(View.INVISIBLE);
                basketCL.setVisibility(View.INVISIBLE);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, fragment, "queues").addToBackStack("queues").commit();
            }
        });

        // retrieve restaurant info from firebase
        rootRef.child("Queues").child(restaurantID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int i = 1; i <= 6; i++) {
                    String count = snapshot.child(i + "").getChildrenCount() + "";
                    int intCount = Integer.parseInt(count);
                    currentQInfo.add(intCount);
                }
                // Log.i("info", currentQInfo.toString());

                recyclerView = view.findViewById(R.id.queueRecyclerView);

                if (sessionManager.hasPreorder() && !sessionManager.getPreorderStatus().equals("Ordered")) {
                    recyclerView.setPadding(0,0,0,160);
                } else {
                    recyclerView.setPadding(0,0,0,0);
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(QueuesFragment.this.getContext()));
                recyclerView.setAdapter(new QueueAdapter(getContext(), currentQInfo));
                recyclerView.setHasFixedSize(true);
                ViewCompat.setNestedScrollingEnabled(recyclerView, false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;
    }
}