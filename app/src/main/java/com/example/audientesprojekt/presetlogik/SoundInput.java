package com.example.audientesprojekt.presetlogik;

public class SoundInput {

    private String soundName;
    private String soundUri;
    private long soundDuration;
    private long startTime;
    private long durationStart;
    private long durationEnd;

    public SoundInput(String soundName, String soundUri, long soundDuration) {
        this.soundName = soundName;
        this.soundUri = soundUri;
        this.soundDuration = soundDuration;
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public String getSoundUri() {
        return soundUri;
    }

    public void setSoundUri(String soundUri) {
        this.soundUri = soundUri;
    }

    public long getSoundDuration() {
        return soundDuration;
    }

    public void setSoundDuration(long soundDuration) {
        this.soundDuration = soundDuration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDurationStart() {
        return durationStart;
    }

    public void setDurationStart(long durationStart) {
        this.durationStart = durationStart;
    }

    public long getDurationEnd() {
        return durationEnd;
    }

    public void setDurationEnd(long durationEnd) {
        this.durationEnd = durationEnd;
    }
}
