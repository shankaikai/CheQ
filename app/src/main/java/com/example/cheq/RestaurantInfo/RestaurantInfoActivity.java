package com.example.cheq.RestaurantInfo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.cheq.R;
import com.example.restinfo_queue.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class RestaurantInfoActivity extends AppCompatActivity {

    RecyclerView menuRecyclerView;
    // int itemImages[] = {};

    // String s1[], s2[];
    ArrayList<Integer> images;
    Button joinQueueButton;
    PopupWindow popUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        joinQueueButton = findViewById(R.id.joinQueueButton);

        menuRecyclerView = findViewById(R.id.menuRecyclerView);

        images = new ArrayList<>();
        images.add(R.drawable.crookedcooks);

        // MenuAdapter menuAdapter = new MenuAdapter(this, s1, s2, itemImages);
        // menuRecyclerView.setAdapter(menuAdapter);
        // menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
}