package com.escapevelocityfromearth.automoderator.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Handler;
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
import com.escapevelocityfromearth.automoderator.util.Prefs;

public class CardDialogActivity extends AppCompatActivity {

    ViewPager pager;
    CardPagerAdapter adapter;

    MediaPlayer mp;

    Handler mHandler;
    int countTime = 0;
    MTGCardFragment mtgFragment;

    private VoiceAnalysisService mService;

    VoiceAnalysisService.OnCountListener listener = new VoiceAnalysisService.OnCountListener() {
        @Override
        public void onCount() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    countTime--;
                    setTimeText();
                }
            }, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_card_dialog);

        //CardDialogFragment dialog = new CardDialogFragment();
        //dialog.show(getSupportFragmentManager(), dialog.getClass().getName());

        pager = (ViewPager) findViewById(R.id.init_view);

        adapter = new CardPagerAdapter(getSupportFragmentManager(), this);

        mtgFragment = new MTGCardFragment();
        adapter.addFragment(mtgFragment);

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

        mHandler = new Handler();
        countTime = Prefs.loadCountTime(this) * 60;

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
        if(mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
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
        mService.sendTextData(Const.CREATE_RECORD_USER, Const.CREATE_RECORD_END);
        mService.sendTextData(Const.CREATE_RECORD_USER, Const.FINISH_TEXT);
    }


    private void startService() {
        Intent i = new Intent(this, VoiceAnalysisService.class);
        bindService(i, connection, Context.BIND_AUTO_CREATE);
    }

    public void stopService() {
        mService.unregisterListener(listener);
        Intent i = new Intent(this, VoiceAnalysisService.class);
//        unbindService(connection);
        stopService(i);
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            VoiceAnalysisService.LocalBinder binder = (VoiceAnalysisService.LocalBinder)service;
            mService = binder.getService();
            mService.registerListener(listener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


    private void setTimeText() {
        if(mtgFragment != null) {
            mtgFragment.setTimeText(countTime);
        }
    }

    public void startSound(int resId, boolean loop) {

        android.util.Log.d("test", "sound:" + getResources().getResourceName(resId));

        if(resId == 0) {
            return;
        }

        //現在はアプリのrawフォルダからの取得のみを想定
        if(mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }

        mp = MediaPlayer.create(this, resId);
        mp.setLooping(loop);
        if (mp.isPlaying()) { //再生中
            mp.stop();
            try {
                mp.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { //停止中
            mp.start();
        }
    }


}
