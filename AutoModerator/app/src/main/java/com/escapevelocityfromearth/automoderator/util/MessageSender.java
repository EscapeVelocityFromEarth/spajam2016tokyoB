package com.escapevelocityfromearth.automoderator.util;


import android.content.Context;

public class MessageSender {

    public static void sendMessage(Context context, String text) {
        sendMessage(context, System.currentTimeMillis(),
                Prefs.loadUserName(context), text);
    }

    public static void sendMessage(Context context, long time, String userName, String text) {
        //メッセージを送信する
    }

}
