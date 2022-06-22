package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletsModel {
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
        @SerializedName("WalletTransactionId")
        @Expose
        private String walletTransactionId;
        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("Amount")
        @Expose
        private String amount;
        @SerializedName("DebitedForm")
        @Expose
        private String debitedFrom;
        @SerializedName("StartTime")
        @Expose
        private String startTime;
        @SerializedName("EndTime")
        @Expose
        private String endTime;
        @SerializedName("TransactionFor")
        @Expose
        private String transactionFor;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("Remark")
        @Expose
        private String remark;
        @SerializedName("FirstName")
        @Expose
        private String firstname;
        @SerializedName("LastName")
        @Expose
        private String lastname;

        public String getWalletTransactionId() {
            return walletTransactionId;
        }

        public void setWalletTransactionId(String walletTransactionId) {
            this.walletTransactionId = walletTransactionId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDebitedFrom() {
            return debitedFrom;
        }

        public void setDebitedFrom(String debitedFrom) {
            this.debitedFrom = debitedFrom;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTransactionFor() {
            return transactionFor;
        }

        public void setTransactionFor(String transactionFor) {
            this.transactionFor = transactionFor;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
    }
}
