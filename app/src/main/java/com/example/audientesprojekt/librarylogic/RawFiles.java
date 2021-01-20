package com.example.audientesprojekt.librarylogic;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.example.audientesprojekt.R;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class RawFiles implements Library {

    @Override
    public void getFiles(Context context, ArrayList<LibraryFile> libraryFiles) {
        libraryFiles.clear();
        Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < fields.length; i++) {

            Uri uri = null;
            try {
                uri = Uri.parse(
                        ContentResolver.SCHEME_ANDROID_RESOURCE
                                + File.pathSeparator + File.separator + File.separator
                                + context.getPackageName()
                                + File.separator
                                + R.raw.class.getField(fields[i].getName()).getInt(null)
                );
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }

            libraryFiles.add(new LibraryFile(fields[i].getName(), uri));
        }
    }
}
