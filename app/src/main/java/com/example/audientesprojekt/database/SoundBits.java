package com.example.audientesprojekt.database;

public class SoundBits {

    private String mediaid,title,songUrl;

    public SoundBits(String mediaid, String title, String songUrl) {
        this.mediaid = mediaid;
        this.title = title;
        this.songUrl = songUrl;
    }

    public String getMediaid() {
        return mediaid;
    }

    public void setMediaid(String mediaid) {
        this.mediaid = mediaid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}


