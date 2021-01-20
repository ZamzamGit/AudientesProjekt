package com.example.audientesprojekt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.media.MediaPlayer;

import com.example.audientesprojekt.librarylogic.LibraryFile;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MusicPlayerFragment extends Fragment implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener, ExampleBottomDialog.OnInputSelected {

    private ImageView placeholderCover;
    private SeekBar seekBar;
    private Button choosePreset;
    private TextView titleOfPreset, totalTime, startTime;
    private ImageView pausePlayBtn;
    private ImageView forwardBtn;
    private ImageView backwardsBtn;
    private ImageView sleepTimerBtn;
    private ImageView repeatBtn;
    private MediaPlayer myMediaPlayer;
    private int position;
    private NotificationExample noti = new NotificationExample();
    private Uri uri;
    private ArrayList<LibraryFile> mySongs;
    private NotificationManager notificationManager;
    private Runnable runnable;
    private Handler handler;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_player, container, false);

        myMediaPlayer = new MediaPlayer();
        myMediaPlayer = MediaPlayer.create(getActivity(), R.raw.crickets);
        handler = new Handler();

        Bundle bundle = getArguments();

        if(bundle != null) {
            mySongs = bundle.getParcelableArrayList("sounds");
            position = bundle.getInt("position");
        }

        placeholderCover = (ImageView) v.findViewById(R.id.cover_Placeholder);;
        totalTime = v.findViewById(R.id.totalTime);
        startTime = v.findViewById(R.id.startTime);
        titleOfPreset = v.findViewById(R.id.title_of_preset);
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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            creatChannel();
        }
        requireActivity().registerReceiver(broadcastReceiver, new IntentFilter("SONG"));
        getActivity().startService(new Intent(getActivity().getBaseContext(), OnClearFromService.class));

        if(myMediaPlayer != null){
            myMediaPlayer.stop();
        }
        return v;

        }

    public void initPlayer(int position) {
        if (myMediaPlayer != null && myMediaPlayer.isPlaying()) {
            myMediaPlayer.reset();
        }
        //uri = Uri.parse(mySongs.get(position).toString());
        uri = mySongs.get(position).getUri();
        myMediaPlayer = MediaPlayer.create(getActivity(), uri);
        myMediaPlayer.setOnPreparedListener(this);
        myMediaPlayer.prepareAsync();
        myMediaPlayer.setOnCompletionListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        seekBar.setMax(myMediaPlayer.getDuration());

        String totalTimer = createTime(myMediaPlayer.getDuration());
        totalTime.setText(totalTimer);

        pausePlayBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        updateSeekbar();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        int currPosition = ((position+1) % mySongs.size());
        initPlayer(currPosition);
        noti.creatNotification(getActivity(), mySongs, R.drawable.ic_baseline_play_arrow_24, currPosition);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            myMediaPlayer.seekTo(progress);
            seekBar.setProgress(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void play(){
        if (myMediaPlayer != null && myMediaPlayer.isPlaying()){
            myMediaPlayer.pause();
            noti.creatNotification(getActivity(), mySongs, R.drawable.ic_baseline_play_arrow_24, position);
            pausePlayBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        } else {
            myMediaPlayer.start();
            noti.creatNotification(getActivity(), mySongs, R.drawable.ic_baseline_pause_24, position);
            pausePlayBtn.setImageResource(R.drawable.ic_baseline_pause_24);
        }
    }

    public void playNextSong(){
        myMediaPlayer.reset();

        position = ((position+1) % mySongs.size());
        initPlayer(position);
        if (myMediaPlayer.isPlaying()){
            pausePlayBtn.setImageResource(R.drawable.ic_baseline_pause_24);
        } else {
            pausePlayBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        }
        noti.creatNotification(getActivity(), mySongs, R.drawable.ic_baseline_play_arrow_24, position);
    }

    public void resetSong(){
        myMediaPlayer.reset();
        initPlayer(position);
    }

    public void playPrevSong(){
        myMediaPlayer.reset();

        if (position <= 0){
            position = mySongs.size()-1;
        } else {
            position--;
        }

        initPlayer(position);

        if (myMediaPlayer.isPlaying()){
            pausePlayBtn.setImageResource(R.drawable.ic_baseline_pause_24);
        } else {
            pausePlayBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        }

        noti.creatNotification(getActivity(), mySongs, R.drawable.ic_baseline_play_arrow_24, position);
    }


    public void updateSeekbar() {
        int currPos = myMediaPlayer.getCurrentPosition();
        seekBar.setProgress(currPos);

        if (myMediaPlayer.isPlaying()) {
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
                play();
                updateSeekbar();
                break;
            case R.id.forward_Button:
                playNextSong();
                //updateSeekbar();
                break;
            case R.id.backwards_Button:
                playPrevSong();
                //updateSeekbar();
                break;
            case R.id.repeatButton:
                resetSong();
                break;
            case R.id.changeSong:
                LibraryFragment libraryFragment = new LibraryFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, libraryFragment)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    public String createTime(int duration){
        String timerLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        timerLabel += min + ":";

        if (sec < 10){
            timerLabel += "0";
        }

        timerLabel += sec;

        return timerLabel;
    }

    public void creatChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(noti.getChannelId(),
                    "noti", NotificationManager.IMPORTANCE_LOW);
            notificationManager = getActivity().getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");

            switch (action){
                case NotificationExample.ACTION_PREV:
                    playPrevSong();
                    break;
                case NotificationExample.ACTION_PLAY:
                    play();
                    break;
                case NotificationExample.ACTION_NEXT:
                    playNextSong();
                    break;
            }
        }
    };

    @Override
    public void sendInput(String input) {
        switch (input){
            case "15 minutter":
                handler.postDelayed(stopPlayerTask, TimeUnit.MINUTES.toMillis(15));
                break;
            case "30 minutter":
                handler.postDelayed(stopPlayerTask, TimeUnit.MINUTES.toMillis(30));
                break;
            case "45 minutter":
                handler.postDelayed(stopPlayerTask, TimeUnit.MINUTES.toMillis(45));
                break;
            default:
                long interVal = Integer.parseInt(input);
                handler.postDelayed(stopPlayerTask, TimeUnit.MINUTES.toMillis(interVal));
                break;
        }
    }

    Runnable stopPlayerTask = new Runnable() {
        @Override
        public void run() {
            myMediaPlayer.pause();
        }
    };
}









