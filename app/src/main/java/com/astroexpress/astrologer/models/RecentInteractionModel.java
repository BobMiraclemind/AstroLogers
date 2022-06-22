package com.astroexpress.astrologer.models;

public class RecentInteractionModel {
    String profileImg,name,problems;

    public RecentInteractionModel(String profileImg, String name, String problems) {
        this.profileImg = profileImg;
        this.name = name;
        this.problems = problems;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }
}
