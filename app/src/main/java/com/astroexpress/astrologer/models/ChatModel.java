package com.astroexpress.astrologer.models;

public class ChatModel {
    String imageUrl,message,time,uid;

    public ChatModel() {
    }

    public ChatModel(String imageUrl, String message, String time, String uid) {
        this.imageUrl = imageUrl;
        this.message = message;
        this.time = time;
        this.uid = uid;
    }

    public ChatModel(String message, String uid,String time) {
        this.message = message;
        this.time = time;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
