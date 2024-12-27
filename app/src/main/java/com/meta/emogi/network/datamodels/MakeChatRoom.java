package com.meta.emogi.network.datamodels;
import com.google.gson.annotations.SerializedName;
public class MakeChatRoom {
    @SerializedName("message")
    private String message;
    @SerializedName("characterId")
    private int characterId;
    @SerializedName("data")
    private Data data;

    public MakeChatRoom(int characterId) {
        this.characterId = characterId;
    }

    public static class Data {
        @SerializedName("chatId")
        private int chatId;
        public int getChatId() {
            return chatId;
        }
        public void setChatId(int chatId) {
            this.chatId = chatId;
        }
    }
    public Data getData() {return data;}
    public void setData(Data data) {
        this.data = data;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
