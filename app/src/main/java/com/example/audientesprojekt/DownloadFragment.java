package com.example.audientesprojekt;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.audientesprojekt.Database.MyFirebaseAdapter;
import com.example.audientesprojekt.Database.SoundBits;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DownloadFragment extends Fragment {

    private FirebaseFirestore db;
    private RecyclerView fieBaseRecycler;
    private ArrayList<SoundBits> soundsBitsList = new ArrayList<>();
    private MyFirebaseAdapter myFirebaseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_download, container, false);

        db= FirebaseFirestore.getInstance();
        fieBaseRecycler = v.findViewById(R.id.fireBaseRecycler);
        fieBaseRecycler.setHasFixedSize(true);
        fieBaseRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        setUpFB();
        dataFromFirebase();

        return v;
    }

    private void dataFromFirebase() {
        if(soundsBitsList.size()>0)
            soundsBitsList.clear();

        db.collection("samples")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot documentSnapshot: task.getResult()) {


                            SoundBits soundBits= new SoundBits(documentSnapshot.getString("mediaid"), documentSnapshot.getString("title"),
                                    documentSnapshot.getString("songUrl"));

                            soundsBitsList.add(soundBits);

                        }

                        myFirebaseAdapter= new MyFirebaseAdapter(soundsBitsList);
                        fieBaseRecycler.setAdapter(myFirebaseAdapter);
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error ;-.-;", Toast.LENGTH_SHORT).show();
                    }
                })
        ;


    }

    private void setUpFB(){
        db=FirebaseFirestore.getInstance();
    }
}


