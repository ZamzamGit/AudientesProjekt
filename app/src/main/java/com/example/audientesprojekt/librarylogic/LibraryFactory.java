package com.example.audientesprojekt.librarylogic;

public class LibraryFactory {

    // Factory Pattern
    public Library getLibraryFiles(String type) {

        if(type.equals("raw")) {
            return new RawFiles();


        } else if(type.equals("preset")) {
            return new PresetFiles();
        }
        return null;
    }
}
