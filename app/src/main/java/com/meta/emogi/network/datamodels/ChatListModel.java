package com.meta.emogi.network.datamodels;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class ChatListModel {

    @SerializedName("chatId")
    private int chatId;
    @SerializedName("userId")
    private int userId;
    @SerializedName("characterId")
    private int characterId;
    @SerializedName("chatCreateAt")
    private String chatCreateAt;
    @SerializedName("lastMessageAt")
    private String lastMessageAt;
    @SerializedName("chatLogs")
    private List<ChatLogs> chatLogs;

    public static class ChatLogs {
        @SerializedName("logId")
        private int logId;
        @SerializedName("contents")
        private String contents;

        public int getLogId() {
            return logId;
        }
        public void setLogId(int logId) {
            this.logId = logId;
        }
        public String getContents() {
            return contents;
        }
        public void setContents(String contents) {
            this.contents = contents;
        }
    }
    public int getChatId() {
        return chatId;
    }
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getCharacterId() {
        return characterId;
    }
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
    public String getChatCreateAt() {
        return chatCreateAt;
    }
    public void setChatCreateAt(String chatCreateAt) {
        this.chatCreateAt = chatCreateAt;
    }
    public String getLastMessageAt() {
        return lastMessageAt;
    }
    public void setLastMessageAt(String lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }
    public List<ChatLogs> getChatLogs() {
        return chatLogs;
    }
    public void setChatLogs(List<ChatLogs> chatLogs) {
        this.chatLogs = chatLogs;
    }
}
