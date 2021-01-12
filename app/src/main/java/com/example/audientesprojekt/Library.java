package com.example.audientesprojekt;

import java.util.ArrayList;

public class Library {
    private String mfilnavn;

    public Library(String fil) {
        mfilnavn = fil;
    }

    public String getfilnavn(){
        return mfilnavn;
    }


    public static ArrayList<Library> createList(int a){
        ArrayList<Library> files = new ArrayList<Library>();

        for(int i = 0; i < a; i++){
            files.add(new Library("Fil" + i));
        }
        System.out.println(files);
        return files;

    }

}

