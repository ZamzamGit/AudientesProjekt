package com.example.audientesprojekt;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;


public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private ArrayList<Library> filenames;
    private MediaPlayer mediaPlayer;

    public LibraryAdapter(ArrayList<Library> filenames) {
        this.filenames = filenames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Library library = filenames.get(position);
        holder.textView.setText(library.getfilnavn());
    }

    @Override
    public int getItemCount() {
        return filenames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;


        public ViewHolder(@NonNull View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.fil_navn);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                //Context context = null;
                //String library = "R.raw." + filenames.get(position);
                Library library = filenames.get(position);
                String filnavn = library.toString();
                Uri uri = Uri.parse(filnavn);

                //context.getResources().getIdentifier(filnavn, "raw", "raw");
/*
                mediaPlayer = MediaPlayer.create(context,uri);
                mediaPlayer.start();
                try {
                Thread.sleep(5000);
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;

 */
            }


        }
//https://guides.codepath.com/android/using-the-recyclerview
//https://stackoverflow.com/questions/12266502/android-mediaplayer-stop-and-play
//https://stackoverflow.com/questions/15912825/how-to-read-file-from-res-raw-by-name/15912883
//https://stackoverflow.com/questions/6589797/how-to-get-package-name-from-anywhere
    }
}
