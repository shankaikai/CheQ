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

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

        SessionManager sessionManager;
        FirebaseManager firebaseManager;

        String userPhone;
        String userName;
        String userEmail;

        String currentActivity;

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

                if (getIntent().getStringExtra("currentActivity") != null) {
                        if (getIntent().getStringExtra("currentActivity").equals("1")) {
                                currentActivity = "1";
                                Log.i("activity", currentActivity);
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserActivitiesFragment()).commit();
                                bottomNav.getMenu().findItem(R.id.nav_activities).setChecked(true);
                        }
                }

                // Retrieving the user's name and email from firebase and
                // Store these info locally in the Main Activity
                rootRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                userName = snapshot.child("Users").child(userPhone).child("name").getValue(String.class);
                                userEmail = snapshot.child("Users").child(userPhone).child("userEmail").getValue(String.class);

                                Boolean checkCurrentQueue = snapshot.child("Users").child(userPhone).child("currentQueue").exists();

                                if (checkCurrentQueue) {
                                        sessionManager.updateQueueStatus("In Queue");
                                        String preorderRest = snapshot.child("Users").child(userPhone).child("currentQueue").child("restaurantID").getValue().toString();
                                        // Check if preorder exists
                                        if (snapshot.child("Preorders").child(preorderRest).child(userPhone).exists()) {
                                                // if preorder exists
                                                // get count of children and collate total price of basket
                                                long count = snapshot.child("Preorders").child(preorderRest).child(userPhone).getChildrenCount();
                                                Integer itemCount = 0;
                                                Double totalPrice = 0.0;
                                                String preorderStr = "";
                                                // get all the dish name, quantity and price
                                                for (Iterator<DataSnapshot> dish = snapshot.child("Preorders").child(preorderRest).child(userPhone).getChildren().iterator(); dish.hasNext();) {
                                                        String dishName = dish.next().getKey();
                                                        String dishQuantity = snapshot.child("Preorders").child(preorderRest).child(userPhone).child(dishName).getValue().toString();
                                                        Double dishPrice = Double.parseDouble(snapshot.child("Menu").child(preorderRest).child(dishName).child("dishPrice").getValue().toString());
                                                        totalPrice += Integer.parseInt(dishQuantity) * dishPrice;
                                                        // convert to preorderStr
                                                        itemCount += Integer.parseInt(dishQuantity);
                                                        preorderStr = preorderStr + dishName + "," + "quantity:" + dishQuantity + ",price:" + dishPrice.toString() + "/";
                                                }
                                                Log.i("preorder string", preorderStr);
                                                // add preorder info to session manager
                                                sessionManager.uniqDishCount(String.valueOf(count));
                                                sessionManager.savePreorder(String.valueOf(count), totalPrice.toString(), preorderStr.substring(0, preorderStr.length() - 1), preorderRest);
                                        }
                                } else {
                                        // remove data in shared preferences if user is not in queue / has been seated
                                        sessionManager.updateQueueStatus("");
                                        sessionManager.removePreorder();
                                        sessionManager.updatePreorderStatus("");
                                        Log.i("preorder = empty", sessionManager.getPreorder());
                                }
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