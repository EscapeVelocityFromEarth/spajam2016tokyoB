package com.escapevelocityfromearth.automoderator.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.escapevelocityfromearth.automoderator.activity.SettingsActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MeasureTimeService extends Service {

    private Intent mIntent;
    private Timer mTimer;
    private CountDownTask mCountDownTask;
    private Handler mHander = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mTimer != null) {
            mTimer.cancel();
        }
        startStopWatch();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
   private class CountDownTask extends TimerTask {

        @Override
        public void run() {
            mHander.post(new Runnable() {
                @Override
                public void run() {
                    sendCountDownBroadCast();
                }
            });
        }
    }

    private void startStopWatch() {
        mTimer = new Timer();
        mCountDownTask = new CountDownTask();
        mTimer.schedule(mCountDownTask, 1000, 1000);
    }

    private void sendCountDownBroadCast() {
        if (mIntent == null) {
            mIntent = new Intent();
            mIntent.setAction(SettingsActivity.ACTION_STOPWATCH_COUNT);
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcastSync(mIntent);
    }
}
