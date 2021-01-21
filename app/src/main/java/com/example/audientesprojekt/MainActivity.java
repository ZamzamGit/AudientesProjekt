package com.example.audientesprojekt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomViewnavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }

         */
        setContentView(R.layout.activity_main);
        bottomViewnavigator = findViewById(R.id.bottomNavigationView);
        bottomViewnavigator.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MusicPlayerFragment()).commit();
        bottomViewnavigator.setSelectedItemId(R.id.musicplayerFragment);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.libraryFragment:
                            selectedFragment = new LibraryFragment();
                            break;
                        case R.id.musicplayerFragment:
                            selectedFragment = new MusicPlayerFragment();
                            break;
                        case R.id.hearinTestFragment:
                            selectedFragment = new HearingTestFragment();
                            break;
                        case R.id.presetFragment:
                            selectedFragment = new PresetFragment();
                            break;
                        case R.id.optionsFragment:
                            selectedFragment = new OptionsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
                    return true;
                }
            };
}