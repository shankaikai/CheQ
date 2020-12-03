package com.example.cheq.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class SessionManagerTest {

    Context context1;
    SessionManager sessionManager1;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;


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

    @Before
    public void init() {
        context1 = RuntimeEnvironment.application.getApplicationContext();
        sessionManager1 = SessionManager.getSessionManager(context1);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context1);
        editor = sharedPrefs.edit();
    }


    @Test
    public void getSessionManagerTest() {
        Context context2 = RuntimeEnvironment.application.getApplicationContext();
        SessionManager sessionManager2 = SessionManager.getSessionManager(context2);
        assertTrue(sessionManager1 == sessionManager2);
    }

    @Test
    public void saveSessionTestCustomer() {
        editor.putString(USERPHONEKEY, "88888888").commit();
        editor.putString(USERTYPE, "Customer").commit();
        assertEquals("Customer", sharedPrefs.getString(USERTYPE, "0"));
        assertEquals("88888888", sharedPrefs.getString(USERPHONEKEY, "0"));
    }
    @Test
    public void saveSessionTestRestaurant() {
        editor.putString(USERPHONEKEY, "88888888").commit();
        editor.putString(USERTYPE, "Restaurant").commit();
        editor.putString(RESTAURANTID, "a").commit();
        assertEquals("88888888", sharedPrefs.getString(USERPHONEKEY, "0"));
        assertEquals("Restaurant", sharedPrefs.getString(USERTYPE, "0"));
        assertEquals("a", sharedPrefs.getString(RESTAURANTID, "0"));
    }


    @Test
    public void savePreorderTest() {
        editor.putString(PREORDERCOUNT, "1").commit();
        editor.putString(PREORDERTOTAL, "2").commit();
        editor.putString(PREORDER, "a").commit();
        editor.putString(PREORDERREST, "b").commit();
        assertEquals("1", sharedPrefs.getString(PREORDERCOUNT, "0"));
        assertEquals("2", sharedPrefs.getString(PREORDERTOTAL, "0"));
        assertEquals("a", sharedPrefs.getString(PREORDER, "0"));
        assertEquals("b", sharedPrefs.getString(PREORDERREST, "0"));
    }

    @Test
    public void uniqDishCountTest() {
        editor.putString(PREORDERUNIQUECOUNT, "1").commit();
        assertEquals("1", sharedPrefs.getString(PREORDERUNIQUECOUNT, "0"));
    }

    @Test
    public void setPreorderRestNameTest() {
        editor.putString(PREORDERRESTNAME, "name").commit();
        assertEquals("name", sharedPrefs.getString(PREORDERRESTNAME, "0"));
    }

    @Test
    public void isLoggedInTest() {
        editor.putString(USERPHONEKEY, "88888888").commit();
        assertEquals(true, sharedPrefs.getString(USERPHONEKEY, "") == "88888888");
    }
    @Test
    public void isLoggedInTestFalse() {
        Boolean actual = sessionManager1.isLoggedIn();
        assertEquals(false, actual);
    }

    @Test
    public void hasPreorderTest() {
        editor.putString(PREORDER, "a").commit();
        assertEquals(true, sharedPrefs.getString(PREORDER, "") == "a");
    }
    @Test
    public void hasPreorderTestFalse() {
        assertEquals(false, sessionManager1.hasPreorder());
    }

    @Test
    public void getRestaurantIDTest() {
        editor.putString(RESTAURANTID, "test").commit();
        assertEquals("test", sharedPrefs.getString(RESTAURANTID, ""));
    }

    @Test
    public void getUserTypeTest() {
        editor.putString(USERTYPE, "Customer").commit();
        assertEquals("Customer", sharedPrefs.getString(USERTYPE, ""));
    }

    @Test
    public void removeSessionTest() {
        editor.putString(USERPHONEKEY, "").commit();
        editor.putString(USERTYPE, "").commit();
        assertEquals("", sharedPrefs.getString(USERPHONEKEY, "not empty"));
        assertEquals("", sharedPrefs.getString(USERTYPE, "not empty"));
    }

    @Test
    public void removePreorderTest() {
        editor.putString(PREORDER, "").commit();
        editor.putString(PREORDERREST, "").commit();
        editor.putString(PREORDERCOUNT, "").commit();
        editor.putString(PREORDERTOTAL, "").commit();
        editor.putString(PREORDERSTATUS, "").commit();
        assertEquals("", sharedPrefs.getString(PREORDER, "not empty"));
        assertEquals("", sharedPrefs.getString(PREORDERREST, "not empty"));
        assertEquals("", sharedPrefs.getString(PREORDERCOUNT, "not empty"));
        assertEquals("", sharedPrefs.getString(PREORDERTOTAL, "not empty"));
        assertEquals("", sharedPrefs.getString(PREORDERSTATUS, "not empty"));
    }

    @Test
    public void updatePreorderStatusTest() {
        editor.putString(PREORDERSTATUS, "DONE").commit();
        assertEquals("DONE", sharedPrefs.getString(PREORDERSTATUS, "DONE"));
    }

    @Test
    public void updateQueueStatusTest() {
        editor.putString(QUEUESTATUS, "seat").commit();
        assertEquals("seat", sharedPrefs.getString(QUEUESTATUS, "seat"));
    }

    @Test
    public void getUserPhoneTest() {
        editor.putString(USERPHONEKEY, "88888888").commit();
        assertEquals("88888888", sharedPrefs.getString(USERPHONEKEY, ""));
    }

    @Test
    public void getPreorderRestTest() {
        editor.putString(PREORDERREST, "test").commit();
        assertEquals("test", sharedPrefs.getString(PREORDERREST, ""));
    }

    @Test
    public void getPreorderTest() {
        editor.putString(PREORDER, "test").commit();
        assertEquals("test",sharedPrefs.getString(PREORDER, ""));
    }

    @Test
    public void getPreorderCountTest() {
        editor.putString(PREORDERCOUNT, "test").commit();
        assertEquals("test", sharedPrefs.getString(PREORDERCOUNT, ""));
    }

    @Test
    public void getPreorderTotalTest() {
        editor.putString(PREORDERTOTAL, "test").commit();
        assertEquals("test", sharedPrefs.getString(PREORDERTOTAL, ""));
    }

    @Test
    public void getPreorderUniqueCountTest() {
        editor.putString(PREORDERUNIQUECOUNT, "test").commit();
        assertEquals("test", sharedPrefs.getString(PREORDERUNIQUECOUNT, ""));
    }

    @Test
    public void getPreorderStatusTest() {
        editor.putString(PREORDERSTATUS, "test").commit();
        assertEquals("test", sharedPrefs.getString(PREORDERSTATUS, ""));
    }

    @Test
    public void cancelPreorderTest() {
        editor.putString(PREORDERSTATUS, "").commit();
        assertEquals("", sharedPrefs.getString(PREORDERSTATUS, "not empty"));
    }

    @Test
    public void getPreorderRestNameTest() {
        editor.putString(PREORDERRESTNAME, "test").commit();
        assertEquals("test", sharedPrefs.getString(PREORDERRESTNAME, "not empty"));
    }

    @Test
    public void getQueueStatusTest() {
        editor.putString(QUEUESTATUS, "test").commit();
        assertEquals("test", sharedPrefs.getString(QUEUESTATUS, ""));
    }

    @Test
    public void clearPreferencesTest() {
        editor.clear();
        assertEquals(new HashMap<>(),sharedPrefs.getAll());
    }
}