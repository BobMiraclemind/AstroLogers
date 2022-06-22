package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AstrologerStatusModel {
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
        @SerializedName("IsActive")
        @Expose
        private boolean isActive;
        @SerializedName("IsDeleted")
        @Expose
        private boolean isDeleted;

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }
    }
}
