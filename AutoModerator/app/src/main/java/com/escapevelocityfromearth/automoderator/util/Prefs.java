package com.escapevelocityfromearth.automoderator.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static final String PREFS_KEY = "my_preferences";

    //ユーザ設定がされていなかった場合のデフォルトネーム
    private static final String KEY_DEFAULT_USER = "user_name";

    public static boolean saveUserName(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_DEFAULT_USER, name);
        return editor.commit();
    }

    public static String loadUserName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_KEY, Activity.MODE_PRIVATE);
        return pref.getString(KEY_DEFAULT_USER, Const.DEFAULT_USER);
    }


}
