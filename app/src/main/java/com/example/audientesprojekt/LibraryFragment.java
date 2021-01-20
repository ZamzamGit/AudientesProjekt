package com.example.audientesprojekt;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.audientesprojekt.Adapter.SectionPageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class LibraryFragment extends Fragment {

    //https://guides.codepath.com/android/using-the-recyclerview

    //String path = getFilePath(getActivity(),"cave.flac");
    private RecyclerView libraryRecyclerView;
    private ArrayList<Library> filenames;
    private LibraryAdapter adapter;
    private TextView textView;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_library,container,false);

        viewPager = v.findViewById(R.id.viewPager);
        tabLayout = v.findViewById(R.id.tabLayout);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getChildFragmentManager());
        sectionPageAdapter.addFragment(new RawFragment(), "Raw");
        sectionPageAdapter.addFragment(new Library_Preset_Fragment(), "Presets");

        viewPager.setAdapter(sectionPageAdapter);
    }

    /*
    public String getFilePath(Context context, String YourFileName){
        File file = context.getFileStreamPath(YourFileName);
        return file.getAbsolutePath();
    }

 */
}