package com.meta.emogi.network.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MakeCharacterModel {
    // 요청에 필요한 필드
    @SerializedName("characterName")
    @Expose
    private String characterName;

    @SerializedName("characterProfile")
    @Expose
    private String characterProfile;

    @SerializedName("characterGender")
    @Expose
    private String characterGender;

    @SerializedName("characterPersonality")
    @Expose
    private String characterPersonality;

    @SerializedName("characterDetails")
    @Expose
    private String characterDetails;

    @SerializedName("characterIsPublic")
    @Expose
    private boolean characterIsPublic;

    @SerializedName("relationships")
    @Expose
    private List<Integer> relationships;

    // 응답에서 받는 필드
    @SerializedName("characterId")
    @Expose
    private int characterId;

    @SerializedName("message")
    @Expose
    private String message;

    // 기본 생성자
    public MakeCharacterModel() {
    }

    // 요청에 사용하는 생성자
    public MakeCharacterModel(String characterName, String characterProfile, String characterGender,
                          String characterPersonality, String characterDetails,
                          boolean characterIsPublic, List<Integer> relationships) {
        this.characterName = characterName;
        this.characterProfile = characterProfile;
        this.characterGender = characterGender;
        this.characterPersonality = characterPersonality;
        this.characterDetails = characterDetails;
        this.characterIsPublic = characterIsPublic;
        this.relationships = relationships;
    }

    // Getter와 Setter
    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterProfile() {
        return characterProfile;
    }

    public void setCharacterProfile(String characterProfile) {
        this.characterProfile = characterProfile;
    }

    public String getCharacterGender() {
        return characterGender;
    }

    public void setCharacterGender(String characterGender) {
        this.characterGender = characterGender;
    }

    public String getCharacterPersonality() {
        return characterPersonality;
    }

    public void setCharacterPersonality(String characterPersonality) {
        this.characterPersonality = characterPersonality;
    }

    public String getCharacterDetails() {
        return characterDetails;
    }

    public void setCharacterDetails(String characterDetails) {
        this.characterDetails = characterDetails;
    }

    public boolean isCharacterIsPublic() {
        return characterIsPublic;
    }

    public void setCharacterIsPublic(boolean characterIsPublic) {
        this.characterIsPublic = characterIsPublic;
    }

    public List<Integer> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Integer> relationships) {
        this.relationships = relationships;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}