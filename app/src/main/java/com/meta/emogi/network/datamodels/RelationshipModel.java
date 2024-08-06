package com.meta.emogi.network.datamodels;

import com.google.gson.annotations.SerializedName;
public class RelationshipModel {

    @SerializedName("relationshipId")
    private int relationshipId;
    @SerializedName("relationshipName")
    private String relationshipName;
    private boolean isSelected;

    public RelationshipModel(int relationshipId, String relationshipName) {
        this.relationshipId = relationshipId;
        this.relationshipName = relationshipName;
        this.isSelected = false;
    }
    public int getRelationshipId() {
        return relationshipId;
    }
    public void setRelationshipId(int relationshipId) {
        this.relationshipId = relationshipId;
    }
    public String getRelationshipName() {
        return relationshipName;
    }
    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}