package com.example.cheq.Restaurant;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.cheq.Login.LoginActivity;
import com.example.cheq.Login.RegistrationActivity;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestaurantActivity extends AppCompatActivity {

    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home);

        BottomNavigationView bottomNavigationViewRestaurant = findViewById(R.id.bottomNavigationViewRestaurant);
        bottomNavigationViewRestaurant.setOnNavigationItemSelectedListener(navListenerRestaurant);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentRestaurant, new RestaurantHomeFragment()).commit();

        logoutBtn = findViewById(R.id.buttonLogout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = SessionManager.getSessionManager(RestaurantActivity.this);
                sessionManager.removeSession();
                moveToLoginActivity();
            }
        });
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

    private void moveToLoginActivity() {
        Intent intent = new Intent(RestaurantActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
