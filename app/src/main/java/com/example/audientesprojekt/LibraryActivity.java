package com.example.audientesprojekt;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LibraryActivity extends AppCompatActivity {

    TextView library;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        library = findViewById(R.id.librarytext);
        listView = findViewById(R.id.listview);
    }
}
