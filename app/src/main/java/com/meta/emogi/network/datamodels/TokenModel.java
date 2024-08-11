package com.meta.emogi.network.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TokenModel {


    // 요청에 사용할 생성자
    public TokenModel(String idToken) {
        this.idToken = idToken;
    }

    // 기본 생성자 - 응답에 사용할 기본 생성자
    public TokenModel() {}

    @SerializedName("idToken")
    @Expose(serialize = true,
            deserialize = false)
    private String idToken;

    @SerializedName("status")
    @Expose(serialize = false,
            deserialize = true)
    private String status;

    @SerializedName("message")
    @Expose(serialize = false,
            deserialize = true)
    private String message;

    @SerializedName("user")
    @Expose(serialize = false,
            deserialize = true)
    private User user;

    @SerializedName("accessToken")
    @Expose(serialize = false,
            deserialize = true)
    private String accessToken;

    public static class User {
        @SerializedName("userId")
        @Expose(serialize = false,
                deserialize = true)
        private int userId;
        @SerializedName("userEmail")
        @Expose(serialize = false,
                deserialize = true)
        private String userEmail;
        @SerializedName("userName")
        @Expose(serialize = false,
                deserialize = true)
        private String userName;
        @SerializedName("userProfile")
        @Expose(serialize = false,
                deserialize = true)
        private String userProfile;

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

    public String getIdToken() {
        return idToken;
    }
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}