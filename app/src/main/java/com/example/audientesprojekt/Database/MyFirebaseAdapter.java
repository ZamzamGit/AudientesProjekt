package com.example.audientesprojekt.Database;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audientesprojekt.R;
import com.example.audientesprojekt.downloadFragment;

import java.util.ArrayList;

public class MyFirebaseAdapter extends RecyclerView.Adapter<MyViewHolder> {

    downloadFragment downloadFragment;
    ArrayList<sound_bits> sound_bitsArrayList;

    public MyFirebaseAdapter(com.example.audientesprojekt.downloadFragment downloadFragment) {
        this.downloadFragment = downloadFragment;
    }

    public MyFirebaseAdapter(ArrayList<sound_bits> sound_bitsArrayList) {
        this.sound_bitsArrayList = sound_bitsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(downloadFragment.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_download, null, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // det her fungerer ikke hvad sker der for getItemId
        holder.sampleTitle.setText(sound_bits.get);


    }

    @Override
    public int getItemCount() {
        return sound_bitsArrayList.size();
    }
}
