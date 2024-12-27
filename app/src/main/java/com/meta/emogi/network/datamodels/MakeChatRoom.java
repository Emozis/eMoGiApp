package com.meta.emogi.network.datamodels;
import com.google.gson.annotations.SerializedName;
public class MakeChatRoom {
    @SerializedName("message")
    private String message;
    @SerializedName("chatId")
    private int chatId;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getChatId() {
        return chatId;
    }
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
