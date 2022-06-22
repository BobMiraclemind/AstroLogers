package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersModel {
    @SerializedName("Result")
    @Expose
    private List<Result> result;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Code")
    @Expose
    private String code;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
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
        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("LastChatMessage")
        @Expose
        private String lastChatMessage;
        @SerializedName("ChatType")
        @Expose
        private String chatType;
        @SerializedName("IsSentByUser")
        @Expose
        private String isSentByUser;
        @SerializedName("SeenStatus")
        @Expose
        private String seenStatus;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("FirstName")
        @Expose
        private String firstname;
        @SerializedName("LastName")
        @Expose
        private String LastName;
        @SerializedName("ProfileImageUrl")
        @Expose
        private String profileImageUrl;
        @SerializedName("ProfileThumbnail")
        @Expose
        private String profileThumbnail;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLastChatMessage() {
            return lastChatMessage;
        }

        public void setLastChatMessage(String lastChatMessage) {
            this.lastChatMessage = lastChatMessage;
        }

        public String getChatType() {
            return chatType;
        }

        public void setChatType(String chatType) {
            this.chatType = chatType;
        }

        public String getIsSentByUser() {
            return isSentByUser;
        }

        public void setIsSentByUser(String isSentByUser) {
            this.isSentByUser = isSentByUser;
        }

        public String getSeenStatus() {
            return seenStatus;
        }

        public void setSeenStatus(String seenStatus) {
            this.seenStatus = seenStatus;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public void setProfileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
        }

        public String getProfileThumbnail() {
            return profileThumbnail;
        }

        public void setProfileThumbnail(String profileThumbnail) {
            this.profileThumbnail = profileThumbnail;
        }
    }


}
