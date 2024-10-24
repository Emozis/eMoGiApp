package com.meta.emogi.network;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.network.datamodels.ChatLogModel;
import com.meta.emogi.network.datamodels.MakeCharacterModel;
import com.meta.emogi.network.datamodels.MakeChatRoom;
import com.meta.emogi.network.datamodels.MessageResponse;
import com.meta.emogi.network.datamodels.RelationshipModel;
import com.meta.emogi.network.datamodels.TokenModel;
import com.meta.emogi.network.datamodels.UserData;
import com.meta.emogi.network.datamodels.ImageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ApiService {
    @POST("api/v1/auth/login/google/id-token")
    Call<TokenModel> createAccessToken(@Body TokenModel request);

    @GET("api/v1/characters/me/")
    Call<List<CharacterModel>> getMyCharacterList(@Header("Authorization") String accessToken);

    @GET("api/v1/characters/rank/")
    Call<List<CharacterModel>> getRankCharacterList();

    @GET("api/v1/chat/me/")
    Call<List<ChatListModel>> getChatList(@Header("Authorization") String accessToken);

    @DELETE("api/v1/chat/{chat_id}")
    Call<MessageResponse> deleteChat(@Header("Authorization") String accessToken, @Path("chat_id") int chat_id);

    @POST("api/v1/characters/")
    Call<MakeCharacterModel> createCharacter(@Header("Authorization") String accessToken, @Body
    MakeCharacterModel characterModel);

    @GET("api/v1/user/me")
    Call<UserData> getUserData(@Header("Authorization") String accessToken);

    @GET("api/v1/default-images/")
    Call<List<ImageModel>> getDefaultImageList();

    @GET("api/v1/relationship/")
    Call<List<RelationshipModel>> getDefaultRelationshipList();

    @GET("api/v1/characters/{character_id}")
    Call<CharacterModel> getCharacterDetails(@Header("Authorization") String accessToken, @Path("character_id") int characterId);

    @POST("api/v1/chat")
    Call<MakeChatRoom> createChatRoom(
            @Header("Authorization") String accessToken,
            @Query("character_id") int characterId
    );

    @GET("api/v1/chat-log/me/{chat_id}")
    Call<List<ChatLogModel>> getChatLog(@Header("Authorization") String accessToken, @Path("chat_id") int chatId);


}




