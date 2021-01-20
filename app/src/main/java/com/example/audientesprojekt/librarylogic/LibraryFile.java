package com.example.audientesprojekt.librarylogic;
import android.net.Uri;

public class LibraryFile {

    private String fileName;
    private Uri uri;

    public LibraryFile(String fileName, Uri uri) {
        this.fileName = fileName;
        this.uri = uri;

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }


    /*
    public static ArrayList<LibraryFile> createList(int a){
        ArrayList<LibraryFile> filenames = new ArrayList<LibraryFile>();

        for(int i = 0; i < a; i++){
            filenames.add(new LibraryFile("Fil" + i));
        }
        System.out.println(filenames);
        return filenames;

    }

    /*
    public static ArrayList<LibraryFile> getFiles() {
        ArrayList<LibraryFile> filenames = new ArrayList<LibraryFile>();

        Class directory = R.raw.class;
        Class[] files = directory.getClasses();

        for(int i = 0; i < files.length; i++){
            LibraryFile file_name = new LibraryFile(files[i].toString());
            filenames.add(new LibraryFile("" + file_name));
        }
        System.out.println(filenames);
        return filenames;
    }
    public static ArrayList<LibraryFile> listRaw() {
        ArrayList<LibraryFile> filenames = new ArrayList<LibraryFile>();

        Field[] fields = R.raw.class.getDeclaredFields();
        for(int i=0; i < fields.length; i++){
            Log.i("Raw asset: ", fields[i].getName());
            LibraryFile file_name = new LibraryFile(fields[i].getName());
            filenames.add(file_name);
            //filenames.add(new Library("" + file_name));
        }
        System.out.println(filenames);
        return  filenames;
    }

     */

}

