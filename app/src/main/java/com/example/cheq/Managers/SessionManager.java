package com.example.cheq.Managers;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.cheq.Constants.SharedPreferencesConstants.*;

public class SessionManager {
    // Instantiate SharePreference and Editor objects which will be used to store and edit sessions
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // String variables
    final String SHAREDPREFNAME = "sharedPreferences";
    final String USERPHONEKEY = "userPhone";
    final String USERTYPE = "userType";
    final String RESTAURANTID = "restaurantID";

    // Singleton SessionManager object
    private static SessionManager sessionManager = null;

    private SessionManager(Context context){
        sharedPreferences= context.getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * This method checks if the user is already logged in by calling the session manager class
     * @return a boolean value
     */
    public static SessionManager getSessionManager(Context context) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context);
        }
        return sessionManager;
    }

    // Call this function to remember that the customer user is logged in and add a session
    public void saveSession(String userPhone, String userType){
        editor.putString(USERPHONEKEY, userPhone).commit();
        editor.putString(USERTYPE, userType).commit();
    }

    // Call this function to remember that the restaurant user is logged in and add a session
    public void saveSession(String userPhone, String userType, String restaurantID){
        editor.putString(USERPHONEKEY, userPhone).commit();
        editor.putString(USERTYPE, userType).commit();
        editor.putString(RESTAURANTID, restaurantID).commit();
    }

    // Call this function to check if user is logged in
    public boolean isLoggedIn(){
        if (sharedPreferences.getString(USERPHONEKEY, "") == "") {
            return false;
        }
        return true;
    }

    // Call this function to get the restaurant ID of the logged in restaurant user
    public String getRestaurantID() {
        return sharedPreferences.getString(RESTAURANTID, "");
    }

    // Call this function to retrieve userType
    public String getUserType(){
        return sharedPreferences.getString(USERTYPE, "");
    }

    // Call this function to log out the user and remove the session
    public void removeSession() {
        editor.putString(USERPHONEKEY, "").commit();
        editor.putString(USERTYPE, "").commit();
    }
}
