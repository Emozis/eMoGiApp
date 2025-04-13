package com.meta.emogi.data.network.model;
import com.google.gson.annotations.SerializedName;
public class DeleteChatResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
