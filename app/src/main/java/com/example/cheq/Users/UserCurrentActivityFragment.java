package com.example.cheq.Users;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.cheq.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;

import static com.example.cheq.Constants.SharedPreferencesConstants.SHAREDPREFNAME;
import static com.example.cheq.Constants.SharedPreferencesConstants.USERPHONEKEY;

public class UserCurrentActivityFragment extends Fragment {

    Button viewPreOrderButton, leaveQueueButton;
    ConstraintLayout currentActivityConstraintLayout;
    SharedPreferences sharedPreferences;
    String userID;
    Boolean isCurrentQueuePresent = false;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getContext().getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(USERPHONEKEY, null); // get phone number from sharedpreference

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(userID).hasChild("currentQueue")) {
                    isCurrentQueuePresent = true;
                    Log.i("CurrentQueue present?", isCurrentQueuePresent.toString());
                } else isCurrentQueuePresent = false;

                if (isCurrentQueuePresent == true) {
                    currentActivityConstraintLayout.setVisibility(View.VISIBLE);
                } else {
                    currentActivityConstraintLayout.setVisibility(View.GONE);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        view = inflater.inflate(R.layout.fragment_user_current_activities, container, false);

        viewPreOrderButton = view.findViewById(R.id.view_pre_order_button);
        leaveQueueButton = view.findViewById(R.id.leave_queue_button);
        currentActivityConstraintLayout = view.findViewById(R.id.current_activity_constraintLayout);

        viewPreOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: pre order button
                Toast.makeText(getActivity(), "view_pre_order", Toast.LENGTH_SHORT).show();

                // Retrieve preorder from firebase
                

                ViewBasketFragment fragment = new ViewBasketFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userActivitiesBase, fragment).commit();
            }
        });

        leaveQueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: leave queue button
                UserLeaveQueuePopupFragment userLeaveQueuePopupFragment = new UserLeaveQueuePopupFragment();
                userLeaveQueuePopupFragment.show(getFragmentManager(), "userLeaveQueuePopupFragment");
            }
        });

        return view;
    }
}
