package com.example.cheq.Login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cheq.User;

public class SessionManager {
    // Instantiate SharePreference and Editor objects which will be used to store and edit sessions
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // String variables to store the shared preference name and session key
    String sharedPrefName = "session";
    String sessionKey = "session_user";

    public SessionManager(Context context){
        sharedPreferences= context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user){
        // Grabs the ID of the user that is logged in
        int id = user.getId();
        //save session of user whenever user is logged
        editor.putInt(sessionKey, id).commit();
    }

    public int getSession(){
        // Return user whose session is saved else -1
        return sharedPreferences.getInt(sessionKey, -1);
    }

    public void removeSession() {
        editor.putInt(sessionKey, -1).commit();
    }
}
