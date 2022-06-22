package com.astroexpress.astrologer.models;

public class ChatListModel {
    String imageUrl,name,lastMessage,time,uid;

    public ChatListModel() {
    }

    public ChatListModel(String name) {
        this.name = name;
    }

    public ChatListModel(String imageUrl, String name, String lastMessage, String time) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.lastMessage = lastMessage;
        this.time = time;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
