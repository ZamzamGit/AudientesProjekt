package com.example.audientesprojekt;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Environment;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.audientesprojekt.presetlogik.PresetRecAdapter;
import com.example.audientesprojekt.presetlogik.SoundData;
import com.example.audientesprojekt.presetlogik.SoundInput;
import com.example.audientesprojekt.presetlogik.SoundInputDialog;
import com.example.audientesprojekt.presetlogik.SoundMixer;
import java.io.File;
import java.util.ArrayList;

public class PresetFragment extends Fragment implements View.OnClickListener {

    private RecyclerView presetRecyclerView;
    private ArrayList<SoundInput> soundInputs;
    private PresetRecAdapter adapter;
    private Button addSound, mixBtn;
    private String appFolder = "Audientes";
    private int REQUEST_CODE = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preset, container, false);

        /*
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }

         */

        presetRecyclerView = v.findViewById(R.id.presetRecyclerView);
        addSound = v.findViewById(R.id.addSoundBtn);
        mixBtn = v.findViewById(R.id.mixBtn);

        presetRecyclerView.setHasFixedSize(true);
        presetRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        soundInputs = SoundData.getInstance().readSounds(getActivity());

        if(soundInputs == null) {
            soundInputs = new ArrayList<>();
        }

        adapter = new PresetRecAdapter(getActivity(), soundInputs);
        presetRecyclerView.setAdapter(adapter);
        addSound.setOnClickListener(this);
        mixBtn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        if(v == addSound) {
            // hvis appen har adgang til brugerens filer
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                File file = new File(Environment.getExternalStorageDirectory(), appFolder);
                // mappe oprettes hvis ikke den allerede eksisterer
                if(!file.exists()) {
                    file.mkdirs();
                }
                selectDialog();
            } else {
                // hvis applikationen ikke har adgang til brugerens filer
                requestStoragePermission();
            }
        } else {

            if(soundInputs.isEmpty()) {
                Toast.makeText(getActivity(), "No sounds to mix", Toast.LENGTH_SHORT).show();
            } else {
                startMix();
            }
        }
    }

    // ny fil navngivet og lydfilerne mixes derefter
    public void startMix() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Name your file");
        final EditText nameInput = new EditText(getActivity());
        builder.setView(nameInput);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = nameInput.getText().toString();

                if(fileName.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_SHORT).show();
                } else {
                    String outputPath = Environment.getExternalStorageDirectory() + "/" + appFolder + "/" + fileName + ".flac";
                    SoundMixer mixer = new SoundMixer(getActivity(), outputPath);
                    mixer.mix(soundInputs);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void openUserAudioFiles() {
        Intent intent;
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void selectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] fileOptions = {"Choose your own file", "Choose file from library", "Cancel"};
        builder.setTitle("Choose an option");
        builder.setItems(fileOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    openUserAudioFiles();
                } else if(which == 1) {
                    Toast.makeText(getActivity(), "Library", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }

    public void requestStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE) {
            if(grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission is not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();

                Cursor cursor = getActivity().getContentResolver().
                        query(uri, null, null, null, null);
                int fileName = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME); // henter lydfilens navn
                cursor.moveToFirst();
                MediaMetadataRetriever retriever = new MediaMetadataRetriever(); // bruges til at hente informationer om lydfil
                retriever.setDataSource(getActivity(), uri);
                String durationString = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // henter lydfilens varighed
                long duration = Integer.parseInt(durationString) * 1000;  //konventerer varigheden fra milli to mikro sekunder

                SoundInput input = new SoundInput(cursor.getString(fileName), uri.toString(), duration);
                inputDialog(input);

                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void inputDialog(final SoundInput input) {
        final SoundInputDialog soundInputDialog = new SoundInputDialog(getActivity(), input);
        soundInputDialog.setCancelable(false);
        soundInputDialog.show();

        /* Denne her metode køres hver 200 millisekunder, for at tjekke om en condition er true
         * Her bliver betingelsen true, når dialogen lukkes, og alt nyt information indsættes
         * inde i objektet, som derefter bliver sat i arraylisten og gemmes*/
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!soundInputDialog.isDialogClosed())
                    handler.postDelayed(this, 200);
                else {
                    input.setStartTime((long)(soundInputDialog.getOffset()*1000000));
                    input.setDurationStart(soundInputDialog.getMinimum());
                    input.setDurationEnd(soundInputDialog.getMaximum());
                    soundInputs.add(input);
                    SoundData.getInstance().saveSound(input, getActivity());
                    refreshFragment();
                }
            }
        }, 200);
    }

    public void refreshFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            transaction.setReorderingAllowed(false);
        }
        transaction.detach(this).attach(this).commit();
    }
}