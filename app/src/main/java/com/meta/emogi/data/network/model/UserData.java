package com.meta.emogi.data.network.model;
import com.google.gson.annotations.SerializedName;
public class UserData {

    @SerializedName("chatId")
    private int chatId;
    @SerializedName("userEmail")
    private String userEmail;
    @SerializedName("userName")
    private String userName;
    @SerializedName("userProfile")
    private String userProfile;
    @SerializedName("userGender")
    private String userGender;
    @SerializedName("userBirthdate")
    private String userBirthdate;

    public int getChatId() {
        return chatId;
    }
    public void setChatId(int chatId) {
        this.chatId = chatId;
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
    public String getUserGender() {
        return userGender;
    }
    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
    public String getUserBirthdate() {
        return userBirthdate;
    }
    public void setUserBirthdate(String userBirthdate) {
        this.userBirthdate = userBirthdate;
    }
}
