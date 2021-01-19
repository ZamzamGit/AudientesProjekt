package com.example.audientesprojekt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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


    public static ArrayList<Library> createList(int a){
        ArrayList<Library> filenames = new ArrayList<Library>();

        for(int i = 0; i < a; i++){
            filenames.add(new Library("Fil" + i));
        }
        System.out.println(filenames);
        return filenames;

    }
    public static ArrayList<Library> getFiles() {
        ArrayList<Library> filenames = new ArrayList<Library>();

        Class directory = R.raw.class;
        Class[] files = directory.getClasses();

        for(int i = 0; i < files.length; i++){
            Library file_name = new Library(files[i].toString());
            filenames.add(new Library("" + file_name));
        }
        System.out.println(filenames);
        return filenames;
    }
    public static ArrayList<Library> listRaw() {
        ArrayList<Library> filenames = new ArrayList<Library>();

        Field[] fields = R.raw.class.getDeclaredFields();
        for(int i=0; i < fields.length; i++){
            Log.i("Raw asset: ", fields[i].getName());
            Library file_name = new Library(fields[i].getName());
            filenames.add(file_name);
            //filenames.add(new Library("" + file_name));
        }
        System.out.println(filenames);
        return  filenames;
    }
}