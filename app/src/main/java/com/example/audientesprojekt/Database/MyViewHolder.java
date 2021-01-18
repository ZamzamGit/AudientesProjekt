package com.example.audientesprojekt.Database;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.support.
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.audientesprojekt.R;


public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView sampleTitle;
    Button playButton,downloadButton;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        sampleTitle=itemView.findViewById(R.id.sampleTitle);
        downloadButton=itemView.findViewById(R.id.downloadButton);
        playButton=itemView.findViewById(R.id.playButton);




    }
}

