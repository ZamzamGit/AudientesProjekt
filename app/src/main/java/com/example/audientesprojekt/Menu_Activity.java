package com.example.audientesprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_Activity extends AppCompatActivity {


    Button continueToPresets = (Button) findViewById(R.id.presetBtn);
    Button continueToHearingTest = (Button) findViewById(R.id.hearingTestBtn);
    Button continueToOptions = (Button) findViewById(R.id.second_activity_btn);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        continueToPresets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(),Preset_activity.class);
                // a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(a);

            }
        });

        continueToHearingTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(),Hearing_test_activity.class);
                startActivity(a);
            }
        });

        continueToOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(),Option_Activity.class);
                startActivity(a);
    }
    });
    }
}