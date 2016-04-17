package com.escapevelocityfromearth.automoderator.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.fragment.CardPagerAdapter;
import com.escapevelocityfromearth.automoderator.fragment.FinishCardFragment;
import com.escapevelocityfromearth.automoderator.fragment.GoalCardFragment;
import com.escapevelocityfromearth.automoderator.fragment.ItemCardFragment;
import com.escapevelocityfromearth.automoderator.fragment.MTGCardFragment;
import com.escapevelocityfromearth.automoderator.fragment.ReGoalCardFragment;
import com.escapevelocityfromearth.automoderator.fragment.ScheduleCardFragment;
import com.escapevelocityfromearth.automoderator.service.VoiceAnalysisService;
import com.escapevelocityfromearth.automoderator.util.Const;

public class CardDialogActivity extends AppCompatActivity {

    ViewPager pager;
    CardPagerAdapter adapter;

    private VoiceAnalysisService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_card_dialog);

        //CardDialogFragment dialog = new CardDialogFragment();
        //dialog.show(getSupportFragmentManager(), dialog.getClass().getName());

        pager = (ViewPager) findViewById(R.id.init_view);

        adapter = new CardPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new MTGCardFragment());

        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                //do nothing
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        startService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService();
    }

    public void addGoalFragment() {
        adapter.addFragment(new GoalCardFragment());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        mService.sendTextData(Const.CREATE_RECORD_USER, Const.CHECK_GOAL_TEXT);
    }

    public void ngGoalFragment() {
        adapter.addFragment(new ReGoalCardFragment());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        mService.sendTextData(Const.CREATE_RECORD_USER, Const.CHECK_REGOAL_TEXT);
    }

    public void addItemFragment() {
        adapter.addFragment(new ItemCardFragment());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        mService.sendTextData(Const.CREATE_RECORD_USER, Const.CHECK_ITEM_TEXT);
    }

    public void addScheduleFragment() {
        adapter.addFragment(new ScheduleCardFragment());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        mService.sendTextData(Const.CREATE_RECORD_USER, Const.CHECK_SCHEDULE_TEXT);
    }

    public void addFinishFragment() {
        adapter.addFragment(new FinishCardFragment());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        mService.sendTextData(Const.CREATE_RECORD_USER, Const.FINISH_TEXT);
    }


    private void startService() {
        Intent i = new Intent(this, VoiceAnalysisService.class);
        bindService(i, connection, Context.BIND_AUTO_CREATE);
    }

    public void stopService() {
        Intent i = new Intent(this, VoiceAnalysisService.class);
//        unbindService(connection);
        stopService(i);
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            VoiceAnalysisService.LocalBinder binder = (VoiceAnalysisService.LocalBinder)service;
            mService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


}
