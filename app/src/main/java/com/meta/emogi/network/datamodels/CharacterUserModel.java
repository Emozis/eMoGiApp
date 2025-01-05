package com.meta.emogi.network.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterUserModel {

    // 요청 및 응답에 필요한 필드
    @SerializedName("characterId")
    @Expose
    private int characterId;

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

    @SerializedName("characterRelationships")
    @Expose
    private List<CharacterRelationships> characterRelationships;

    @SerializedName("characterCreatedAt")
    @Expose
    private String characterCreatedAt;

    @SerializedName("user")
    @Expose
    private User user;

    // 추가적인 메타데이터 필드
    @SerializedName("characterDescription")
    @Expose
    private String characterDescription;

    @SerializedName("characterGreeting")
    @Expose
    private String characterGreeting;

    @SerializedName("characterIsPublic")
    @Expose
    private boolean characterIsPublic;

    // Relationships 클래스 정의
    public static class CharacterRelationships {
        @SerializedName("relationshipId")
        @Expose
        private int relationshipId;

        @SerializedName("relationshipName")
        @Expose
        private String relationshipName;

        // 생성자
        public CharacterRelationships(int relationshipId, String relationshipName) {
            this.relationshipId = relationshipId;
            this.relationshipName = relationshipName;
        }

        // Getter 및 Setter
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

    // User 클래스 정의
    public static class User {
        @SerializedName("userId")
        @Expose
        private int userId;

        @SerializedName("userEmail")
        @Expose
        private String userEmail;

        @SerializedName("userName")
        @Expose
        private String userName;

        @SerializedName("userProfile")
        @Expose
        private String userProfile;

        // 생성자
        public User(int userId, String userEmail, String userName, String userProfile) {
            this.userId = userId;
            this.userEmail = userEmail;
            this.userName = userName;
            this.userProfile = userProfile;
        }

        // Getter 및 Setter
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
    }

    // 기본 생성자
    public CharacterUserModel() {}

    // 요청에 사용하는 생성자
    public CharacterUserModel(String characterName, String characterProfile, String characterGender,
                          String characterPersonality, String characterDetails, String characterDescription,
                          String characterGreeting, boolean characterIsPublic, List<CharacterRelationships> characterRelationships) {
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

    // Getter 및 Setter
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