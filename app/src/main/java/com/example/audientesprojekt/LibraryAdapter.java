package com.example.audientesprojekt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private ArrayList<Library> filenames;

    public LibraryAdapter(ArrayList<Library> filenames) {
        this.filenames = filenames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Library library = filenames.get(position);
        holder.textView.setText(library.getfilnavn());
    }

    @Override
    public int getItemCount(){
        return filenames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;


        public ViewHolder(@NonNull View view){
            super(view);

            textView = (TextView) view.findViewById(R.id.fil_navn);
        }
    }

}
