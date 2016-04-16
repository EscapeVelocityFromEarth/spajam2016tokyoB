package com.escapevelocityfromearth.automoderator.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.util.Prefs;

public class DebugSettingActivity extends AppCompatActivity {
    private static final String DIALOG_TAG_1 = "dialog_tag1";

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
    }


}
