package com.example.cheq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.cheq.Login.LoginActivity;

import com.example.cheq.MainActivity;
import com.example.cheq.Managers.FirebaseManager;

import com.example.cheq.Managers.SessionManager;
import com.example.cheq.Users.UserAccountFragment;
import com.example.cheq.Users.UserActivitiesFragment;
import com.example.cheq.Users.UserHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

        SessionManager sessionManager;
        FirebaseManager firebaseManager;

        String userPhone;
        String userName;
        String userEmail;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                BottomNavigationView bottomNav = findViewById(R.id.btm_nav_user);
                bottomNav.setOnNavigationItemSelectedListener(navListener);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserHomeFragment()).commit();

                // Retrieve the user's phone number from session manager
                sessionManager = SessionManager.getSessionManager(MainActivity.this);
                userPhone = sessionManager.getUserPhone();

                firebaseManager = new FirebaseManager();

                DatabaseReference rootRef = firebaseManager.rootRef;

                // Retrieving the user's name and email from firebase and
                // Store these info locally in the Main Activity
                rootRef.child("Users").child(userPhone).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                userName = snapshot.child("name").getValue(String.class);
                                userEmail = snapshot.child("userEmail").getValue(String.class);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                });
        }

        private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        switch (item.getItemId()) {
                                case R.id.nav_home:
                                        selectedFragment = new UserHomeFragment();
                                        break;
                                case R.id.nav_activities:
                                        selectedFragment = new UserActivitiesFragment();
                                        break;
                                case R.id.nav_account:
                                        selectedFragment = new UserAccountFragment();
                                        break;
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                        return true;
                }
        };

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.btm_nav_users, menu);
                return true;
        }

        public void logout(View view) {
                // Remove session and open login screen
                SessionManager sessionManager = SessionManager.getSessionManager(MainActivity.this);
                sessionManager.removeSession();
                moveToLoginActivity();
        }

        private void moveToLoginActivity() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        public String getUserName() {
                return userName;
        }

        public String getUserEmail() {
                return userEmail;
        }

        public String getUserPhone() {
                return userPhone;
        }
}