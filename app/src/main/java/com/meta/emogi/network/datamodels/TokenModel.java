package com.meta.emogi.network.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenModel {

    @SerializedName("idToken")
    @Expose(serialize = true, deserialize = false)
    private String idToken;

    @SerializedName("jwtToken")
    @Expose(serialize = false, deserialize = true)
    private String jwtToken;

    // 요청에 사용할 생성자
    public TokenModel(String idToken) {
        this.idToken = idToken;
    }

    // 기본 생성자 - 응답에 사용할 기본 생성자
    public TokenModel() {}

    // Getters and Setters
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}