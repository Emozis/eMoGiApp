package com.meta.emogi.network.datamodels;
import com.google.gson.annotations.SerializedName;
public class UserData {

    @SerializedName("chatId")
    private int chatId;
    @SerializedName("userEmail")
    private String userEmail;
    @SerializedName("userName")
    private String userName;
    @SerializedName("userProfile")
    private String userEuserProfilemail;
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
    public String getUserEuserProfilemail() {
        return userEuserProfilemail;
    }
    public void setUserEuserProfilemail(String userEuserProfilemail) {
        this.userEuserProfilemail = userEuserProfilemail;
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
