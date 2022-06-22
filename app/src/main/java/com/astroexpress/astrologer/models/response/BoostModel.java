package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoostModel {
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
        @SerializedName("IsEnabled")
        @Expose
        private String isEnabled;

        public String getIsEnabled() {
            return isEnabled;
        }

        public void setIsEnabled(String isEnabled) {
            this.isEnabled = isEnabled;
        }
    }
}
