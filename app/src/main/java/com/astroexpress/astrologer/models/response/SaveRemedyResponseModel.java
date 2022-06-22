package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveRemedyResponseModel {
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
        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("ProductName")
        @Expose
        private String productName;
        @SerializedName("Price")
        @Expose
        private String price;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("Attachments")
        @Expose
        private List<Object> attachments = null;
        @SerializedName("RemedyId")
        @Expose
        private Integer remedyId;

        public String getAstrologerId() {
            return astrologerId;
        }

        public void setAstrologerId(String astrologerId) {
            this.astrologerId = astrologerId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<Object> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<Object> attachments) {
            this.attachments = attachments;
        }

        public Integer getRemedyId() {
            return remedyId;
        }

        public void setRemedyId(Integer remedyId) {
            this.remedyId = remedyId;
        }
    }
}
