package com.example.cheq;


import androidx.annotation.NonNull;

import com.example.cheq.Login.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// This class handles all firebase interactions with easy to use functions
public class FirebaseManager {

    private FirebaseDatabase firebaseInstance;
//    private FirebaseAuth firebaseAuth;
//    private FirebaseUser currentUser;

    // Private global variables
    private boolean userExist;
    private String userPassword;

    public FirebaseManager(){
        firebaseInstance = FirebaseDatabase.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        currentUser = firebaseAuth.getCurrentUser();
    }

    // TODO: Doesn't work
    public boolean userExist(String userPhone) {
        // Set reference to "Users" child
        DatabaseReference firebaseReference = firebaseInstance.getReference("Users").child(userPhone);

        // Check if child exists in Users
        firebaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check if the phone number exists in Users
                if(snapshot.exists()){
                    userExist = true;
                }
                else {
                    userExist = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                userExist = false;
            }
        });

        return userExist;
    }

    // Checks if the password entered is correct
    public boolean correctPassword (String userPhone, String inputPassword) {
        // Set reference to the userPhone child
        DatabaseReference firebaseReference = firebaseInstance.getReference(userPhone).child("userPassword");

        firebaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userPassword = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return userPhone.equals(inputPassword);
    }

    public void registerNewUser(User user) {
        // Set reference to "Users" child
        DatabaseReference firebaseReference = firebaseInstance.getReference("Users");

        // Insert user into firebase
        firebaseReference.child(user.getUserPhone()).setValue(user);
    }


}
