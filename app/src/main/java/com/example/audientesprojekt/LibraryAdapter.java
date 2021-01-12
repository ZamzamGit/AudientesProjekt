package com.example.audientesprojekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    public LibraryAdapter(ArrayList<Library> filenames) {
        mLibrary = filenames;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;


        public ViewHolder(View view){
            super(view);

            textView = (TextView) view.findViewById(R.id.fil_navn);
        }
    }
    private List<Library> mLibrary;

    public void LibraryAdapter(List<Library> library){
        mLibrary = library;
    }

    @Override
    public LibraryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View libraryView = inflater.inflate(R.layout.rowlayout,parent, false);


        ViewHolder viewHolder = new ViewHolder(libraryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LibraryAdapter.ViewHolder holder, int position){
        Library library = mLibrary.get(position);

        TextView textView = holder.textView;
        textView.setText(library.getfilnavn());


    }

    @Override
    public int getItemCount() {
        return 0;
    }
    /*
    @Override
    public int getFilCount(){
        return mLibrary.size();
    }
     */

}
