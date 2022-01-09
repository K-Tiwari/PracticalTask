package com.app.practicaltask;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public static final String PREF_DEFAULT_VALUE = "EMPTY";

    public static SharedPreferences getInstance(Context context) {
        return context.getSharedPreferences("PracticalTask", Context.MODE_PRIVATE);
    }

    public static String getStringPref(Context context, String key) {
        return getInstance(context).getString(key, PREF_DEFAULT_VALUE);
    }

    public static void setStringPref(Context context, String key, String value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static boolean getBooleanPref(Context context, String key) {
        return getInstance(context).getBoolean(key,false);
    }

    public static void setBooleanPref(Context context,String key,boolean value){
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
