package com.escapevelocityfromearth.automoderator.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
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

    View view;
    TextView mTextView;
    Button button;
    Handler mHandler;

    Bitmap back;
    Bitmap back2;
    Bitmap back3;
    Bitmap back4;
    Bitmap back5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mtg_card, container, false);

        mTextView = (TextView) view.findViewById(R.id.count_text);
        mTextView.setVisibility(View.INVISIBLE);

        button = (Button) view.findViewById(R.id.start_check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CardDialogActivity) getActivity()).addGoalFragment();

                ((CardDialogActivity) getActivity()).startSound(R.raw.genpei_se_06, false);
            }
        });
        button.setVisibility(View.GONE);

        back = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.img04_bg);
        view.setBackground(new BitmapDrawable(getActivity().getResources(), back));

        mHandler = new Handler();
        mHandler.postDelayed(bgTask, 1500);

        ((CardDialogActivity) getActivity()).startSound(R.raw.genpei_music_02, false);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mHandler.removeCallbacksAndMessages(null);
    }

    Runnable bgTask = new Runnable() {
        @Override
        public void run() {
            back2 = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.img05_bg);
            back.recycle();
            back = null;
            view.setBackground(new BitmapDrawable(getActivity().getResources(), back2));
            ((CardDialogActivity) getActivity()).startSound(R.raw.genpei_music_02, false);
            mHandler.postDelayed(bgTask2, 1500);
        }
    };

    Runnable bgTask2 = new Runnable() {
        @Override
        public void run() {
            back3 = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.img06_bg);
            view.setBackground(new BitmapDrawable(getActivity().getResources(), back3));
            back2.recycle();
            back2 = null;
            ((CardDialogActivity) getActivity()).startSound(R.raw.genpei_music_02, false);
            mHandler.postDelayed(bgTask3, 1500);
        }
    };

    Runnable bgTask3 = new Runnable() {
        @Override
        public void run() {
            back4 = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.img07_bg);
            view.setBackground(new BitmapDrawable(getActivity().getResources(), back4));
            back3.recycle();
            back3 = null;
            mTextView.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    };


    public void setTimeText(int countTime) {
        if(countTime >= 0) {
            int minute = countTime / 60;
            int second = countTime % 60;
            mTextView.setText(String.format("%02d:%02d", minute, second));
            if(minute == 0 && second == 30) {
                ((CardDialogActivity) getActivity()).startSound(R.raw.genpei_music_03, false);
            }
        }

        if (countTime == 0) {
            mTextView.setVisibility(View.INVISIBLE);
            back5 = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.img072_bg);
            view.setBackground(new BitmapDrawable(getActivity().getResources(), back5));
            back4.recycle();
            back4 = null;
            ((CardDialogActivity) getActivity()).startSound(R.raw.genpei_music_04, false);
        }
    }
}
