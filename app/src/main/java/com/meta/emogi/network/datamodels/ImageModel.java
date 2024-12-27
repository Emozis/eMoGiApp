package com.meta.emogi.network.datamodels;
import com.google.gson.annotations.SerializedName;
public class ImageModel {
    @SerializedName("imageId")
    private int imageId;
    @SerializedName("imageName")
    private String imageName;
    @SerializedName("imageUrl")
    private String imageUrl;
    private boolean isSelected; // 선택 상태를 저장하는 필드 추가

    public ImageModel(int imageId, String imageName, String imageUrl, boolean isSelected) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.isSelected = false;
    }


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
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}