package com.example.audientesprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button secondActivityBtn ;
    Button loginButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        secondActivityBtn = findViewById(R.id.second_activity_btn);
        //loginButton = findViewById(R.id.loginButton);
        secondActivityBtn.setOnClickListener(this);
        loginButton.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        if (v == secondActivityBtn){
            Intent a = new Intent(this, Menu_Activity.class);
            startActivity(a);
        }else {
            Intent a = new Intent(this, Menu_Activity.class);
            startActivity(a);
        }
    }
}