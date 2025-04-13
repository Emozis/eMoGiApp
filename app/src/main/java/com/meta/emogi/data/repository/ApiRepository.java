package com.meta.emogi.data.repository;
import com.meta.emogi.data.network.api.ApiService;
import com.meta.emogi.data.network.api.RetrofitClient;
import com.meta.emogi.data.network.model.CharacterResponse;
import com.meta.emogi.domain.TokenManager;
import com.meta.emogi.data.network.model.ChatResponse;
import com.meta.emogi.data.network.model.ChatLogResponse;
import com.meta.emogi.data.network.model.CharacterImageResponse;
import com.meta.emogi.data.network.model.CreateChatResponse;
import com.meta.emogi.data.network.model.ResponseModel;
import com.meta.emogi.data.network.model.TokenModel;
import com.meta.emogi.data.network.model.UserData;
import com.meta.emogi.data.network.model.DeleteChatResponse;

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
    public void getMyCharacterList(Callback<List<CharacterResponse>> callback) {
        apiService.getMyCharacterList(token).enqueue(callback);
    }

    //Menu
    public void getRankCharacterList(Callback<List<CharacterResponse>> callback) {
        apiService.getRankCharacterList().enqueue(callback);
    }

    //profile_myPage
    public void getUserData(Callback<UserData> callback) {
        apiService.getUserData(token).enqueue(callback);
    }

    //makeCharacter
    public void getDefaultRelationshipList(Callback<List<CharacterResponse.CharacterRelationships>> callback) {
        apiService.getDefaultRelationshipList().enqueue(callback);
    }

    //makeCharacter
    public void getDefaultImageList(Callback<List<CharacterImageResponse>> callback) {
        apiService.getDefaultImageList().enqueue(callback);
    }

    //chatRoom
    public void getChatLogList(
            int chatId,
            Callback<List<ChatLogResponse>> callback
    ) {
        apiService.getChatLog(token, chatId).enqueue(callback);
    }

    //chatList
    public void getChatList(Callback<List<ChatResponse>> callback) {
        apiService.getChatList(token).enqueue(callback);
    }

    //deletechat
    public void deleteChat(int chatId, Callback<DeleteChatResponse> callback) {
        apiService.deleteChat(token, chatId).enqueue(callback);
    }

    //characterDetail
    public void getCharacterDetails(
            int characterId,
            Callback<CharacterResponse> callback
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
            CharacterResponse characterResponse,
            int characterId,
            Callback<CharacterResponse> callback
    ) {
        apiService.updateCharacter(token, characterResponse, characterId).enqueue(callback);
    }

    //// POST

    //makeCharacter
    public void createCharacter(
            CharacterResponse characterResponse,
            Callback<CharacterResponse> callback
    ) {
        apiService.createCharacter(token, characterResponse).enqueue(callback);
    }

    //login
    public void createAccessToken(TokenModel requestToken, Callback<TokenModel> callback) {
        apiService.createAccessToken(requestToken).enqueue(callback);
    }

    //characterDetail
    public void createChatRoom(
            CreateChatResponse createChatResponse,
            Callback<CreateChatResponse> callback
    ) {
        apiService.createChatRoom(token, createChatResponse).enqueue(callback);
    }

}
