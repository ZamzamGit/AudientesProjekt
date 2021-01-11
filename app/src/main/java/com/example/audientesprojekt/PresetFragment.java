package com.example.audientesprojekt;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class PresetFragment extends Fragment {

    private RecyclerView presetRecyclerView;
    private ArrayList<Preset> presets;
    private PresetRecAdapter adapter;
    private TextView noPresetText;
    private Button addPreset;
    private int i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_preset, container, false);



    }
}