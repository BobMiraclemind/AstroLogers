package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastMonthEarningModel {
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
        @SerializedName("TotalEarning")
        @Expose
        private String totalEarning;
        @SerializedName("PGCharges")
        @Expose
        private String pgCharges;
        @SerializedName("PGChargesPercentage")
        @Expose
        private String pgChargesPercent;
        @SerializedName("TDS")
        @Expose
        private String tds;
        @SerializedName("TDSPercentage")
        @Expose
        private String tdsPercent;
        @SerializedName("FinalEarning")
        @Expose
        private String finalEarning;

        public String getTotalEarning() {
            return totalEarning;
        }

        public void setTotalEarning(String totalEarning) {
            this.totalEarning = totalEarning;
        }

        public String getPgCharges() {
            return pgCharges;
        }

        public void setPgCharges(String pgCharges) {
            this.pgCharges = pgCharges;
        }

        public String getPgChargesPercent() {
            return pgChargesPercent;
        }

        public void setPgChargesPercent(String pgChargesPercent) {
            this.pgChargesPercent = pgChargesPercent;
        }

        public String getTds() {
            return tds;
        }

        public void setTds(String tds) {
            this.tds = tds;
        }

        public String getTdsPercent() {
            return tdsPercent;
        }

        public void setTdsPercent(String tdsPercent) {
            this.tdsPercent = tdsPercent;
        }

        public String getFinalEarning() {
            return finalEarning;
        }

        public void setFinalEarning(String finalEarning) {
            this.finalEarning = finalEarning;
        }
    }
}
