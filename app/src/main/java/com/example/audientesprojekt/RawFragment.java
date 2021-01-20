package com.example.audientesprojekt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class RawFragment extends Fragment {


    //https://guides.codepath.com/android/using-the-recyclerview

    //String path = getFilePath(getActivity(),"cave.flac");
    private RecyclerView libraryRecyclerView;
    private ArrayList<Library> filenames;
    private LibraryAdapter adapter;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_raw, container, false);

        libraryRecyclerView = v.findViewById(R.id.libraryrecyclerView);
        textView = v.findViewById(R.id.fil_navn);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        libraryRecyclerView.addItemDecoration(itemDecoration);

        libraryRecyclerView.setHasFixedSize(true);
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        filenames = new ArrayList<>();
        //filenames = Library.createList(10);
        filenames = Library.listRaw();
        //filenames = Library.getFiles();

        adapter = new LibraryAdapter(filenames);
        libraryRecyclerView.setAdapter(adapter);
        return v;
    }
}