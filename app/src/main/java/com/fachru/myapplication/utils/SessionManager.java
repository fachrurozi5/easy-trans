package com.fachru.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fachru on 14/03/16.
 */
public class SessionManager {

    private static final String KEY_PHONE_NUMBER = "phone_number";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences("easy-trans-pref", 0);
        editor = preferences.edit();
    }

    public void set_phone_number(String phone_number) {
        editor.putString(KEY_PHONE_NUMBER, phone_number);
        editor.commit();
    }

    public String get_phone_number() {
        return preferences.getString(KEY_PHONE_NUMBER, "");
    }

}
