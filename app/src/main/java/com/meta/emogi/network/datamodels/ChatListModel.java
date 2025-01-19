package com.meta.emogi.network.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatListModel {

    @SerializedName("chatId")
    private int chatId;

    @SerializedName("user")
    private User user;

    @SerializedName("character")
    private Character character;

    @SerializedName("chatCreateAt")
    private String chatCreateAt;

    @SerializedName("lastMessage")
    private String lastMessage;

    @SerializedName("lastMessageAt")
    private String lastMessageAt;

    @SerializedName("isEmptyChat")
    private Boolean isEmptyChat;

    @SerializedName("lastLog")
    private LastLog chatLogs;



    public static class LastLog {
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

    public static class User {
        @SerializedName("userId")
        private int userId;

        @SerializedName("userName")
        private String userName;

        @SerializedName("userProfile")
        private String userProfile;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

    public static class Character {
        @SerializedName("characterId")
        private int characterId;

        @SerializedName("characterName")
        private String characterName;

        @SerializedName("characterProfile")
        private String characterProfile;

        public int getCharacterId() {
            return characterId;
        }

        public void setCharacterId(int characterId) {
            this.characterId = characterId;
        }

        public String getCharacterName() {
            return characterName;
        }

        public void setCharacterName(String characterName) {
            this.characterName = characterName;
        }

        public String getCharacterProfile() {
            return characterProfile;
        }

        public void setCharacterProfile(String characterProfile) {
            this.characterProfile = characterProfile;
        }
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getChatCreateAt() {
        return chatCreateAt;
    }

    public void setChatCreateAt(String chatCreateAt) {
        this.chatCreateAt = chatCreateAt;
    }

    public String getLastMessage() {
        return lastMessage;
    }
    public Boolean getEmptyChat() {
        return isEmptyChat;
    }
    public void setEmptyChat(Boolean emptyChat) {
        isEmptyChat = emptyChat;
    }
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
    public String getLastMessageAt() {
        return lastMessageAt;
    }

    public void setLastMessageAt(String lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }

    public LastLog getLastLog() {
        return chatLogs;
    }

    public void setLastLog(LastLog chatLogs) {
        this.chatLogs = chatLogs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}