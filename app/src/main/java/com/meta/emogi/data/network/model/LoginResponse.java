// LoginResponse.java
package com.meta.emogi.data.network.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("success") private boolean success;
    @SerializedName("message") private String message;
    @SerializedName("data")    private Data data;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Data getData() { return data; }

    public static class Data {
        @SerializedName("accessToken")   private String accessToken;
        @SerializedName("refreshToken")  private String refreshToken;
        @SerializedName("isNewUser")     private boolean isNewUser;

        public String getAccessToken()  { return accessToken; }
        public String getRefreshToken() { return refreshToken; }
        public boolean isNewUser()      { return isNewUser; }
    }
}