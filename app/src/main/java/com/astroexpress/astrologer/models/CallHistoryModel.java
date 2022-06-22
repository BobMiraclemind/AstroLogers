package com.astroexpress.astrologer.models;

public class CallHistoryModel {
    String name,duration,time;

    public CallHistoryModel() {
    }

    public CallHistoryModel(String name, String duration, String time) {
        this.name = name;
        this.duration = duration;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
