package com.example.cheq.RestaurantInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.R;
import com.example.cheq.RestaurantInfo.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestaurantInfoActivity extends AppCompatActivity {

    RecyclerView menuRecyclerView;

    FirebaseManager firebaseManager;

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
}