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
    final String PREORDER = "preorder";
    final String PREORDERCOUNT = "preorderCount";
    final String PREORDERTOTAL = "preorderTotal";
    final String PREORDERREST = "preorderRest";
    final String PREORDERUNIQUECOUNT = "preorderUniqueCount";
    final String PREORDERSTATUS = "preorderStatus";
    final String PREORDERRESTNAME = "preorderRestName";
    final String QUEUESTATUS = "queueStatus";

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

    // Call this function save the preorders (orders are not placed yet)
    public void savePreorder(String count, String total, String preorderString, String restaurantID) {
        editor.putString(PREORDERCOUNT, count).commit();
        editor.putString(PREORDERTOTAL, total).commit();
        editor.putString(PREORDER, preorderString).commit();
        editor.putString(PREORDERREST, restaurantID).commit();
    }

    public void uniqDishCount(String count) {
        editor.putString(PREORDERUNIQUECOUNT, count).commit();
    }

    public void setPreorderRestName(String name) {
        editor.putString(PREORDERRESTNAME, name).commit();
    }

    // Call this function to check if user is logged in
    public boolean isLoggedIn(){
        if (sharedPreferences.getString(USERPHONEKEY, "") == "") {
            return false;
        }
        return true;
    }

    // Call this function to check if there is a preorder
    public boolean hasPreorder(){
        if (sharedPreferences.getString(PREORDER, "") == "") {
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

    // Call this function to remove the preorder
    public void removePreorder() {
        editor.putString(PREORDER, "").commit();
        editor.putString(PREORDERREST, "").commit();
        editor.putString(PREORDERCOUNT, "").commit();
        editor.putString(PREORDERTOTAL, "").commit();
        editor.putString(PREORDERSTATUS, "").commit();
    }

    public void updatePreorderStatus(String status) {
        editor.putString(PREORDERSTATUS, status).commit();
    }

    public void updateQueueStatus(String status) {
        editor.putString(QUEUESTATUS, status).commit();
    }

    public String getUserPhone() {
        return sharedPreferences.getString(USERPHONEKEY, "");
    }

    public String getPreorderRest() {
        return sharedPreferences.getString(PREORDERREST, "");
    }

    public String getPreorder() {
        return sharedPreferences.getString(PREORDER, "");
    }

    public String getPreorderCount() {
        return sharedPreferences.getString(PREORDERCOUNT, "");
    }

    public String getPreorderTotal() {
        return sharedPreferences.getString(PREORDERTOTAL, "");
    }

    public String getPreorderUniqueCount() {
        return sharedPreferences.getString(PREORDERUNIQUECOUNT, "");
    }

    public String getPreorderStatus() {
        return sharedPreferences.getString(PREORDERSTATUS, "");
    }

    public void cancelPreorder() {
        editor.putString(PREORDERSTATUS, "").commit();
    }

    public String getPreorderRestName() {
        return sharedPreferences.getString(PREORDERRESTNAME, "");
    }

    public String getQueueStatus() {
        return sharedPreferences.getString(QUEUESTATUS, "");
    }

    public void clearPreferences() {
        sharedPreferences.edit().clear();
    }

}
