package com.meta.emogi.util;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.network.datamodels.ChatLogModel;
import com.meta.emogi.network.datamodels.ImageModel;
import com.meta.emogi.network.datamodels.MakeCharacterModel;
import com.meta.emogi.network.datamodels.MakeChatRoom;
import com.meta.emogi.network.datamodels.RelationshipModel;
import com.meta.emogi.network.datamodels.TokenModel;
import com.meta.emogi.network.datamodels.UserData;
import com.meta.emogi.network.datamodels.MessageResponse;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
public class ApiRepository {
    private ApiService apiService;

    public ApiRepository(){
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);
    }


    /// GET

    //Menu, profile_characterMange
    public void getMyCharacterList(String accessToken, Callback<List<CharacterModel>> callback){
        apiService.getMyCharacterList(accessToken).enqueue(callback);
    }

    //Menu
    public void getRankCharacterList(Callback<List<CharacterModel>> callback){
        apiService.getRankCharacterList().enqueue(callback);
    }

    //profile_myPage
    public void getUserData(String accessToken,Callback<UserData> callback){
        apiService.getUserData(accessToken).enqueue(callback);
    }

    //makeCharacter
    public void getDefaultRelationshipList(Callback<List<RelationshipModel>> callback){
        apiService.getDefaultRelationshipList().enqueue(callback);
    }

    //makeCharacter
    public void getDefaultImageList(Callback<List<ImageModel>> callback){
        apiService.getDefaultImageList().enqueue(callback);
    }

    //chatRoom
    public void getChatLogList(String accessToken,int chatId, Callback<List<ChatLogModel>> callback){
        apiService.getChatLog(accessToken,chatId).enqueue(callback);
    }

    //chatList
    public void getChatList(String accessToken,Callback<List<ChatListModel>> callback){
        apiService.getChatList(accessToken).enqueue(callback);
    }

    //deletechat
    public void deleteChat(String accessToken,int chatId,Callback<MessageResponse> callback){
        apiService.deleteChat(accessToken,chatId).enqueue(callback);
    }

    //characterDetail
    public void getCharacterDetails(String accessToken, int characterId, Callback<CharacterModel> callback){
        apiService.getCharacterDetails(accessToken,characterId).enqueue(callback);
    }


    //// POST

    //makeCharacter
    public void createCharacter(String accessToken, MakeCharacterModel characterModel, Callback<MakeCharacterModel> callback){
        apiService.createCharacter(accessToken,characterModel).enqueue(callback);
    }

    //login
    public void createAccessToken(TokenModel requestToken, Callback<TokenModel> callback){
        apiService.createAccessToken(requestToken).enqueue(callback);
    }

    //characterDetail
    public void createChatRoom(String accessToken,int characterId, Callback<MakeChatRoom> callback){
        apiService.createChatRoom(accessToken,characterId).enqueue(callback);
    }











}
