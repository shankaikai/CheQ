package com.example.cheq.Restaurant;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cheq.R;

public class RestaurantSeatPopupFragment extends DialogFragment {

    Button confirmSeatButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_restaurant_seat_popup, container, false);
        confirmSeatButton = view.findViewById(R.id.confirm_seat_button);
        confirmSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Customer Seated", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}