package com.escapevelocityfromearth.automoderator.activity;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.fragment.CardDialogFragment;
import com.escapevelocityfromearth.automoderator.fragment.CardPagerAdapter;
import com.escapevelocityfromearth.automoderator.fragment.FinishCardFragment;
import com.escapevelocityfromearth.automoderator.fragment.GoalCardFragment;
import com.escapevelocityfromearth.automoderator.fragment.ItemCardFragment;
import com.escapevelocityfromearth.automoderator.fragment.MTGCardFragment;
import com.escapevelocityfromearth.automoderator.fragment.ReGoalCardFragment;
import com.escapevelocityfromearth.automoderator.fragment.ScheduleCardFragment;

public class CardDialogActivity extends AppCompatActivity {

    ViewPager pager;
    CardPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    public void addGoalFragment() {
        adapter.addFragment(new GoalCardFragment());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        //TODO ServiceにFacilitatorのメッセージを投げる
    }

    public void ngGoalFragment() {
        adapter.addFragment(new ReGoalCardFragment());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        //TODO ServiceにFacilitatorのメッセージを投げる
    }

    public void addItemFragment() {
        adapter.addFragment(new ItemCardFragment());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        //TODO ServiceにFacilitatorのメッセージを投げる
    }

    public void addScheduleFragment() {
        adapter.addFragment(new ScheduleCardFragment());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        //TODO ServiceにFacilitatorのメッセージを投げる
    }

    public void addFinishFragment() {
        adapter.addFragment(new FinishCardFragment());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        //TODO ServiceにFacilitatorのメッセージを投げる
    }

}
