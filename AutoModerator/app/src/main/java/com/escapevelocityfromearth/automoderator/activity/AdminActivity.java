package com.escapevelocityfromearth.automoderator.activity;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.escapevelocityfromearth.automoderator.R;

public class AdminActivity extends AppCompatActivity {
    private static final String DIALOG_TAG_1 = "dialog_tag1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void showDialog(DialogFragment df, String tag) {
        if (isFinishing()) return;
        df.setCancelable(false);
        getSupportFragmentManager().beginTransaction().add(df, tag).commitAllowingStateLoss();
    }

}
