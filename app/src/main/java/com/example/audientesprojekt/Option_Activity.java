package com.example.audientesprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Option_Activity extends AppCompatActivity implements View.OnClickListener{

    Button toLibrary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_);

        toLibrary = findViewById(R.id.toLibrary);

        toLibrary.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == toLibrary){
            Intent i = new Intent(this,LibraryActivity.class);
            startActivity(i);
        }
    }
}