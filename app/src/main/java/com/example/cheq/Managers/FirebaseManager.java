package com.example.cheq.Managers;


import android.util.Log;

import com.example.cheq.Entities.FirebaseDishItem;
import com.example.cheq.Entities.RestaurantInfo;
import com.example.cheq.Entities.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// This class handles all firebase interactions
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

    public void addDish(FirebaseDishItem firebaseDishItem, String userPhone) {
        // Set reference to "Users" child
        DatabaseReference firebaseReference = firebaseInstance.getReference("Menu");
        Log.i("hi", "add dish");
        // Insert user into firebase
        firebaseReference.child(userPhone).push().setValue(firebaseDishItem);
    }
}
