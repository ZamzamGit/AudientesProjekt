package com.example.audientesprojekt;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class PresetFragment extends Fragment implements View.OnClickListener {

    private RecyclerView presetRecyclerView;
    private ArrayList<Preset> presets;
    private PresetRecAdapter adapter;
    private TextView noPresetText;
    private Button addPreset;
    private int i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preset, container, false);

        presetRecyclerView = v.findViewById(R.id.presetRecyclerView);
        noPresetText = v.findViewById(R.id.noPresetText);
        addPreset = v.findViewById(R.id.addPresetBtn);

        presetRecyclerView.setHasFixedSize(true);
        presetRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        presets = new ArrayList<>();

        adapter = new PresetRecAdapter(presets);
        presetRecyclerView.setAdapter(adapter);
        addPreset.setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View v) {
        i++;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter a preset name");
        final EditText input = new EditText(getActivity());
        input.setText("Preset " + i);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String presetName = input.getText().toString();
                Preset preset = new Preset(presetName);
                presets.add(preset);
                adapter.notifyDataSetChanged();

                if(!presets.isEmpty()) {
                    noPresetText.setVisibility(View.GONE);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}