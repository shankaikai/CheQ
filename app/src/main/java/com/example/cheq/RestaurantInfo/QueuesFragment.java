package com.example.cheq.RestaurantInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.cheq.R;
import com.example.cheq.Users.ViewBasketFragment;


public class QueuesFragment extends Fragment {

    CardView basketCardView;
    ConstraintLayout basketCL;

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
        View view = inflater.inflate(R.layout.fragment_queues, container, false);

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

        return view;
    }
}