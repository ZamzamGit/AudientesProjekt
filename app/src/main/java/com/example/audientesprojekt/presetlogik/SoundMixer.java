package com.example.audientesprojekt.presetlogik;
import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import zeroonezero.android.audio_mixer.AudioMixer;
import zeroonezero.android.audio_mixer.input.GeneralAudioInput;

public class SoundMixer {

    private AudioMixer mixer;
    private String path;
    private Activity activity;

    public SoundMixer(Activity activity, String path) {
        this.activity = activity;
        this.path = path;
    }

    public void mix(ArrayList<SoundInput> inputs) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Mixing...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgress(0);

        try {
            mixer = new AudioMixer(path);

            // løber gennem alle lydfiler i arraylisten, og tilføjer dem til mixeren
            for (SoundInput sound : inputs) {
                Uri uri = Uri.parse(sound.getSoundUri());
                GeneralAudioInput audioInput = new GeneralAudioInput(activity, uri, null);
                audioInput.setStartOffsetUs(sound.getStartTime()); // lydfilens starttid
                audioInput.setStartTimeUs(sound.getDurationStart()); // lydfilens start varighed
                audioInput.setEndTimeUs(sound.getDurationEnd()); // lydfilens slut varighed
                mixer.addDataSource(audioInput); // tilføjes til mixeren
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mixer.setMixingType(AudioMixer.MixingType.PARALLEL);

        mixer.setProcessingListener(new AudioMixer.ProcessingListener() {
            @Override
            public void onProgress(final double progress) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setProgress((int) (progress * 100));
                    }
                });
            }

            @Override
            public void onEnd() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                        Toast.makeText(activity, "Mixing done", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        try {
            mixer.start();
            mixer.processAsync();
            progressDialog.show();
        } catch (IOException e) {
            mixer.release();
        }
    }
}
