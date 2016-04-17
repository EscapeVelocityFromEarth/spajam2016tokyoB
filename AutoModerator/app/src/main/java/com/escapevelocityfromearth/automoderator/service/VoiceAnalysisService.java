package com.escapevelocityfromearth.automoderator.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Toast;

import com.escapevelocityfromearth.automoderator.util.Const;
import com.escapevelocityfromearth.automoderator.util.L;
import com.escapevelocityfromearth.automoderator.util.MessageSender;
import com.escapevelocityfromearth.automoderator.util.Prefs;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class VoiceAnalysisService extends Service {

    private SpeechRecognizer mSpeechRecognizer;
    private MessageSender sender;

    /** カウントダウン機能 **/
    private Timer mTimer;
    private CountDownTask mCountDownTask;
    private Handler mHandler = new Handler();

    ArrayList<OnCountListener> listeners;

    public interface OnCountListener {
        public void onCount();
    }

    public void registerListener(OnCountListener listener) {
        this.listeners.add(listener);
    }

    public void unregisterListener(OnCountListener listener) {
        this.listeners.remove(listener);
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public VoiceAnalysisService getService() {
            return VoiceAnalysisService.this;
        }
    }

    public VoiceAnalysisService() {
    }

    @Override
    public void onCreate() {
        L.outputMethodName();

        startListener();
        sender = new MessageSender(this);
        if(Prefs.loadUserName(this).equals(Const.DEFAULT_USER)) {
            sender.sendMessage(System.currentTimeMillis(), Const.CREATE_RECORD_USER, Const.CREATE_RECORD_START);
        }

        if (mTimer != null) {
            mTimer.cancel();
        }
        startStopWatch();

        listeners = new ArrayList<OnCountListener>();
    }

    private void startListener() {
        L.outputMethodName();
        if (mSpeechRecognizer == null) {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mSpeechRecognizer.setRecognitionListener(mRecognitionListener);
        }

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        mSpeechRecognizer.startListening(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.outputMethodName();
        mSpeechRecognizer.cancel();
        mSpeechRecognizer.destroy();
        if(Prefs.loadUserName(this).equals(Const.DEFAULT_USER)) {
            sender.sendMessage(System.currentTimeMillis(), Const.CREATE_RECORD_USER, Const.CREATE_RECORD_END);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // ここに音声認識のテキストデータが入ります。
    public void sendTextData(String sendData) {
        sender.sendMessage(sendData);
    }

    public void sendTextData(String user, String data) {
        sender.sendMessage(System.currentTimeMillis(), user, data);
    }

    private RecognitionListener mRecognitionListener = new RecognitionListener() {

        private Boolean mIsListening = false;

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            L.outputMethodName();
        }

        @Override
        public void onBeginningOfSpeech() {
            L.outputMethodName();

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            L.outputMethodName();

        }

        @Override
        public void onEndOfSpeech() {
            L.outputMethodName();

        }

        @Override
        public void onError(int i) {
            L.outputMethodName();
            L.d("Error id = " + i);
            switch (i) {
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    mSpeechRecognizer.stopListening();
                    mSpeechRecognizer.cancel();
                case SpeechRecognizer.ERROR_NO_MATCH:
                    startListener();
                    // もう一度トライ
                    break;
                case SpeechRecognizer.ERROR_AUDIO:
                case SpeechRecognizer.ERROR_CLIENT:
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                case SpeechRecognizer.ERROR_NETWORK:
                case SpeechRecognizer.ERROR_SERVER:
                    // キャンセルしてもう一度
                    mSpeechRecognizer.stopListening();
                    mSpeechRecognizer.cancel();
                    mSpeechRecognizer.destroy();
                    mSpeechRecognizer = null;
                    startListener();
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    // 設定確認必須
                    Toast.makeText(getApplicationContext(),"ネット接続もしくはパーミッションを確認して下さい",Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onResults(Bundle bundle) {
            L.outputMethodName();

            ArrayList<String> arrayList = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            L.d("get Recognize data = " + arrayList.get(0));

            sendTextData(arrayList.get(0));
            startListener();
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            L.outputMethodName();

        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            L.outputMethodName();

        }

    };


    private class CountDownTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    count();
                }
            });
        }
    }

    private void startStopWatch() {
        mTimer = new Timer();
        mCountDownTask = new CountDownTask();
        mTimer.schedule(mCountDownTask, 1000, 1000);
    }


    private void count() {
        if(listeners.size() > 0) {
            for(OnCountListener listener : listeners) {
                listener.onCount();
            }
        }
    }
}
