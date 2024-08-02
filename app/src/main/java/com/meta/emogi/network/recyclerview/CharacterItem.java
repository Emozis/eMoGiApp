package com.meta.emogi.network.recyclerview;
public class CharacterItem {
    private int imageResId;
    private String characterName;
    private String characterDescription;

    public CharacterItem(int imageResId, String characterName, String characterDescription) {
        this.imageResId = imageResId;
        this.characterName = characterName;
        this.characterDescription = characterDescription;
    }
    public int getImageResId() {
        return imageResId;
    }
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
    public String getCharacterName() {
        return characterName;
    }
    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
    public String getCharacterDescription() {
        return characterDescription;
    }
    public void setCharacterDescription(String characterDescription) {
        this.characterDescription = characterDescription;
    }


}
