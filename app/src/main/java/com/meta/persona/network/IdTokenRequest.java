package com.meta.persona.network;
public class IdTokenRequest {
    private String code;

    public IdTokenRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}