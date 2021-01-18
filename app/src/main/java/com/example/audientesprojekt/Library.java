package com.example.audientesprojekt;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Library {
    private String filnavn;

    public Library(String filnavn) {
        this.filnavn = filnavn;
    }

    public String getfilnavn(){
        return filnavn;
    }


    public static ArrayList<Library> createList(int a){
        ArrayList<Library> filenames = new ArrayList<Library>();

        for(int i = 0; i < a; i++){
            filenames.add(new Library("Fil" + i));
        }
        System.out.println(filenames);
        return filenames;

    }
    public static ArrayList<Library> getFiles() {
        ArrayList<Library> filenames = new ArrayList<Library>();

        Class directory = R.raw.class;
        Class[] files = directory.getClasses();

        for(int i = 0; i < files.length; i++){
            Library file_name = new Library(files[i].toString());
            filenames.add(new Library("" + file_name));
        }
        System.out.println(filenames);
        return filenames;
    }
    public static ArrayList<Library> listRaw() {
        ArrayList<Library> filenames = new ArrayList<Library>();

        Field[] fields = R.raw.class.getDeclaredFields();
        for(int i=0; i < fields.length; i++){
            Log.i("Raw asset: ", fields[i].getName());
            Library file_name = new Library(fields[i].getName());
            filenames.add(file_name);
            //filenames.add(new Library("" + file_name));
        }
        System.out.println(filenames);
        return  filenames;
    }

}

