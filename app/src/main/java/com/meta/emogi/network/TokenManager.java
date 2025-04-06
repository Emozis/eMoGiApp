package com.meta.emogi.network;

public class TokenManager {

    private static final TokenManager instance = new TokenManager();

    private String totken;

    private TokenManager(){}

    public static TokenManager getInstance(){
        return instance;
    }

    public void setTotken(String totken){
        this.totken = totken;
    }

    public String getTotken(){
        return totken;
    }

}
