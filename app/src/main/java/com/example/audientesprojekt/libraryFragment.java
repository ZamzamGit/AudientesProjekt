package com.example.audientesprojekt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class libraryFragment extends Fragment {

    String path = "C:/Users/mikke/AndroidStudioProjects/AudientesProjekt/app/src/main/res/raw/Animals-flac";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GetFiles();


        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    private ArrayList<String> GetFiles() {
        ArrayList<String> filenames = new ArrayList<String>();

        File directory = new File(path);
        File[] files = directory.listFiles();

        for(int i = 0; i < files.length; i++){
            String file_name = files[i].getName();
            filenames.add(file_name);
        }
        return filenames;
    }
}