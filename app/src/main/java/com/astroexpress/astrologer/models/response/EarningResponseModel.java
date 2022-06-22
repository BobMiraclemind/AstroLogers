package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EarningResponseModel {
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
        private String debitedForm;
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
        @SerializedName("UserFirstName")
        @Expose
        private String userFirstName;
        @SerializedName("UserLastName")
        @Expose
        private String userLastName;

        public String getUserFirstName() {
            return userFirstName;
        }

        public void setUserFirstName(String userFirstName) {
            this.userFirstName = userFirstName;
        }

        public String getUserLastName() {
            return userLastName;
        }

        public void setUserLastName(String userLastName) {
            this.userLastName = userLastName;
        }

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

        public String getDebitedForm() {
            return debitedForm;
        }

        public void setDebitedForm(String debitedForm) {
            this.debitedForm = debitedForm;
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
    }
}
