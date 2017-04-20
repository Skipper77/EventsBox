package com.example.dellpc.eventsbox;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by DELL PC on 18-Apr-17.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    //Selecting Mode...
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "MyEventsBox";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String IS_LOGGED_IN ="IsLoggedIn";

    private static final String IS_STUDENT_LOGIN="IsStudentLogin";
    private static final String IS_ADMIN_LOGIN="IsAdminLogin";
    private static final String EMAIL="email";
    private static final String MESSAGE="message";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN,false);
    }

    public void setStudentLogIn(boolean isStudentLogin){
        editor.putBoolean(IS_STUDENT_LOGIN, isStudentLogin);
        editor.commit();
    }
    public void setAdminLogIn(boolean isAdminLogin){
        editor.putBoolean(IS_ADMIN_LOGIN, isAdminLogin);
        editor.commit();
    }

    public boolean isStudentLogIn(){
        return pref.getBoolean(IS_STUDENT_LOGIN,false);
    }
    public boolean isAdminLogIn(){
        return pref.getBoolean(IS_ADMIN_LOGIN,false);
    }

    public void setData(String email,String society){
        editor.putString(EMAIL, email);
        editor.putString(MESSAGE,society);
        editor.commit();
    }
    public HashMap<String,String> getData(){
        HashMap<String,String> values=new HashMap<>();
        values.put(EMAIL,pref.getString(EMAIL,null));
        values.put(MESSAGE,pref.getString(MESSAGE,null));
         return values;
    }
}
