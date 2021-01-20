package com.example.audientesprojekt.Database;
import android.app.DownloadManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.audientesprojekt.R;
import com.example.audientesprojekt.DownloadFragment;
import static android.os.Environment.DIRECTORY_DOCUMENTS;

import java.io.IOException;
import java.util.ArrayList;

public class MyFirebaseAdapter extends RecyclerView.Adapter<MyFirebaseAdapter.MyViewHolder> {

    private ArrayList<SoundBits> soundsBitsList;
    private MediaPlayer mediaPlayer = null;
    private Button oldHolder = null;


    public MyFirebaseAdapter(ArrayList<SoundBits> sound_bitsArrayList) {
        this.soundsBitsList = sound_bitsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.firebase_recycler, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final SoundBits soundBits = soundsBitsList.get(position);

        holder.sampleTitle.setText(soundBits.getTitle());

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    playSound(holder, soundBits);
            }
        });

        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                /*
                downloadFile(holder.sampleTitle.getContext(),
                        soundsBitsList.get(position).getTitle(),
                        ".flac", DIRECTORY_DOWNLOADS, soundsBitsList.get(position).getSongUrl());

                 */
            }
        });

    }

    public void playSound(final MyViewHolder holder, SoundBits soundBits) {

        if (oldHolder != null && oldHolder != holder.playButton ){
            oldHolder.setText("Play");
            if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }


        oldHolder = holder.playButton;

        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        if(mediaPlayer.isPlaying()) {
           holder.playButton.setText("Play");
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        } else {
            try {
                mediaPlayer.setDataSource(soundBits.getSongUrl());
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                            holder.playButton.setText("Stop");
                            mediaPlayer.start();


                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        holder.playButton.setText("Play");
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager =(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadManager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return soundsBitsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sampleTitle;
        Button playButton, downloadButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sampleTitle = itemView.findViewById(R.id.sampleTitle);
            downloadButton = itemView.findViewById(R.id.downloadButton);
            playButton = itemView.findViewById(R.id.playButton);
        }
    }
}
