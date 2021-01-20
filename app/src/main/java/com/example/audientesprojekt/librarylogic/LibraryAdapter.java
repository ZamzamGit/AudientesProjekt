package com.example.audientesprojekt.librarylogic;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.audientesprojekt.R;

import java.util.ArrayList;


public class LibraryAdapter extends ArrayAdapter<LibraryFile> {


    public LibraryAdapter(Context context, ArrayList<LibraryFile> libraryFiles) {
        super(context, 0, libraryFiles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LibraryFile libraryFile = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.library_list, parent, false);
        }
        TextView audioName = convertView.findViewById(R.id.audioName);
        audioName.setText(libraryFile.getFileName());
        return convertView;
    }
}
