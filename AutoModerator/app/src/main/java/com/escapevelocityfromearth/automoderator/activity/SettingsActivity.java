package com.escapevelocityfromearth.automoderator.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.service.MeasureTimeService;
import com.escapevelocityfromearth.automoderator.util.L;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String ACTION_STOPWATCH_COUNT = "action_stopwatch_count";

    private BroadcastReceiver mStopWatchCountReceiver;

    private Button startButton;
    private Button stopButton;
    private Spinner userTypeSpinner;
    private TextView mTextview;

    private int mUserSelectMinutes;
    private int mTimeLimitSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mTextview = (TextView)findViewById(R.id.time_count);
        startButton = (Button) findViewById(R.id.start_measurement);
        startButton.setOnClickListener(this);
        stopButton = (Button) findViewById(R.id.finish_measurement);
        stopButton.setOnClickListener(this);

        userTypeSpinner = (Spinner) findViewById(R.id.spinner_user_type);
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mUserSelectMinutes = Integer.parseInt(((String)parent.getItemAtPosition(position)).replaceAll("[^0-9]", ""));
                L.d("mUserSelectMinutes = " + mUserSelectMinutes);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMeasureTime();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_measurement:
                startMeasureTime();
                break;
            case R.id.finish_measurement:
                stopMeasureTime();
                break;
        }
    }

    // TODO このメソッドをたたくとストップウォッチ開始
    private void startMeasureTime() {
        L.outputMethodName();
        registReceiver();
        Intent intent = new Intent(getApplicationContext(), MeasureTimeService.class);
        startService(intent);
        mTimeLimitSecond = mUserSelectMinutes * 60;
        setTimeText();
    }

    private void stopMeasureTime() {
        L.outputMethodName();
        mTimeLimitSecond = 0;
        setTimeText();
        unRegistReceiver();

        Intent intent = new Intent(getApplicationContext(), MeasureTimeService.class);
        stopService(intent);
    }

    private BroadcastReceiver getStopWatchCountReceiverInstance() {

        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent == null) {
                    return;
                }
                if (ACTION_STOPWATCH_COUNT.equals(intent.getAction())) {
                    L.traceCodePlace("mStopWatchCountReceiver");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTimeLimitSecond--;
                            if(mTimeLimitSecond < 0) {
                                stopMeasureTime();
                                finishMeasureTimeAction();
                            } else {
                                setTimeText();
                            }
                        }
                    });
                }
            }
        };
    }

    // TODO 計測終了後はこのメソッドが呼ばれる
    private void finishMeasureTimeAction() {
        L.outputMethodName();

    }

    private void setTimeText() {
        int minute = mTimeLimitSecond / 60;
        int second = mTimeLimitSecond % 60;
        mTextview.setText(String.format("%02d:%02d", minute,second));
    }

    private void registReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_STOPWATCH_COUNT);

        if(mStopWatchCountReceiver == null) {
            mStopWatchCountReceiver = getStopWatchCountReceiverInstance();
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mStopWatchCountReceiver, intentFilter);
        }
    }

    private void unRegistReceiver() {
        if (mStopWatchCountReceiver != null) {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mStopWatchCountReceiver);
            mStopWatchCountReceiver = null;
        }
    }
}