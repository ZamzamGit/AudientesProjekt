package com.example.audientesprojekt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PresetRecAdapter extends RecyclerView.Adapter<PresetRecAdapter.ViewHolder> {

    private ArrayList<Preset> presets;


    public PresetRecAdapter(ArrayList<Preset> presets) {
        this.presets = presets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preset_recyclerlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Preset preset = presets.get(position);
        holder.audioName.setText(preset.getPresetName());
        holder.audioDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presets.remove(preset);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return presets.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView audioImage;
        private TextView audioName;
        private ImageView audioDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            audioImage = itemView.findViewById(R.id.audioImage);
            audioName = itemView.findViewById(R.id.audioName);
            audioDelete = itemView.findViewById(R.id.audioDelete);
        }
    }
}
