package com.example.audientesprojekt;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.TimeUnit;

public class TimerFragment extends Fragment implements View.OnClickListener, ExampleBottomDialog.OnInputSelected {
    private Button open;
    private MediaPlayer mp;
    private Handler handler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sleep_timer, container, false);
        open = v.findViewById(R.id.button);
        open.setOnClickListener(this);
        handler = new Handler();
        return v;
    }

    @Override
    public void onClick(View v) {
        ExampleBottomDialog bottomDialog = new ExampleBottomDialog();
        bottomDialog.setTargetFragment(this, 1);
        bottomDialog.show(getActivity().getSupportFragmentManager(), "exampleDialog");

    }

    @Override
    public void sendInput(String input) {
        switch (input){
            case "15 minutter":
                handler.postDelayed(stopPlayerTask, TimeUnit.SECONDS.toMillis(2));
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
            mp.pause();
        }
    };
}
