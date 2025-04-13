package com.meta.emogi.data.network.model;
public class ResponseModel {
    private String message;
    private Data data;

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private int characterId;

        public int getCharacterId() {
            return characterId;
        }
    }
}
