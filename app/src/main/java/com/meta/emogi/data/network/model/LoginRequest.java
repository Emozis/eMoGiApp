// LoginRequest.java
package com.meta.emogi.data.network.model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("accessToken")
    private final String accessToken;

    public LoginRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}