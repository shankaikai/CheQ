package com.example.cheq.Managers;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cheq.Entities.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
}
