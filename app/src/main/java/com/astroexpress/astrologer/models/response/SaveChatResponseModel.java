package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveChatResponseModel {

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

    public static class Result {

        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("AstrologerId")
        @Expose
        private String astrologerId;
        @SerializedName("ChatMessage")
        @Expose
        private String chatMessage;
        @SerializedName("FileUrl")
        @Expose
        private Object fileUrl;
        @SerializedName("FileThumbnail")
        @Expose
        private Object fileThumbnail;
        @SerializedName("IsSentByUser")
        @Expose
        private Boolean isSentByUser;
        @SerializedName("SeenStatus")
        @Expose
        private String seenStatus;
        @SerializedName("Id")
        @Expose
        private Integer id;

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

        public String getChatMessage() {
            return chatMessage;
        }

        public void setChatMessage(String chatMessage) {
            this.chatMessage = chatMessage;
        }

        public Object getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(Object fileUrl) {
            this.fileUrl = fileUrl;
        }

        public Object getFileThumbnail() {
            return fileThumbnail;
        }

        public void setFileThumbnail(Object fileThumbnail) {
            this.fileThumbnail = fileThumbnail;
        }

        public Boolean getIsSentByUser() {
            return isSentByUser;
        }

        public void setIsSentByUser(Boolean isSentByUser) {
            this.isSentByUser = isSentByUser;
        }

        public String getSeenStatus() {
            return seenStatus;
        }

        public void setSeenStatus(String seenStatus) {
            this.seenStatus = seenStatus;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }

}

