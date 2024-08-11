package com.meta.emogi.network.datamodels;
import com.google.gson.annotations.SerializedName;
public class ChatLogModel {
    @SerializedName("logId")
    private int logId;

    @SerializedName("chatId")
    private int chatId;

    @SerializedName("role")
    private String role;

    @SerializedName("contents")
    private String contents;

    @SerializedName("logCreateAt")
    private String logCreateAt;

    // Getter and Setter methods

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getLogCreateAt() {
        return logCreateAt;
    }

    public void setLogCreateAt(String logCreateAt) {
        this.logCreateAt = logCreateAt;
    }
}
