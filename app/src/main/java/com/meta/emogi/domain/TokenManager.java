package com.meta.emogi.domain;

public class TokenManager {

    private static final TokenManager instance = new TokenManager();

    private String token;

    private TokenManager(){}

    public static TokenManager getInstance(){
        return instance;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

}
