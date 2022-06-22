package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextOnlineResponseModel {
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
        @SerializedName("NextOnlineForChat")
        @Expose
        private String nextOnlineForChat;
        @SerializedName("NextOnlineForCall")
        @Expose
        private String nextOnlineForCall;

        public String getAstrologerId() {
            return astrologerId;
        }

        public void setAstrologerId(String astrologerId) {
            this.astrologerId = astrologerId;
        }

        public String getNextOnlineForChat() {
            return nextOnlineForChat;
        }

        public void setNextOnlineForChat(String nextOnlineForChat) {
            this.nextOnlineForChat = nextOnlineForChat;
        }

        public String getNextOnlineForCall() {
            return nextOnlineForCall;
        }

        public void setNextOnlineForCall(String nextOnlineForCall) {
            this.nextOnlineForCall = nextOnlineForCall;
        }
    }
}
