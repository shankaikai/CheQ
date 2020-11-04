package com.example.cheq.Restaurant;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.cheq.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestaurantActivity extends AppCompatActivity {
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
}
