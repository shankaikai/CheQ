package com.example.cheq.Login;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    // Instantiate SharePreference and Editor objects which will be used to store and edit sessions
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // String variables to store the shared preference name and session key
    String sharedPreferenceName = "sharedPreferences";

    public SessionManager(Context context){
        sharedPreferences= context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Call this function to remember that the user is logged in and add a session
    public void saveSession(String userPhone){
        editor.putString("userPhone", userPhone).commit();
    }

    // Call this function to check if user is logged in
    public boolean isLoggedIn(){
        if (sharedPreferences.getString("userPhone", "") == "") {
            return false;
        }
        return true;
    }

    // Get the number of the current logged in user
    public String getUser(){
        return sharedPreferences.getString("userPhone", "");
    }

    // Call this function to log out the user and remove the session
    public void removeSession() {
        editor.putString("userPhone", "").commit();
    }
}
