package com.example.audientesprojekt.librarylogic;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;

public class PresetFiles implements Library {

    @Override
    public void getFiles(Context context, ArrayList<LibraryFile> libraryFiles) {
        libraryFiles.clear();
        String path = Environment.getExternalStorageDirectory().toString() + "/Audientes";
        System.out.println(path);
        File file = new File(path);
        File[] list = file.listFiles();

        for (int i = 0; i < list.length ; i++) {
            libraryFiles.add(new LibraryFile(list[i].getName(), Uri.fromFile(list[i])));
        }
    }
}
