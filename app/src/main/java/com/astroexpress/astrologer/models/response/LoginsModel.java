package com.astroexpress.astrologer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginsModel {
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
        @SerializedName("FirstName")
        @Expose
        private String firstname;
        @SerializedName("LastName")
        @Expose
        private String lastname;
        @SerializedName("Mobile")
        @Expose
        private String mobile;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Password")
        @Expose
        private String password;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("ProfileImageUrl")
        @Expose
        private String profileImgUrl;
        @SerializedName("ProfileThumbnail")
        @Expose
        private String profileThumbnail;
        @SerializedName("IsOnlineForChat")
        @Expose
        private String isOnlineForChat;
        @SerializedName("IsOnlineForCall")
        @Expose
        private String isOnlineForCall;
        @SerializedName("IsOnlineForChatEM")
        @Expose
        private String  isOnlineForChatEm;
        @SerializedName("IsOnlineForCallEM")
        @Expose
        private String isOnlineForCallEm;
        @SerializedName("IsOnline")
        @Expose
        private String isOnline;
        @SerializedName("IsBoosted")
        @Expose
        private String isBoosted;
        @SerializedName("Speciality")
        @Expose
        private String speciality;
        @SerializedName("Language")
        @Expose
        private String language;
        @SerializedName("Experience")
        @Expose
        private String experience;
        @SerializedName("ChargePerMinute")
        @Expose
        private String chargePerMin;
        @SerializedName("Rating")
        @Expose
        private String rating;
        @SerializedName("TotalCallMinuts")
        @Expose
        private String totalCallMinutes;
        @SerializedName("TotalChatMinuts")
        @Expose
        private String totalChatMinutes;
        @SerializedName("AboutUs")
        @Expose
        private String aboutUs;
        @SerializedName("AadharNumber")
        @Expose
        private String aadhaarNumber;
        @SerializedName("PANNumber")
        @Expose
        private String panNumber;
        @SerializedName("AadharImageUrl")
        @Expose
        private String aadhaarImageUrl;
        @SerializedName("PANImageUrl")
        @Expose
        private String panImageUrl;
        @SerializedName("CountryCode")
        @Expose
        private String countryCode;

        public String getIsBoosted() {
            return isBoosted;
        }

        public void setIsBoosted(String isBoosted) {
            this.isBoosted = isBoosted;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getAadhaarNumber() {
            return aadhaarNumber;
        }

        public void setAadhaarNumber(String aadhaarNumber) {
            this.aadhaarNumber = aadhaarNumber;
        }

        public String getPanNumber() {
            return panNumber;
        }

        public void setPanNumber(String panNumber) {
            this.panNumber = panNumber;
        }

        public String getAadhaarImageUrl() {
            return aadhaarImageUrl;
        }

        public void setAadhaarImageUrl(String aadhaarImageUrl) {
            this.aadhaarImageUrl = aadhaarImageUrl;
        }

        public String getPanImageUrl() {
            return panImageUrl;
        }

        public void setPanImageUrl(String panImageUrl) {
            this.panImageUrl = panImageUrl;
        }

        public String getIsOnlineForChat() {
            return isOnlineForChat;
        }

        public void setIsOnlineForChat(String isOnlineForChat) {
            this.isOnlineForChat = isOnlineForChat;
        }

        public String getIsOnlineForCall() {
            return isOnlineForCall;
        }

        public void setIsOnlineForCall(String isOnlineForCall) {
            this.isOnlineForCall = isOnlineForCall;
        }

        public String getIsOnlineForChatEm() {
            return isOnlineForChatEm;
        }

        public void setIsOnlineForChatEm(String isOnlineForChatEm) {
            this.isOnlineForChatEm = isOnlineForChatEm;
        }

        public String getIsOnlineForCallEm() {
            return isOnlineForCallEm;
        }

        public void setIsOnlineForCallEm(String isOnlineForCallEm) {
            this.isOnlineForCallEm = isOnlineForCallEm;
        }

        public String getAstrologerId() {
            return astrologerId;
        }

        public void setAstrologerId(String astrologerId) {
            this.astrologerId = astrologerId;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getProfileImgUrl() {
            return profileImgUrl;
        }

        public void setProfileImgUrl(String profileImgUrl) {
            this.profileImgUrl = profileImgUrl;
        }

        public String getProfileThumbnail() {
            return profileThumbnail;
        }

        public void setProfileThumbnail(String profileThumbnail) {
            this.profileThumbnail = profileThumbnail;
        }

        public String getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(String isOnline) {
            this.isOnline = isOnline;
        }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getChargePerMin() {
            return chargePerMin;
        }

        public void setChargePerMin(String chargePerMin) {
            this.chargePerMin = chargePerMin;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getTotalCallMinutes() {
            return totalCallMinutes;
        }

        public void setTotalCallMinutes(String totalCallMinutes) {
            this.totalCallMinutes = totalCallMinutes;
        }

        public String getTotalChatMinutes() {
            return totalChatMinutes;
        }

        public void setTotalChatMinutes(String totalChatMinutes) {
            this.totalChatMinutes = totalChatMinutes;
        }

        public String getAboutUs() {
            return aboutUs;
        }

        public void setAboutUs(String aboutUs) {
            this.aboutUs = aboutUs;
        }
    }
}
