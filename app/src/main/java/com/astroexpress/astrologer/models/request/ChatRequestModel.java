package com.astroexpress.astrologer.models.request;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatRequestModel {

    @SerializedName("ChatId")
    @Expose
    private String chatId;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("AstrologerId")
    @Expose
    private String astrologerId;
    @SerializedName("ChatMessage")
    @Expose
    private String chatMessage;
    @SerializedName("IsSentByUser")
    @Expose
    private Boolean isSentByUser;
    @SerializedName("SeenStatus")
    @Expose
    private String seenStatus;
    @SerializedName("ChatType")
    @Expose
    private String chatType;
    @SerializedName("FirebaseChatId")
    @Expose
    private String firebaseChatId;

    public ChatRequestModel() {
    }

    public ChatRequestModel(String userId, String astrologerId, String chatMessage, Boolean isSentByUser, String seenStatus, String chatType, String firebaseChatId) {
        this.userId = userId;
        this.astrologerId = astrologerId;
        this.chatMessage = chatMessage;
        this.isSentByUser = isSentByUser;
        this.seenStatus = seenStatus;
        this.chatType = chatType;
        this.firebaseChatId = firebaseChatId;
    }

    @PropertyName("UserId")
    public String getUserId() {
        return userId;
    }

    @PropertyName("UserId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @PropertyName("AstrologerId")
    public String getAstrologerId() {
        return astrologerId;
    }

    @PropertyName("AstrologerId")
    public void setAstrologerId(String astrologerId) {
        this.astrologerId = astrologerId;
    }

    @PropertyName("ChatMessage")
    public String getChatMessage() {
        return chatMessage;
    }

    @PropertyName("ChatMessage")
    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    @PropertyName("IsSentByUser")
    public Boolean getIsSentByUser() {
        return isSentByUser;
    }

    @PropertyName("IsSentByUser")
    public void setIsSentByUser(Boolean isSentByUser) {
        this.isSentByUser = isSentByUser;
    }

    @PropertyName("SeenStatus")
    public String getSeenStatus() {
        return seenStatus;
    }

    @PropertyName("SeenStatus")
    public void setSeenStatus(String seenStatus) {
        this.seenStatus = seenStatus;
    }

    @PropertyName("FirebaseChatId")
    public String getFirebaseChatId() {
        return firebaseChatId;
    }

    @PropertyName("FirebaseChatId")
    public void setFirebaseChatId(String firebaseChatId) {
        this.firebaseChatId = firebaseChatId;
    }

    @PropertyName("ChatType")
    public String getChatType() {
        return chatType;
    }

    @PropertyName("ChatType")
    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    @PropertyName("ChatId")
    public String getChatId() {
        return
                chatId;
    }

    @PropertyName("ChatId")
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
