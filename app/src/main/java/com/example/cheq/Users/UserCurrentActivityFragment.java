package com.example.cheq.Users;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cheq.R;

public class UserCurrentActivityFragment extends Fragment {

    Button viewPreOrderButton, leaveQueueButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_current_activities, container, false);

        viewPreOrderButton = view.findViewById(R.id.view_pre_order_button);
        leaveQueueButton = view.findViewById(R.id.leave_queue_button);

        viewPreOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: pre order button
                Toast.makeText(getActivity(), "view_pre_order", Toast.LENGTH_SHORT).show();
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
