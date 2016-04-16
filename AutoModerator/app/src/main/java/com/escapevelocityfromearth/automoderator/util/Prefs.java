package com.escapevelocityfromearth.automoderator.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static final String PREFS_KEY = "my_preferences";

    //ユーザ設定がされていなかった場合のデフォルトネーム
    private static final String KEY_DEFAULT_USER = "user_name";

    private static final String KEY_SERVER_URL = "server_url";

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

    public static void saveServerUrl(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_SERVER_URL, url);
        editor.apply();
    }

    public static String loadServerUrl(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_KEY, Activity.MODE_PRIVATE);
        return pref.getString(KEY_SERVER_URL, Const.DEFAULT_URL);
    }
}
