package com.meta.emogi.network.datamodels;

import com.google.gson.annotations.SerializedName;

public class ImageModel {
    @SerializedName("imageId")
    private int imageId;
    @SerializedName("imageName")
    private String imageName;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("imageGender")
    private String imageGender;
    @SerializedName("imageAgeGroup")
    private String imageAgeGroup;
    @SerializedName("imageEmotion")
    private String imageEmotion;
    @SerializedName("imageCreatedAt")
    private String imageCreatedAt;
    @SerializedName("imageUpdatedAt")
    private String imageUpdatedAt;

    private boolean isSelected; // 선택 상태를 저장하는 필드 추가

    // 생성자 추가
    public ImageModel(int imageId, String imageName, String imageUrl, String imageGender, String imageAgeGroup, String imageEmotion, String imageCreatedAt, String imageUpdatedAt, boolean isSelected) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.imageGender = imageGender;
        this.imageAgeGroup = imageAgeGroup;
        this.imageEmotion = imageEmotion;
        this.imageCreatedAt = imageCreatedAt;
        this.imageUpdatedAt = imageUpdatedAt;
        this.isSelected = isSelected;
    }

    // Getters and Setters
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageGender() {
        return imageGender;
    }

    public void setImageGender(String imageGender) {
        this.imageGender = imageGender;
    }

    public String getImageAgeGroup() {
        return imageAgeGroup;
    }

    public void setImageAgeGroup(String imageAgeGroup) {
        this.imageAgeGroup = imageAgeGroup;
    }

    public String getImageEmotion() {
        return imageEmotion;
    }

    public void setImageEmotion(String imageEmotion) {
        this.imageEmotion = imageEmotion;
    }

    public String getImageCreatedAt() {
        return imageCreatedAt;
    }

    public void setImageCreatedAt(String imageCreatedAt) {
        this.imageCreatedAt = imageCreatedAt;
    }

    public String getImageUpdatedAt() {
        return imageUpdatedAt;
    }

    public void setImageUpdatedAt(String imageUpdatedAt) {
        this.imageUpdatedAt = imageUpdatedAt;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}