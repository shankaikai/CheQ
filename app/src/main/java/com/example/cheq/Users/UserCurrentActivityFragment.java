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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.cheq.MainActivity;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.example.cheq.RestaurantInfo.RestaurantInfoActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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

    TextView currentActivityName;
    TextView currentActivityDate;
    TextView currentActivityGroupSize;
    TextView currentActivityGroupsBefore;
    TextView currentActivityWait;
    TextView noCurrentActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getContext().getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(USERPHONEKEY, null); // get phone number from sharedpreference

        view = inflater.inflate(R.layout.fragment_user_current_activities, container, false);

        // Initialise UI Elements
        viewPreOrderButton = view.findViewById(R.id.viewPreOrderButton);
        leaveQueueButton = view.findViewById(R.id.leaveQueueButton);
        currentActivityConstraintLayout = view.findViewById(R.id.currentActivityCL);
        noCurrentActivity = view.findViewById(R.id.noCurrentActivity);

        // Retrieve preorder info and display onto the cardView
        currentActivityName = view.findViewById(R.id.currentActivityName);
        currentActivityDate = view.findViewById(R.id.currentActivityDate);
        currentActivityGroupSize = view.findViewById(R.id.currentActivityGroupSize);
        currentActivityGroupsBefore = view.findViewById(R.id.currentActivityGroupsBefore);
        currentActivityWait = view.findViewById(R.id.currentActivityWait);

        leaveQueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: leave queue button
                UserLeaveQueuePopupFragment userLeaveQueuePopupFragment = new UserLeaveQueuePopupFragment();
                userLeaveQueuePopupFragment.show(getFragmentManager(), "userLeaveQueuePopupFragment");
            }
        });

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(userID).hasChild("currentQueue")) {
                    isCurrentQueuePresent = true;
                    // Log.i("CurrentQueue present?", isCurrentQueuePresent.toString());

                    // Retrieve queue info
                    final String restID = snapshot.child("Users").child(userID).child("currentQueue").child("restaurantID").getValue().toString();
                    String name = snapshot.child("Restaurants").child(restID).child("restName").getValue().toString();
                    String date = snapshot.child("Users").child(userID).child("currentQueue").child("date").getValue().toString();
                    Integer size = Integer.parseInt(snapshot.child("Users").child(userID).child("currentQueue").child("groupSize").getValue().toString());

                    // Format group size
                    String sizeStr = size.toString();
                    if (size == 1) {
                        sizeStr += " person";
                    } else {
                        sizeStr += " people";
                    }
                    currentActivityGroupSize.setText(sizeStr);

                    currentActivityName.setText(name);

                    // Format date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd MMMM yyyy, E");
                    try {
                        Date formattedDate = dateFormat.parse(date);
                        currentActivityDate.setText(dateFormat2.format(formattedDate));
                    } catch (ParseException e) {
                        Log.i("date not parsed", "help");
                    }

                    // Check if preorder data exists
                    Boolean check = snapshot.child("Preorders").child(restID).child(userID).exists();
                    if (snapshot.child("Preorders").child(restID).child(userID).exists()) {
                        // If yes, view preorder button redirects to basket
                        viewPreOrderButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), BlankActivity.class);
                            startActivity(intent);
                            }
                        });
                    } else {
                        // If no, change button text to place order and redirects to restaurant menu
                        viewPreOrderButton.setText("Place Order");
                        viewPreOrderButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), RestaurantInfoActivity.class);
                            intent.putExtra("restaurantID", restID);
                            startActivity(intent);
                            }
                        });
                    }

                    // toggle the view
                    currentActivityConstraintLayout.setVisibility(View.VISIBLE);
                    noCurrentActivity.setVisibility(View.INVISIBLE);
                } else {
                    isCurrentQueuePresent = false;
                    currentActivityConstraintLayout.setVisibility(View.GONE);
                    noCurrentActivity.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
