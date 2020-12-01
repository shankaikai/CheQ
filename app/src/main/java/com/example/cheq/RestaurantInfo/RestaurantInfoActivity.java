package com.example.cheq.RestaurantInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.example.cheq.RestaurantInfo.SectionsPagerAdapter;
import com.example.cheq.Users.Queue;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class RestaurantInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView menuRecyclerView;

    FirebaseManager firebaseManager;

    SessionManager sessionManager;

    // Store restaurant info
    String restaurantID;
    String restaurantName;
    String restaurantDes;
    String restaurantImage;

    // UI Elements
    ImageView restImage;
    TextView restTitle;
    TextView restCategory;
    Button joinQueueButton;
    PopupWindow popUp;
    Spinner paxSpinner;

    // Selected Pax No
    int paxNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        joinQueueButton = findViewById(R.id.joinQueueButton);

        // Initialise session manager
        sessionManager = SessionManager.getSessionManager(RestaurantInfoActivity.this);

        // retrieve restaurant ID from the main activity
        restaurantID = getIntent().getStringExtra("restaurantID");

        // Initialise UI elements
        restImage = findViewById(R.id.restImage);
        restTitle = findViewById(R.id.restaurantName);
        restCategory = findViewById(R.id.restaurantCategory);

        // Initialise firebaseManager
        firebaseManager = new FirebaseManager();
        final DatabaseReference rootRef = firebaseManager.rootRef;

        // retrieve restaurant info from firebase
        rootRef.child("Restaurants").child(restaurantID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Log.i("fb", snapshot.toString());
                restaurantName = snapshot.child("restName").getValue().toString();
                restaurantDes = snapshot.child("restCategory").getValue().toString();
                restaurantImage = snapshot.child("restImageUri").getValue().toString();
                Glide.with(RestaurantInfoActivity.this).load(restaurantImage).into(restImage);
                restTitle.setText(restaurantName);
                restCategory.setText(restaurantDes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // sessionManager.updateQueueStatus("");

        // if user is already in queue, join button is greyed out
        if (sessionManager.getQueueStatus().equals("In Queue")) {
            if (sessionManager.getPreorderRest().equals(restaurantID)) {
                joinQueueButton.setOnClickListener(null);
                joinQueueButton.setText("Joined Queue");
                joinQueueButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                joinQueueButton.setBackground(getResources().getDrawable(R.drawable.joined_queue_button));
            } else {
                joinQueueButton.setOnClickListener(null);
                joinQueueButton.setBackground(getResources().getDrawable(R.drawable.joined_queue_button));
            }
        }
    }

    public void onButtonShowPopupWindowClick(View v) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.join_queue_pop_up, null);

        // blur bg
        View popupBg = inflater.inflate(R.layout.blur_bg_frame, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // blur bg
        final PopupWindow popupWindowBg = new PopupWindow(popupBg, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // blur bg
        popupWindowBg.showAtLocation(v, Gravity.CENTER, 0, 0);

        // Set up the paxSpinner
        paxSpinner = popupView.findViewById(R.id.paxSpinner);
        String[] pax = {"1", "2", "3", "4", "5", "6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pax);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paxSpinner.setAdapter(adapter);
        paxSpinner.setOnItemSelectedListener(this);

        // initialise firebase
        final DatabaseReference rootRef = firebaseManager.rootRef;

        // set up clickListener for Join Queue Btn to send queue data into firebase
        final Button joinQueueBtn = popupView.findViewById(R.id.confirmJoinButton);
        joinQueueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = sessionManager.getUserPhone();

                Date queueDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                // Send to Users DB
                com.example.cheq.Users.Queue queue = new Queue(paxNo, restaurantID, dateFormat.format(queueDate));
                firebaseManager.addToUser(queue, userID);

                // Send to Queues ID
                rootRef.child("Queues").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Integer curNum = 0;

                        Boolean check = snapshot.child(restaurantID).child(new Integer(paxNo).toString()).exists();

                        if (check) {
                            for (Iterator<DataSnapshot> queue = snapshot.child(restaurantID).child(new Integer(paxNo).toString()).getChildren().iterator(); queue.hasNext();) {
                                Integer num = Integer.parseInt(queue.next().getKey());
                                if (num > curNum) {
                                    curNum = num;
                                }
                            }
                            // Add one to the highest value before adding to DB
                            curNum += 1;
                        }

                        firebaseManager.addToQueues(userID, restaurantID, curNum, paxNo);
                        Toast.makeText(RestaurantInfoActivity.this, "You have joined the queue successfully. Navigate to the menu tab to place your preorder.", Toast.LENGTH_LONG).show();

                        // dismiss the popup
                        popupWindow.dismiss();

                        // update queue status on shared preferences
                        sessionManager.updateQueueStatus("In Queue");

                        // disable join queue button when user joined the queue
                        joinQueueButton.setOnClickListener(null);
                        joinQueueButton.setText("Joined Queue");
                        joinQueueButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                        joinQueueButton.setBackground(getResources().getDrawable(R.drawable.joined_queue_button));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        paxNo = i + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}