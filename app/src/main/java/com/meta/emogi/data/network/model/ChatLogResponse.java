package com.meta.emogi.data.network.model;
import com.google.gson.annotations.SerializedName;
public class ChatLogResponse {
    @SerializedName("logId")
    private int logId;

    @SerializedName("character")
    private Character character;

    @SerializedName("role")
    private String role;

    @SerializedName("contents")
    private String contents;

    @SerializedName("logCreateAt")
    private String logCreateAt;

    public static class Character{
        @SerializedName("characterId")
        private String characterId;

        @SerializedName("characterName")
        private String characterName;

        @SerializedName("characterProfile")
        private String characterProfile;


    }



    // Getter and Setter methods

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
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
