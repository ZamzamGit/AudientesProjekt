package com.example.audientesprojekt;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class LibraryFragment extends Fragment {

    //https://guides.codepath.com/android/using-the-recyclerview

    //String path = getFilePath(getActivity(),"cave.flac");
    private RecyclerView libraryRecyclerView;
    private ArrayList<Library> filenames;
    private LibraryAdapter adapter;
    private TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_library,container,false);

        libraryRecyclerView = rootView.findViewById(R.id.libraryrecyclerView);
        textView = rootView.findViewById(R.id.fil_navn);

        libraryRecyclerView.setHasFixedSize(true);
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        filenames = new ArrayList<>();
        //filenames = Library.createList(10);
        filenames = Library.listRaw();
        //filenames = Library.getFiles();

        adapter = new LibraryAdapter(filenames);
        libraryRecyclerView.setAdapter(adapter);

        return rootView;
    }


/*
    public String getFilePath(Context context, String YourFileName){
        File file = context.getFileStreamPath(YourFileName);
        return file.getAbsolutePath();
    }

 */
}