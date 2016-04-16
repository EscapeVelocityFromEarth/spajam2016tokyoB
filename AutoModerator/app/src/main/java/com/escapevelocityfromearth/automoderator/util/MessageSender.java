package com.escapevelocityfromearth.automoderator.util;


import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class MessageSender {

    private static final boolean DBG = Const.DBG;
    private static final String TAG = "MessageSender";

    private static final String sendMessageUrl = "";   //TODO URL

    private static final String KEY_TIME = "time";
    private static final String KEY_USER = "user";
    private static final String KEY_TEXT = "text";


    private Context context;
    private ArrayList<MessageData> messages;
    private ArrayList<Thread> threads;

    class MessageData {
        public long time = 0;
        public String userName = "";
        public String text = "";
    }

    public MessageSender(Context context) {
        this.context = context;

        /**
         サーバへの送信が詰まりそうであれば、アプリ側でキャッシュして順に送信する
         messages = new ArrayList<MessageData>();
         threads = new ArrayList<Thread>();
         **/

    }

    /** 情報がテキストのみの場合はデフォルトのユーザ名を使用 **/
    public void sendMessage(String text) {
        sendMessage(System.currentTimeMillis(), Prefs.loadUserName(context), text);
    }

    /** メッセージを生成してサーバに送信する **/
    public void sendMessage(long time, String userName, String text) {
        //メッセージを送信する
        //final String data = makeJson(time, userName, text);
        final String data = makePostData(time, userName, text);

        if(sendMessageUrl.equals("") || data.equals("")) {
            return;
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                DataOutputStream out = null;

                try {

                    URL url = new URL(sendMessageUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    //connection.setFixedLengthStreamingMode(data.getBytes().length);
                    //connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                    //connection.connect();

                    out = new DataOutputStream(connection.getOutputStream());
                    out.write(data.getBytes());
                    out.flush();
                    out.close();

                    int responseCode = connection.getResponseCode();
                    String result = "";

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        StringBuilder builder = new StringBuilder();
                        String line = "";

                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while ((line = reader.readLine()) != null) {
                            builder.append(String.format("%s", line));
                        }

                        result = builder.toString();

                    } else {
                        result = "Error : response code " + String.valueOf(responseCode);
                    }

                    if(DBG) Log.d(TAG, "result / " + result);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (connection != null) connection.disconnect();
                        if (reader != null) reader.close();
                        if (out != null) {
                            out.flush();
                            out.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();

    }

    public void requestRecord() {
        sendMessage(System.currentTimeMillis(), Const.CREATE_RECORD_USER, Const.CREATE_REORD_TEXT);
    }

    private static String makePostData(long time, String userName, String text) {
        String postData =
                KEY_TIME + "=" + timeStringFormat(time) + "&" +
                KEY_USER + "=" + userName + "&" +
                KEY_TEXT + "=" + text;

        if(DBG) Log.d(TAG, "send data : " + postData);

        return postData;

    }

    /** 送信データをJson形式に変換 **/
    private static String makeJson(long time, String userName, String text) {

        if(time == 0 || userName.equals("") || text.equals("")) {
            return "";
        }

        String jsonStr = "{" +
                "\"" + KEY_TIME + "\", \"" + timeStringFormat(time) + "\", " +
                "\"" + KEY_USER + "\", \"" + userName + "\", " +
                "\"" + KEY_TEXT + "\", \"" + text + "\", " +
                "}";

        if(DBG) Log.d(TAG, "send data : " + jsonStr);

        return jsonStr;

    }

    /** 時間をlong型からString型に変換 **/
    private static String timeStringFormat (long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.YEAR) + "/" +
                calendar.get(Calendar.MONTH) + "/" +
                calendar.get(Calendar.DATE) + " " +
                String.format("%02d:%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }


}
