package com.astroexpress.astrologer.models;

public class ProfileGalleryModel {
    String imageUrl;

    public ProfileGalleryModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
