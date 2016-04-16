package com.escapevelocityfromearth.automoderator.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.escapevelocityfromearth.automoderator.util.Const;

public class VoiceAnalysisService extends Service {

    private static final boolean DBG = Const.DBG;
    private static final String TAG = "VoiceAnalysisService";

    public VoiceAnalysisService() {
    }

    @Override
    public void onCreate() {
        if(DBG) Log.d(TAG, "service onCreate");
    }

    @Override
    public void onDestroy() {
        if(DBG) Log.d(TAG, "service onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
