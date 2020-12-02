package com.example.cheq.Users;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.cheq.Constants.SharedPreferencesConstants;
import com.example.cheq.Entities.CurrentQueue;
import com.example.cheq.Entities.User;
import com.example.cheq.Login.PasswordActivity;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.ChildrenNode;

import static com.example.cheq.Constants.SharedPreferencesConstants.*;

public class UserLeaveQueuePopupFragment extends DialogFragment {

    Button confirmLeaveQueueButton;
    SharedPreferences sharedPreferences;
    String userID;
    CurrentQueue currentQueue;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_confirm_leave_queue_popup, container);
        sharedPreferences = getContext().getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(USERPHONEKEY, null); // get phone number from sharedpreference
        Log.i("userID: ", userID);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        sessionManager = SessionManager.getSessionManager(getActivity());

        confirmLeaveQueueButton = view.findViewById(R.id.confirm_Leave_button);
        confirmLeaveQueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check current queue groupsize, restaurantID
                Log.i("clicked leave queue", "");
                if (userID != null) {

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            currentQueue = snapshot.child("Users").child(userID).child("currentQueue").getValue(CurrentQueue.class);
                            databaseReference.child("Users").child(userID).child("currentQueue").removeValue();

                            if (currentQueue.getRestaurantID() != null) {
                                for (DataSnapshot dataSnapshot : snapshot.child("Queues").child(currentQueue.getRestaurantID()).child(currentQueue.getGroupSize().toString()).getChildren()) {
                                    Log.i("LOGCAT-queue", dataSnapshot.getValue().toString());
                                    String key = dataSnapshot.getKey();
                                    String value = dataSnapshot.getValue().toString();
                                    if (value.equals(userID)) {  // check if userID is in txhe queue, if it is, delete the value
                                        databaseReference.child("Queues").child(currentQueue.getRestaurantID()).child(currentQueue.getGroupSize().toString()).child(key).removeValue();
                                        sessionManager.updateQueueStatus("");
                                    }
                                }

                                Log.i("queue removed", "nooooo");

                                // Delete preorder if it exists
                                String restID = currentQueue.getRestaurantID();
                                if (snapshot.child("Preorders").child(restID).child(userID).exists()) {
                                    databaseReference.child("Preorders").child(restID).child(userID).removeValue();
                                    sessionManager.updatePreorderStatus("");
                                    sessionManager.removePreorder();
                                    Toast.makeText(getContext(), "You have left the queue and your preorder is removed", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.i("no preorders", "no deletion");
                                    Toast.makeText(getContext(), "You have left the queue", Toast.LENGTH_SHORT).show();
                                }

                                // Close popup
                                getFragmentManager().beginTransaction().remove(UserLeaveQueuePopupFragment.this).commit();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), "Service is unavailable", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
    }
}