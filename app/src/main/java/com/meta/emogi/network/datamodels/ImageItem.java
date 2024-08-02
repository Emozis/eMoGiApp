package com.meta.emogi.network.datamodels;
public class ImageItem {
    private int imageResId;  // 리소스 ID를 사용하거나 URL을 사용할 수 있음

    public ImageItem(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }
}