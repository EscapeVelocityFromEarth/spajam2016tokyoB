package com.escapevelocityfromearth.automoderator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.service.VoiceAnalysisService;

public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_member);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(this, VoiceAnalysisService.class);
        stopService(i);
    }
}
