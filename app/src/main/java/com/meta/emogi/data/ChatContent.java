package com.meta.emogi.data;
public class ChatContent {
    public static final String TYPE_AUTH = "auth";
    public static final String TYPE_USER = "user";
    public static final String TYPE_CHARACTER = "character";

    private String type;
    private String content;
    private String token; // 인증 토큰을 위한 필드 추가
    private String aiUrl;

    // 인증 메시지 생성자를 추가합니다.
    public ChatContent(String type, String token, boolean notUse) {
        this.type = type;
        this.token = token;
    }

    // 채팅 메시지 생성자를 유지합니다.
    public ChatContent(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public ChatContent(String type, String content,String aiUrl) {
        this.type = type;
        this.content = content;
        this.aiUrl = aiUrl;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void appendContent(String additionalContent) {
        this.content += additionalContent; // Append new message fragment
    }

    public String getAiUrl() {
        return aiUrl;
    }
    public void setAiUrl(String aiUrl) {
        this.aiUrl = aiUrl;
    }
}
