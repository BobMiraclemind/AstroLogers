package com.astroexpress.astrologer.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveNextOnlineTimeRequestModel {
    @SerializedName("AstrologerId")
    @Expose
    private String astrologerId;
    @SerializedName("NextOnlineTime")
    @Expose
    private String nextOnlineTime;
    @SerializedName("IsFor")
    @Expose
    private String isFor;

    public String getAstrologerId() {
        return astrologerId;
    }

    public void setAstrologerId(String astrologerId) {
        this.astrologerId = astrologerId;
    }

    public String getNextOnlineTime() {
        return nextOnlineTime;
    }

    public void setNextOnlineTime(String nextOnlineTime) {
        this.nextOnlineTime = nextOnlineTime;
    }

    public String getIsFor() {
        return isFor;
    }

    public void setIsFor(String isFor) {
        this.isFor = isFor;
    }
}
