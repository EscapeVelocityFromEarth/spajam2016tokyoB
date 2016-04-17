package com.escapevelocityfromearth.automoderator.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.util.L;
import com.escapevelocityfromearth.automoderator.util.Prefs;

public class SettingsActivity extends AppCompatActivity {

    private Spinner userTypeSpinner;

    private int mUserSelectMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userTypeSpinner = (Spinner) findViewById(R.id.spinner_user_type);
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mUserSelectMinutes = Integer.parseInt(((String)parent.getItemAtPosition(position)).replaceAll("[^0-9]", ""));
                L.d("mUserSelectMinutes = " + mUserSelectMinutes);
                Prefs.saveCountTime(SettingsActivity.this, mUserSelectMinutes);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}