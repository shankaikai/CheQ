package com.example.cheq.Managers;


import com.example.cheq.Entities.RestaurantInfo;
import com.example.cheq.Entities.User;
import com.example.cheq.Login.RestaurantOnboard.DishItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

// This class handles all firebase insert interactions
public class FirebaseManager {

    public FirebaseDatabase firebaseInstance;
    public DatabaseReference rootRef;

    public FirebaseManager(){
        firebaseInstance = FirebaseDatabase.getInstance();
        rootRef = firebaseInstance.getReference();
    }

    public void registerNewUser(User user) {
        // Set reference to "Users" child
        DatabaseReference firebaseReference = firebaseInstance.getReference("Users");

        // Insert user into firebase
        firebaseReference.child(user.getUserPhone()).setValue(user);
    }

    public void addRestaurantDetails(RestaurantInfo restaurantInfo, String userPhone) {
        // Set reference to "Users" child
        DatabaseReference firebaseReference = firebaseInstance.getReference("Restaurants");

        // Insert user into firebase
        firebaseReference.child(userPhone).setValue(restaurantInfo);
    }

    // Upload menu to database
    private void uploadMenu(String userPhone, ArrayList<DishItem> menuList) {
        // Set reference to "Users" child
        DatabaseReference firebaseReference = firebaseInstance.getReference("Menus");

        // Insert user into firebase
        firebaseReference.child(userPhone).setValue(menuList);
    }
}
