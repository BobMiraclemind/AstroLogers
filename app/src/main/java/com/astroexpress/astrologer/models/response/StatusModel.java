package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusModel {
    @SerializedName("Result")
    @Expose
    private Result result;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Code")
    @Expose
    private String code;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class Result{
        @SerializedName("AstrologerId")
        @Expose
        private String astrologerId;
        @SerializedName("IsOnlineForChat")
        @Expose
        private boolean isOnlineForChat;
        @SerializedName("IsOnlineForCall")
        @Expose
        private boolean isOnlineForCall;
        @SerializedName("IsOnlineForChatEM")
        @Expose
        private boolean isOnlineForChatEM;
        @SerializedName("IsOnlineForCallEM")
        @Expose
        private boolean isOnlineForCallEM;

        public String getAstrologerId() {
            return astrologerId;
        }

        public void setAstrologerId(String astrologerId) {
            this.astrologerId = astrologerId;
        }

        public boolean isOnlineForChat() {
            return isOnlineForChat;
        }

        public void setOnlineForChat(boolean onlineForChat) {
            isOnlineForChat = onlineForChat;
        }

        public boolean isOnlineForCall() {
            return isOnlineForCall;
        }

        public void setOnlineForCall(boolean onlineForCall) {
            isOnlineForCall = onlineForCall;
        }

        public boolean isOnlineForChatEM() {
            return isOnlineForChatEM;
        }

        public void setOnlineForChatEM(boolean onlineForChatEM) {
            isOnlineForChatEM = onlineForChatEM;
        }

        public boolean isOnlineForCallEM() {
            return isOnlineForCallEM;
        }

        public void setOnlineForCallEM(boolean onlineForCallEM) {
            isOnlineForCallEM = onlineForCallEM;
        }
    }
}
