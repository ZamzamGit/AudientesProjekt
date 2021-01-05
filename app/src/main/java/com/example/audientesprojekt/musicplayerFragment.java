package com.example.audientesprojekt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class musicplayerFragment extends Fragment {

    ImageView placeholderCover;
    SeekBar seekBar;
    Button choosePreset;
    ImageView pausePlayBtn;
    ImageView forwardBtn;
    ImageView backwardsBtn;
    ImageView sleepTimerBtn;
    ImageView repeatBtn;


    // TODO: 05-01-21  lav imageviews om til knapper så du kan pause, spolle frem og kører på repeat
    // TODO: 05-01-21 lav en menu til sleep timer, der popper op når man trykker på den, ligesom mofibos version 





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_player, container,false);

        placeholderCover = (ImageView) v.findViewById(R.id.cover_Placeholder);
        seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        choosePreset = (Button) v.findViewById(R.id.changeSong);
        pausePlayBtn = (ImageView) v.findViewById(R.id.pause_Play_Button);
        forwardBtn = (ImageView) v.findViewById(R.id.forward_Button);
        backwardsBtn = (ImageView) v.findViewById(R.id.backwards_Button);
        sleepTimerBtn = (ImageView) v.findViewById(R.id.sleepTimerButton);
        repeatBtn = (ImageView) v.findViewById(R.id.repeatButton);


        return v;


        seekBar.setOnSeekBarChangeListener(this);
    }





}