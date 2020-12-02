package com.example.cheq.Managers;


import android.util.Log;

import com.example.cheq.Entities.FirebaseDishItem;
import com.example.cheq.Entities.RestaurantInfoItem;
import com.example.cheq.Entities.User;
import com.example.cheq.Users.Preorder;
import com.example.cheq.Users.Queue;
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

    public void addRestaurantDetails(RestaurantInfoItem restaurantInfo, String userPhone) {
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
        firebaseReference.child(userPhone).child(firebaseDishItem.getDishName()).setValue(firebaseDishItem);
    }

    public void addPreorder(Preorder preorder) {
        DatabaseReference firebaseReference = firebaseInstance.getReference("Preorders");
        firebaseReference.child(preorder.getRestaurantID()).child(preorder.getUserID()).child(preorder.getDishName()).setValue(preorder.getDishQuantity());
    }

    public void addToUser(Queue queue, String userID) {
        DatabaseReference firebaseReference = firebaseInstance.getReference("Users");
        firebaseReference.child(userID).child("currentQueue").setValue(queue);
    }

    public void addToQueues(String userID, String restID, Integer count, Integer size) {
        DatabaseReference firebaseReference = firebaseInstance.getReference("Queues");
        firebaseReference.child(restID).child(size.toString()).child(count.toString()).setValue(userID);
    }

    public void addToPastQueues(Queue queue, String userID, String restaurantID) {
        DatabaseReference firebaseReference = firebaseInstance.getReference("Users");
        firebaseReference.child(userID).child("pastQueues").child(restaurantID).setValue(queue);
    }
}
