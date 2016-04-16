package com.escapevelocityfromearth.automoderator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.service.VoiceAnalysisService;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    TextView text;
    Button startButton;
    Button stopButton;
    Spinner userTypeSpinner;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);

        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(this);

        stopButton = (Button) findViewById(R.id.stop);
        stopButton.setOnClickListener(this);

        userTypeSpinner = (Spinner) findViewById(R.id.spinner_user_type);

        go = (Button) findViewById(R.id.button_next);
        go.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (userTypeSpinner.getSelectedItemPosition()==0) {
                    intent = new Intent(MainActivity.this, AdminActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, MemberActivity.class);
                }
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                startService();
                break;
            case R.id.stop:
                stopService();
                break;
            default:
                break;
        }
    }

    private void startService() {
        Intent i = new Intent(this, VoiceAnalysisService.class);
        startService(i);
    }

    private void stopService() {
        Intent i = new Intent(this, VoiceAnalysisService.class);
        stopService(i);
    }


}
