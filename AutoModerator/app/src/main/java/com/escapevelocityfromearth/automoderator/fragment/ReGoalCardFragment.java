package com.escapevelocityfromearth.automoderator.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.activity.CardDialogActivity;

/**
 * Created by mitake on 16/04/17.
 */
public class ReGoalCardFragment extends Fragment {

    Button button;
    Bitmap back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regoal_card, container, false);

        button = (Button) view.findViewById(R.id.re_check_goal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CardDialogActivity) getActivity()).addItemFragment();
                ((CardDialogActivity) getActivity()).startSound(R.raw.genpei_se_08, false);
            }
        });

        back = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.img09_bg);
        view.setBackground(new BitmapDrawable(getActivity().getResources(), back));


        ((CardDialogActivity) getActivity()).startSound(R.raw.genpei_voice_22, false);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        back.recycle();
        back = null;
    }
}
