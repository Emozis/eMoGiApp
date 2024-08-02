package com.meta.emogi.network;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.network.datamodels.MakeCharacterModel;
import com.meta.emogi.network.datamodels.TokenModel;
import com.meta.emogi.network.datamodels.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
public interface ApiService {
    @POST("api/v1/auth/login/google/id-token")
    Call<TokenModel> sendIdToken(@Body TokenModel request);

    @GET("api/v1/characters/me/")
    Call<List<CharacterModel>> getCharactersMe(@Header("Authorization") String accessToken);

    @GET("api/v1/characters/rank/")
    Call<List<CharacterModel>> getCharactersRank();

    @GET("api/v1/chat/me/")
    Call<List<ChatListModel>> getChatList(@Header("Authorization") String accessToken);

    @POST("api/v1/characters/")
    Call<MakeCharacterModel> createCharacter(@Header("Authorization") String accessToken, @Body
    MakeCharacterModel characterRequest);

    @GET("api/v1/user/me")
    Call<UserData> getUserData(@Header("Authorization") String accessToken);
}




