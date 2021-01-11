package com.example.audientesprojekt;

import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.media.MediaPlayer;

import java.io.DataInputStream;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class musicplayerFragment extends Fragment implements View.OnClickListener {

    ImageView placeholderCover;
    SeekBar seekBar;
    Button choosePreset;
    TextView titleOfPreset;
    ImageView pausePlayBtn;
    ImageView forwardBtn;
    ImageView backwardsBtn;
    ImageView sleepTimerBtn;
    ImageView repeatBtn;
    MediaPlayer mediaPlayer;


    Runnable runnable;
    Handler handler;

    // TODO: 05-01-21  lav imageviews om til knapper så du kan pause, spolle frem og kører på repeat
    // TODO: 05-01-21 lav en menu til sleep timer, der popper op når man trykker på den, ligesom mofibos version 
    // TODO: 07-01-21 Skal lave en async thread der kan afpsille musikken i baggrunden


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_player, container, false);

        //getactivty() skal måske ændres
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.crickets);
        handler = new Handler();


        // TODO: 06-01-21 Laver en midlertidig afspiller med en fil, vil bruger library funktionen senere


        placeholderCover = (ImageView) v.findViewById(R.id.cover_Placeholder);
        seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        choosePreset = (Button) v.findViewById(R.id.changeSong);
        pausePlayBtn = (ImageView) v.findViewById(R.id.pause_Play_Button);
        forwardBtn = (ImageView) v.findViewById(R.id.forward_Button);
        backwardsBtn = (ImageView) v.findViewById(R.id.backwards_Button);
        sleepTimerBtn = (ImageView) v.findViewById(R.id.sleepTimerButton);
        repeatBtn = (ImageView) v.findViewById(R.id.repeatButton);

        choosePreset.setOnClickListener(this);
        pausePlayBtn.setOnClickListener(this);
        forwardBtn.setOnClickListener(this);
        backwardsBtn.setOnClickListener(this);
        sleepTimerBtn.setOnClickListener(this);
        repeatBtn.setOnClickListener(this);




        // TODO: 06-01-21 Implementer måske choose song, der tager ind hen til ens library, og derefter en sharedpreference manager, der husker ens sidste valg mellem sessions
      /*  pausePlayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) {
                    runnable
                }


                pausePlayBtn.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                PlaySong();

            }
        });

       */

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mp.getDuration());
                mp.start();
                updateSeekbar();
            }

            });


            forwardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            backwardsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            repeatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            sleepTimerBtn.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                }
            });


            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                        seekBar.setProgress(progress);
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }


            });


            return v;

        }




    public void updateSeekbar() {
        int currPos = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(currPos);

        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    updateSeekbar();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.pause_Play_Button:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    pausePlayBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                } else {
                    mediaPlayer.start();
                    pausePlayBtn.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    updateSeekbar();
                }
                break;
            case R.id.forward_Button:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 15000);
                break;
            case R.id.backwards_Button:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 15000);
                break;
            case R.id.repeatButton:

                break;
            case R.id.sleepTimerButton:

                break;




        }
    }
        }









