package com.meta.emogi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterResponse {
    @SerializedName("characterId")
    private int characterId;
    @SerializedName("characterName")
    private String characterName;
    @SerializedName("characterProfile")
    private String characterProfile;
    @SerializedName("characterGender")
    private String characterGender;
    @SerializedName("characterPersonality")
    private String characterPersonality;
    @SerializedName("characterDetails")
    private String characterDetails;
    @SerializedName("characterRelationships")
    private List<CharacterRelationships> characterRelationships;
    @SerializedName("characterCreatedAt")
    private String characterCreatedAt;
    @SerializedName("user")
    private User user;
    @SerializedName("characterDescription")
    private String characterDescription;
    @SerializedName("characterGreeting")
    private String characterGreeting;
    @SerializedName("characterIsPublic")
    private boolean characterIsPublic;
    private boolean isSelected; // 선택 상태를 저장하는 필드 추가

    // Getters and Setters

    public CharacterResponse(String characterName, String characterDetails) {
        this.characterName = characterName;
        this.characterDetails = characterDetails;
        this.isSelected = false;
    }

    public CharacterResponse(String characterName, String characterProfile, String characterGender, String characterPersonality,
                             String characterDetails, String characterDescription, String characterGreeting, boolean characterIsPublic,
                             List<CharacterRelationships> characterRelationships) {
        this.characterName = characterName;
        this.characterProfile = characterProfile;
        this.characterGender = characterGender;
        this.characterPersonality = characterPersonality;
        this.characterDetails = characterDetails;
        this.characterDescription = characterDescription;
        this.characterGreeting = characterGreeting;
        this.characterIsPublic = characterIsPublic;
        this.characterRelationships = characterRelationships;
    }
    public static class CharacterRelationships {





        public CharacterRelationships(int relationshipId, String relationshipName) {
            this.relationshipId = relationshipId;
            this.relationshipName = relationshipName;
        }

        @SerializedName("relationshipId")
        private int relationshipId;
        @SerializedName("relationshipName")
        private String relationshipName;

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

    }

    public static class User {
        public int getUserId() {
            return userId;
        }
        public void setUserId(int userId) {
            this.userId = userId;
        }
        public String getUserEmail() {
            return userEmail;
        }
        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getUserProfile() {
            return userProfile;
        }
        public void setUserProfile(String userProfile) {
            this.userProfile = userProfile;
        }
        @SerializedName("userId")
        private int userId;
        @SerializedName("userEmail")
        private String userEmail;
        @SerializedName("userName")
        private String userName;
        @SerializedName("userProfile")
        private String userProfile;
    }

    public int getCharacterId() {
        return characterId;
    }
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
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
    public List<CharacterRelationships> getCharacterRelationships() {
        return characterRelationships;
    }
    public void setCharacterRelationships(List<CharacterRelationships> characterRelationships) {
        this.characterRelationships = characterRelationships;
    }
    public String getCharacterCreatedAt() {
        return characterCreatedAt;
    }
    public void setCharacterCreatedAt(String characterCreatedAt) {
        this.characterCreatedAt = characterCreatedAt;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCharacterDescription() {
        return characterDescription;
    }
    public void setCharacterDescription(String characterDescription) {
        this.characterDescription = characterDescription;
    }
    public String getCharacterGreeting() {
        return characterGreeting;
    }
    public void setCharacterGreeting(String characterGreeting) {
        this.characterGreeting = characterGreeting;
    }
    public boolean isCharacterIsPublic() {
        return characterIsPublic;
    }
    public void setCharacterIsPublic(boolean characterIsPublic) {
        this.characterIsPublic = characterIsPublic;
    }
}