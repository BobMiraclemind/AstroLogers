package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuggestRemedyCategoryModel {
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
        @SerializedName("RemedyCategoryId")
        @Expose
        private String remedyCategoryId;
        @SerializedName("CategoryName")
        @Expose
        private String categoryName;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;

        public String getRemedyCategoryId() {
            return remedyCategoryId;
        }

        public void setRemedyCategoryId(String remedyCategoryId) {
            this.remedyCategoryId = remedyCategoryId;
        }
        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        @Override
        public String toString() {
            /*return "Result{" +
                    "remedyCategoryId='" + remedyCategoryId + '\'' +
                    ", categoryName='" + categoryName + '\'' +
                    ", createdOn='" + createdOn + '\'' +
                    '}';*/
            return categoryName;
        }
    }
}
