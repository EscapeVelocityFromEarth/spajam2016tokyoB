package com.escapevelocityfromearth.automoderator.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by mitake on 16/04/17.
 */
public class CardPagerAdapter extends FragmentPagerAdapter{

    private Context context;
    private ArrayList<Fragment> fragments;

    public CardPagerAdapter(FragmentManager manager, Context context) {
        super(manager);
        this.context = context;
        fragments = new ArrayList<Fragment>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragment(Fragment fragment, int index) {
        fragments.add(index, fragment);
        notifyDataSetChanged();
    }

    public void removeFragment(int index) {
        fragments.remove(index);
        notifyDataSetChanged();
    }

}
