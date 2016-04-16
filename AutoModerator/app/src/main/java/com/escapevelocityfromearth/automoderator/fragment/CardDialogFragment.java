package com.escapevelocityfromearth.automoderator.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.escapevelocityfromearth.automoderator.R;

import java.util.ArrayList;

/**
 * Created by kojira on 16/04/16.
 */
public class CardDialogFragment extends DialogFragment {

    /*
    ViewPager pager;
    CardPagerAdapter adapter;

    public static CardDialogFragment newInstance() {
        return new CardDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {

        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_fragment);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setCancelable(false);
        return dialog;

    }

    @Override
    //public Dialog onCreateDialog(Bundle bundle) {
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {

        super.onCreateView(inflater, parent, bundle);

        View dialog = inflater.inflate(R.layout.dialog_fragment, null);

        pager = (ViewPager) dialog.findViewById(R.id.init_view);

        adapter = new CardPagerAdapter(getChildFragmentManager(), getActivity());
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

        return dialog;

    }

    public void addGoalFragment() {
        adapter.addFragment(new GoalCardFragment());
        pager.setCurrentItem(pager.getCurrentItem());
        //TODO ServiceにFacilitatorのメッセージを投げる
    }

    public void ngGoalFragment() {

    }

    public void okGoalFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    */
}
