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
import android.widget.TextView;

import com.escapevelocityfromearth.automoderator.R;
import com.escapevelocityfromearth.automoderator.activity.CardDialogActivity;

/**
 * Created by mitake on 16/04/17.
 */
public class MTGCardFragment extends Fragment {

    TextView mTextView;
    Button button;
    Bitmap back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mtg_card, container, false);

        mTextView = (TextView) view.findViewById(R.id.count_text);

        button = (Button) view.findViewById(R.id.start_check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CardDialogActivity) getActivity()).addGoalFragment();
            }
        });

        back = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.img03_bg);
        view.setBackground(new BitmapDrawable(getActivity().getResources(), back));

        return view;
    }

    @Override
    public void onDetach() {
        android.util.Log.d("test", "call mtg card fragment onDetach");
        super.onDetach();
        back.recycle();
        back = null;
    }


    public void setTimeText(int countTime) {
        if(countTime > 0) {
            int minute = countTime / 60;
            int second = countTime % 60;
            mTextView.setText(String.format("%02d:%02d", minute, second));
        } else if (countTime == 0) {

        }
    }
}
