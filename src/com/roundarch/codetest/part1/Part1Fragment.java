package com.roundarch.codetest.part1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import com.roundarch.codetest.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by mdigiovanni on 8/15/13.
 */
public class Part1Fragment extends Fragment {
    // TODO - any member variables you need to store?

    Switch switch1;
    TextView difference;
    SeekBar bar1, bar2;

    //FIXME: Improve something! Anything
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part1, null);

        // TODO - obtain references to your views from the layout

        switch1 = (Switch) view.findViewById(R.id.switch1);
        bar1 = (SeekBar)  view.findViewById(R.id.seekBar2);
        bar2 = (SeekBar) view.findViewById(R.id.seekBar3);
        difference = (TextView) view.findViewById(R.id.tvDifference);

        // TODO - hook up any event listeners that make sense for the task

        updateDifference();

        initializeSeekbarListener(bar1);
        initializeSeekbarListener(bar2);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bar1.setProgress(50);
                bar2.setProgress(50);
            }
        });


        return view;
    }

    public void initializeSeekbarListener(final SeekBar bar){

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i(TAG, seekBar.toString() + progress);
                updateDifference();

                if(switch1.isChecked()){

                    syncbars(bar);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void syncbars(SeekBar bar) {

        if(bar.equals(bar1)){
            bar2.setProgress(bar1.getProgress());
        }

        else{
            bar1.setProgress(bar2.getProgress());
        }
    }

    public void updateDifference(){

        difference.setText((String.valueOf(Math.abs(bar1.getProgress() - bar2.getProgress()))));

    }

}
