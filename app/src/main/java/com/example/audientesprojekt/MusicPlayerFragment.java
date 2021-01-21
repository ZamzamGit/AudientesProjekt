
/*
* Vi har benyttet os af følgende guide list til at lave denne afspiller:
*
* https://www.youtube.com/watch?v=-srGQk57pBU&list=RDCMUCR1t5eSmLxLUdBnK2XwZOuw&start_radio=1&t=5
*
*
* */


package com.example.audientesprojekt;

import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.audientesprojekt.Services.OnClearFromService;
import com.example.audientesprojekt.librarylogic.LibraryFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.sentry.Sentry;


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
    private Handler handler = new Handler();
    private Bundle bundle;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_player, container, false);

        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
        myMediaPlayer = new MediaPlayer();
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
        choosePreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryFragment libraryFragment = new LibraryFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, libraryFragment)
                        .addToBackStack(null)
                        .commit();
                if (myMediaPlayer != null){
                    myMediaPlayer.reset();
                    myMediaPlayer = null;
                }
            }
        });
        pausePlayBtn.setOnClickListener(this);
        forwardBtn.setOnClickListener(this);
        backwardsBtn.setOnClickListener(this);
        sleepTimerBtn.setOnClickListener(this);
        repeatBtn.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            creatChannel();
        }
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("PLAY_SONG"));
        getActivity().startService(new Intent(getActivity().getBaseContext(), OnClearFromService.class));


        bundle = getArguments();
        if (bundle != null) {
            mySongs = bundle.getParcelableArrayList("sounds");
            position = bundle.getInt("position");
            initPlayer(position);
        }


        return v;
    }

    @Override
    public void onClick(View v){
        if (bundle != null) {
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
                case R.id.sleepTimerButton:
                    ExampleBottomDialog bottomDialog = new ExampleBottomDialog();
                    bottomDialog.setTargetFragment(this, 1);
                    bottomDialog.show(getFragmentManager(),"exampleDialog");
                    break;
            }
        } else {
            Toast.makeText(getActivity(), "Please choose a sound", Toast.LENGTH_SHORT).show();
        }
    }

    public void initPlayer(int position) {
        if (myMediaPlayer != null && myMediaPlayer.isPlaying()) {
            myMediaPlayer.reset();
        }
        String songName = mySongs.get(position).getFileName();
        titleOfPreset.setText(songName);

        uri = mySongs.get(position).getUri();
        myMediaPlayer = new MediaPlayer();
        try {
            myMediaPlayer.setDataSource(getActivity(), uri);
            myMediaPlayer.setOnPreparedListener(this);
            myMediaPlayer.prepareAsync();
            myMediaPlayer.setOnCompletionListener(this);
            seekBar.setOnSeekBarChangeListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        seekBar.setMax(myMediaPlayer.getDuration());

        String totalTimer = createTime(myMediaPlayer.getDuration());
        totalTime.setText(totalTimer);
        myMediaPlayer.start();
        pausePlayBtn.setImageResource(R.drawable.ic_baseline_pause_24);
        noti.creatNotification(getActivity(), mySongs, R.drawable.ic_baseline_pause_24, position);
        updateSeekbar();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNextSong();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            myMediaPlayer.seekTo(progress);
            seekBar.setProgress(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

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
        updateSeekbar();
    }

    public void updateSeekbar() {
        if (myMediaPlayer !=null ){

            int currPos = myMediaPlayer.getCurrentPosition();
            seekBar.setProgress(currPos);
            startTime.setText(createTime(currPos));

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

        noti.creatNotification(getActivity(), mySongs, R.drawable.ic_baseline_play_arrow_24, position);
        initPlayer(position);

        if (myMediaPlayer.isPlaying()){
            pausePlayBtn.setImageResource(R.drawable.ic_baseline_pause_24);
        } else {
            pausePlayBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        }
    }



    public String createTime(int duration) {
        String timerLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        timerLabel += min + ":";
        if (sec < 10) {
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
                case NotificationExample.ACTION_PLAY:
                case NotificationExample.ACTION_NEXT:
                    //play();
                    //playPrevSong();
                    Toast.makeText(getActivity(), "Under development", Toast.LENGTH_SHORT).show();
                    break;
               /* case NotificationExample.ACTION_NEXT:
                    //playNextSong();
                    Toast.makeText(getActivity(), "Underdevelopment", Toast.LENGTH_SHORT).show();
                    break;*/
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (myMediaPlayer != null) {
            myMediaPlayer.pause();
        }
    }
}
