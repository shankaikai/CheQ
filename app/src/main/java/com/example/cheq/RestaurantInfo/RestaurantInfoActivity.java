package com.example.cheq.RestaurantInfo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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

import org.w3c.dom.Text;

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
    String restaurantWaitTime;

    // UI Elements
    ImageView restImage;
    TextView restTitle;
    TextView restCategory;
    Button joinQueueButton;
    TextView restWait;
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
        restWait = findViewById(R.id.numTime);


        // TODO: Initialise waiting time

        // Initialise firebaseManager
        firebaseManager = new FirebaseManager();
        final DatabaseReference rootRef = firebaseManager.rootRef;

        //TODO: Average waiting time
        rootRef.child("Queues").child(restaurantID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (int i = 1; i <= 6; i++) {
                    String indivCount = snapshot.child(i + "").getChildrenCount() + "";
                    int indivCountInt = Integer.parseInt(indivCount);
                    count += indivCountInt;
                }
                if (count == 0) {
                    int waitingTime = 0;
                    restaurantWaitTime = String.valueOf(waitingTime);
                    restWait.setText(restaurantWaitTime);
                } else {
                    int waitingTime = (count * 20) / count;
                    restaurantWaitTime = String.valueOf(waitingTime);
                    restWait.setText(restaurantWaitTime);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // if user is already in queue, join button is greyed out
        if (sessionManager.getQueueStatus().equals("In Queue")) {
            if (sessionManager.getPreorderRest().equals(restaurantID)) {
                joinQueueButton.setOnClickListener(null);
                joinQueueButton.setText("Joined Queue");
                joinQueueButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                joinQueueButton.setBackground(getResources().getDrawable(R.drawable.joined_queue_button));
            } else {
                joinQueueButton.setOnClickListener(null);
                joinQueueButton.setBackground(getResources().getDrawable(R.drawable.greyed_out_button));
            }
        }

        // retrieve restaurant info from firebase
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                restaurantName = snapshot.child("Restaurants").child(restaurantID).child("restName").getValue().toString();
                restaurantDes = snapshot.child("Restaurants").child(restaurantID).child("restCategory").getValue().toString();
                restaurantImage = snapshot.child("Restaurants").child(restaurantID).child("restImageUri").getValue().toString();
                Glide.with(getApplicationContext()).load(restaurantImage).into(restImage);
                restTitle.setText(restaurantName);
                restCategory.setText(restaurantDes);

                // Retrieve userID
                String userID = sessionManager.getUserPhone();
                if (snapshot.child("Users").child(userID).child("currentQueue").exists()) {
                    sessionManager.updateQueueStatus("In Queue");
                    String id = snapshot.child("Users").child(userID).child("currentQueue").child("restaurantID").getValue().toString();
                    if (id.equals(restaurantID)) {
                        joinQueueButton.setOnClickListener(null);
                        joinQueueButton.setText("Joined Queue");
                        joinQueueButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                        joinQueueButton.setBackground(getResources().getDrawable(R.drawable.joined_queue_button));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // sessionManager.updateQueueStatus("");
    }

    public void onButtonShowPopupWindowClick(View v) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.join_queue_pop_up, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // blur window
        popupWindow.setBackgroundDrawable(null);
        popupWindow.showAsDropDown(popupView);

        View container = (View) popupWindow.getContentView().getParent();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        //add flag
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);

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

                        // update queue status on shared preferences
                        sessionManager.updateQueueStatus("In Queue");

                        // disable join queue button when user joined the queue
                        joinQueueButton.setOnClickListener(null);
                        joinQueueButton.setText("Joined Queue");
                        joinQueueButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                        joinQueueButton.setBackground(getResources().getDrawable(R.drawable.joined_queue_button));

                        // dismiss the popup
                        popupWindow.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        // set up the click function for cancel button
        final Button cancelBtn = popupView.findViewById(R.id.cancelButton);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
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