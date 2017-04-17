package com.example.dellpc.eventsbox;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DELL PC on 29-Mar-17.
 */

public class IntroManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context ctx;
    private final String KEY_CHECK="check";
    public IntroManager(Context c){
        ctx=c;
        pref= ctx.getSharedPreferences("first",Context.MODE_PRIVATE);
        editor=pref.edit();
    }

    public void setFirst(boolean isFirst){
        editor.putBoolean(KEY_CHECK,isFirst);
        editor.commit();
    }

    public boolean Check(){
       return pref.getBoolean(KEY_CHECK,false);
    }
}
