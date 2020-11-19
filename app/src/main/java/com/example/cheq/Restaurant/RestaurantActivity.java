package com.example.cheq_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestaurantActivity extends AppCompatActivity {
    public final String TAG = "Logcat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home);

        BottomNavigationView bottomNavigationViewRestaurant = findViewById(R.id.bottomNavigationViewRestaurant);
        bottomNavigationViewRestaurant.setOnNavigationItemSelectedListener(navListenerRestaurant);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentRestaurant, new RestaurantHomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListenerRestaurant = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.restaurantHomeFragment:
                    selectedFragment = new RestaurantHomeFragment();
                    break;
                case R.id.restaurantAccountFragment:
                    selectedFragment = new RestaurantAccountFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentRestaurant, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Resume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Destory");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Restart");
    }
}

