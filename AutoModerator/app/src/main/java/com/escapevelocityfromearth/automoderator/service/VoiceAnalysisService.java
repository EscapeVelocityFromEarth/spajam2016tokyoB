package com.escapevelocityfromearth.automoderator.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.escapevelocityfromearth.automoderator.util.L;
import com.escapevelocityfromearth.automoderator.util.MessageSender;

import java.util.ArrayList;

public class VoiceAnalysisService extends Service {

    private SpeechRecognizer mSpeechRecognizer;
    private MessageSender sender;

    public VoiceAnalysisService() {
    }

    @Override
    public void onCreate() {
        L.outputMethodName();

        if (mSpeechRecognizer == null) {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mSpeechRecognizer.setRecognitionListener(mRecognitionListener);
        }

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        mSpeechRecognizer.startListening(intent);

        sender = new MessageSender(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.outputMethodName();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // ここに音声認識のテキストデータが入ります。
    private void sendTextData(String sendData) {
        sender.sendMessage(sendData);
    }

    private RecognitionListener mRecognitionListener = new RecognitionListener() {

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
                case SpeechRecognizer.ERROR_NO_MATCH:
                    // TODO もう一度トライ
                    break;
                case SpeechRecognizer.ERROR_AUDIO:
                case SpeechRecognizer.ERROR_CLIENT:
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    // TODO キャンセルしてもう一度
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                case SpeechRecognizer.ERROR_SERVER:
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    // TODO 設定確認必須
                    break;
            }
        }

        @Override
        public void onResults(Bundle bundle) {
            L.outputMethodName();

            ArrayList<String> arrayList = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            L.d("get Recognize data = " + arrayList.get(0));

            sendTextData(arrayList.get(0));
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

}
