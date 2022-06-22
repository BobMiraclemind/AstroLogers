package com.astroexpress.astrologer.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatSessionRequestModel {
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("AstrologerId")
    @Expose
    private String astrologerId;

    public ChatSessionRequestModel(String userId, String astrologerId) {
        this.userId = userId;
        this.astrologerId = astrologerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAstrologerId() {
        return astrologerId;
    }

    public void setAstrologerId(String astrologerId) {
        this.astrologerId = astrologerId;
    }
}
