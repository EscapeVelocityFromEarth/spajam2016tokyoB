package com.escapevelocityfromearth.automoderator.fragment;

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
public class GoalCardFragment extends Fragment {

    Button ngButton;
    Button okButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_goal_card, container, false);

        ngButton = (Button) view.findViewById(R.id.not_check_goal);
        ngButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CardDialogActivity) getActivity()).ngGoalFragment();
            }
        });

        okButton = (Button) view.findViewById(R.id.check_goal);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CardDialogActivity) getActivity()).addItemFragment();
            }
        });

        return view;
    }
}