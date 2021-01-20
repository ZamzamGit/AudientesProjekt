package com.example.audientesprojekt.librarylogic;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class LibraryFile implements Parcelable {

    private String fileName;
    private Uri uri;

    public LibraryFile(String fileName, Uri uri) {
        this.fileName = fileName;
        this.uri = uri;
    }

    protected LibraryFile(Parcel in) {
        fileName = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<LibraryFile> CREATOR = new Creator<LibraryFile>() {
        @Override
        public LibraryFile createFromParcel(Parcel in) {
            return new LibraryFile(in);
        }

        @Override
        public LibraryFile[] newArray(int size) {
            return new LibraryFile[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeParcelable(uri, flags);
    }
}

