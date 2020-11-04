package com.example.cheq.Login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cheq.User;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context){
        sharedPreferences= context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user){
        //save session of user whenever user is logged
        int id = user.getId();
        editor.putInt(SESSION_KEY, id).commit();
    }

    public int getSession(){
        //return user whose session is saved else -1
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void removeSession() {
        editor.putInt(SESSION_KEY, -1).commit();
    }
}
