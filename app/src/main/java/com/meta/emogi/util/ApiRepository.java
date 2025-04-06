package com.meta.emogi.util;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.TokenManager;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.network.datamodels.ChatLogModel;
import com.meta.emogi.network.datamodels.ImageModel;
import com.meta.emogi.network.datamodels.MakeChatRoom;
import com.meta.emogi.network.datamodels.ResponseModel;
import com.meta.emogi.network.datamodels.TokenModel;
import com.meta.emogi.network.datamodels.UserData;
import com.meta.emogi.network.datamodels.MessageResponse;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
public class ApiRepository {
    private ApiService apiService;

    String token = TokenManager.getInstance().getTotken();

    public ApiRepository() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);
    }

    /// GET

    //Menu, profile_characterMange
    public void getMyCharacterList(Callback<List<CharacterModel>> callback) {
        apiService.getMyCharacterList(token).enqueue(callback);
    }

    //Menu
    public void getRankCharacterList(Callback<List<CharacterModel>> callback) {
        apiService.getRankCharacterList().enqueue(callback);
    }

    //profile_myPage
    public void getUserData(Callback<UserData> callback) {
        apiService.getUserData(token).enqueue(callback);
    }

    //makeCharacter
    public void getDefaultRelationshipList(Callback<List<CharacterModel.CharacterRelationships>> callback) {
        apiService.getDefaultRelationshipList().enqueue(callback);
    }

    //makeCharacter
    public void getDefaultImageList(Callback<List<ImageModel>> callback) {
        apiService.getDefaultImageList().enqueue(callback);
    }

    //chatRoom
    public void getChatLogList(
            int chatId,
            Callback<List<ChatLogModel>> callback
    ) {
        apiService.getChatLog(token, chatId).enqueue(callback);
    }

    //chatList
    public void getChatList(Callback<List<ChatListModel>> callback) {
        apiService.getChatList(token).enqueue(callback);
    }

    //deletechat
    public void deleteChat(int chatId, Callback<MessageResponse> callback) {
        apiService.deleteChat(token, chatId).enqueue(callback);
    }

    //characterDetail
    public void getCharacterDetails(
            int characterId,
            Callback<CharacterModel> callback
    ) {
        apiService.getCharacterDetails(token, characterId).enqueue(callback);
    }

    public void deleteCharacter(
            int characterId,
            Callback<ResponseModel> callback
    ) {
        apiService.deleteCharacter(token, characterId).enqueue(callback);
    }

    //PUT
    public void updateCharacter(
            CharacterModel characterModel,
            int characterId,
            Callback<CharacterModel> callback
    ) {
        apiService.updateCharacter(token, characterModel, characterId).enqueue(callback);
    }

    //// POST

    //makeCharacter
    public void createCharacter(
            CharacterModel characterModel,
            Callback<CharacterModel> callback
    ) {
        apiService.createCharacter(token, characterModel).enqueue(callback);
    }

    //login
    public void createAccessToken(TokenModel requestToken, Callback<TokenModel> callback) {
        apiService.createAccessToken(requestToken).enqueue(callback);
    }

    //characterDetail
    public void createChatRoom(
            MakeChatRoom makeChatRoom,
            Callback<MakeChatRoom> callback
    ) {
        apiService.createChatRoom(token, makeChatRoom).enqueue(callback);
    }

}
