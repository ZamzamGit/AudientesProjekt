package com.example.audientesprojekt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
public class LibraryFragment extends Fragment {

    //https://guides.codepath.com/android/using-the-recyclerview

    String path = "C:/Users/mikke/AndroidStudioProjects/AudientesProjekt/app/src/main/res/raw/Animals-flac";
    ArrayList<Library> filenames = new ArrayList<Library>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_library,container,false);
        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        //GetFiles();

        filenames = Library.createList(10);

        LibraryAdapter adapter = new LibraryAdapter(filenames);

        recyclerView.setAdapter(adapter);

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //MyAdapter myAdapter = new My

        return inflater.inflate(R.layout.fragment_library, container, false);
    }
/*
    private ArrayList<Library> GetFiles() {


        File directory = new File(path);
        File[] files = directory.listFiles();

        for(int i = 0; i < files.length; i++){
            Library file_name = (Library) files[i].getName();
            filenames.add(file_name);
        }
        return filenames;
    }

 */
}