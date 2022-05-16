package com.example.myapplication.Session;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.myapplication.MainActivity;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TYPE = "type";

    public static final String KEY_MNAME = "mname";
    public static final String KEY_MEMAIL = "memail";
    public static final String KEY_MMOBILE = "mmobile";
    public static final String KEY_MBIRTH = "mbirth";
    public static final String KEY_MGENDER = "mgender";
    public static final String KEY_MPASSWORD = "mpassword";
    public static final String KEY_MIMAGE = "mimage";
    public static final String KEY_MTYPE = "mtype";


    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String name, String email ,String mobile,String location,String company,String password,String image,String type) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_LOCATION, location);
        editor.putString(KEY_COMPANY, company);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_IMAGE, image);
        editor.putString(KEY_TYPE, type);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status If false it will redirect
     * user to login page Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
        user.put(KEY_LOCATION, pref.getString(KEY_LOCATION, null));
        user.put(KEY_COMPANY, pref.getString(KEY_COMPANY, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_IMAGE, pref.getString(KEY_IMAGE, null));
        user.put(KEY_TYPE, pref.getString(KEY_TYPE, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        checkLogin();
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void createLoginSessionModel(String name, String email ,String mobile,String gender,String birthday,String password,String image,String type) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_MNAME, name);
        // Storing email in pref
        editor.putString(KEY_MEMAIL, email);
        editor.putString(KEY_MMOBILE, mobile);
        editor.putString(KEY_MGENDER, gender);
        editor.putString(KEY_MBIRTH, birthday);
        editor.putString(KEY_MPASSWORD, password);
        editor.putString(KEY_MIMAGE, image);
        editor.putString(KEY_MTYPE, type);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getModelDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_MNAME, pref.getString(KEY_MNAME, null));

        // user email id
        user.put(KEY_MEMAIL, pref.getString(KEY_MEMAIL, null));
        user.put(KEY_MMOBILE, pref.getString(KEY_MMOBILE, null));
        user.put(KEY_MGENDER, pref.getString(KEY_MGENDER, null));
        user.put(KEY_MBIRTH, pref.getString(KEY_MBIRTH, null));
        user.put(KEY_MPASSWORD, pref.getString(KEY_MPASSWORD, null));
        user.put(KEY_MIMAGE, pref.getString(KEY_MIMAGE, null));
        user.put(KEY_MTYPE, pref.getString(KEY_MIMAGE, null));

        // return user
        return user;
    }

}


