package com.example.audientesprojekt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        Preset preset = presets.get(position);
        holder.presetName.setText(preset.getPresetName());
    }

    @Override
    public int getItemCount() {
        return presets.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private Button presetBtn;
        private TextView presetName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            presetBtn = itemView.findViewById(R.id.presetButton);
            presetName = itemView.findViewById(R.id.presetName);
        }
    }
}
