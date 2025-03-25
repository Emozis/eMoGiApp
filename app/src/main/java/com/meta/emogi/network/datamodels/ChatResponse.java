package com.meta.emogi.network.datamodels;
public class ChatResponse {
    public static final String GREET = "greeting";
    public static final String CHAT = "chat";
    public static final String END = "end";

    private String type;
    private String character_name;
    private int response_id;
    private String content; // 'character' 필드를 문자열로 가정

    private String chatType;

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCharacterName() {
        return character_name;
    }

    public void setCharacterName(String character_name) {
        this.character_name = character_name;
    }

    public int getResponseId() {
        return response_id;
    }

    public void setResponseId(int response_id) {
        this.response_id = response_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChatType() {
        return chatType;
    }
    public void setChatType(String chatType) {
        this.chatType = chatType;
    }
}
