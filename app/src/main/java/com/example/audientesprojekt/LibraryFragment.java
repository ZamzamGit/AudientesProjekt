package com.example.audientesprojekt;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.audientesprojekt.librarylogic.LibraryAdapter;
import com.example.audientesprojekt.librarylogic.LibraryFactory;
import com.example.audientesprojekt.librarylogic.LibraryFile;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;


public class LibraryFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ArrayList<LibraryFile> libraryFiles;
    private ListView libraryList;
    private LibraryAdapter adapter;
    private TabLayout tabLayout;
    private LibraryFactory factory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_library,container,false);
        /*
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }

         */

        tabLayout = v.findViewById(R.id.tabLayout);
        libraryList = v.findViewById(R.id.libraryList);
        tabLayout.addTab(tabLayout.newTab().setText("Sound"));
        tabLayout.addTab(tabLayout.newTab().setText("Your Presets"));
        libraryFiles = new ArrayList<>();
        factory = new LibraryFactory();
        factory.getLibraryFiles("raw").getFiles(getActivity(), libraryFiles);
        adapter = new LibraryAdapter(getActivity(), libraryFiles);
        libraryList.setAdapter(adapter);
        libraryList.setOnItemClickListener(this);
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0) {
                    factory.getLibraryFiles("raw").getFiles(getActivity(), libraryFiles);

                } else {

                    if(!(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                        libraryFiles.clear();

                    } else {
                        factory.getLibraryFiles("preset").getFiles(getActivity(), libraryFiles);
                    }
                }
                adapter = new LibraryAdapter(getActivity(), libraryFiles);
                libraryList.setAdapter(adapter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("sounds", (ArrayList<? extends Parcelable>) libraryFiles);
        bundle.putInt("position", position);
        MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
        musicPlayerFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, musicPlayerFragment).commit();
    }
}