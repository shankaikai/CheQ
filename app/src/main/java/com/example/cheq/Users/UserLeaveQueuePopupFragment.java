package com.example.cheq.Users;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.cheq.R;

public class UserLeaveQueuePopupFragment extends DialogFragment {

    Button confirmLeaveQueueButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_confirm_leave_queue_popup, container, false);
        confirmLeaveQueueButton = view.findViewById(R.id.confirm_Leave_button);
        confirmLeaveQueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "left queue", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}