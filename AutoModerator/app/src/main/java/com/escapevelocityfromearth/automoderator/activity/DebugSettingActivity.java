package com.escapevelocityfromearth.automoderator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.util.Prefs;

public class DebugSettingActivity extends AppCompatActivity {
    private static final String DIALOG_TAG_1 = "dialog_tag1";

    Spinner userTypeSpinner;
    String userName = "";

    private Spinner userTypeSpinnerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_setting);

        final EditText url = (EditText)findViewById(R.id.editServerUrl);
        url.setText(Prefs.loadServerUrl(this));
        Button apply = (Button)findViewById(R.id.button_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.saveServerUrl(DebugSettingActivity.this, url.getText().toString());
            }
        });

        userTypeSpinner = (Spinner) findViewById(R.id.spinner_user_type);
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userName = (String) parent.getItemAtPosition(position);
                if (!userName.equals("")) {
                    Prefs.saveUserName(DebugSettingActivity.this, userName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        userTypeSpinnerTime = (Spinner) findViewById(R.id.spinner_user_typeTime);
        userTypeSpinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Prefs.saveCountTime(getApplicationContext(), Integer.parseInt(((String)parent.getItemAtPosition(position)).replaceAll("[^0-9]", "")));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
