package com.escapevelocityfromearth.automoderator.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.service.VoiceAnalysisService;
import com.escapevelocityfromearth.automoderator.util.Const;
import com.escapevelocityfromearth.automoderator.util.Prefs;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private final int REQUEST_PERMISSION = 1;

    TextView text;
    Button startButton;
    Button stopButton;
    Spinner userTypeSpinner;
    Button go;

    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!(checkSelfPermission(Manifest.permission.RECORD_AUDIO) ==
                    PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSION);
            }
        }


        text = (TextView) findViewById(R.id.text);

        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(this);

        stopButton = (Button) findViewById(R.id.stop);
        stopButton.setOnClickListener(this);

        userTypeSpinner = (Spinner) findViewById(R.id.spinner_user_type);
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userName = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        go = (Button) findViewById(R.id.button_next);
        go.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (userTypeSpinner.getSelectedItemPosition() == 0) {
                    intent = new Intent(MainActivity.this, AdminActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, MemberActivity.class);
                }
                startActivity(intent);
            }
        });

        Prefs.saveUserName(this, Const.DEFAULT_USER);
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
                if(!userName.equals("")) {
                    Prefs.saveUserName(this, userName);
                }
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
        Intent nextIntent = new Intent(this, CardDialogActivity.class);
        startActivity(nextIntent);
    }

    private void stopService() {
        Intent i = new Intent(this, VoiceAnalysisService.class);
        stopService(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permssions[], int[] grentResults) {
        super.onRequestPermissionsResult(requestCode, permssions, grentResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return true;
    }


}
