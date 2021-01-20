package com.example.audientesprojekt.librarylogic;
import android.content.Context;
import java.util.ArrayList;

public interface Library {

    void getFiles(Context context, ArrayList<LibraryFile> libraryFiles);
}
