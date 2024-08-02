package com.meta.emogi.network.recyclerview;

public class CategoryItem {
    private String categoryName;
    private boolean isSelected; // 선택 상태를 저장하는 필드 추가

    public CategoryItem(String categoryName) {
        this.categoryName = categoryName;
        this.isSelected = false; // 기본값은 선택되지 않은 상태
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}