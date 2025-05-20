package com.meta.emogi.data.network.api;
import com.meta.emogi.data.network.model.CharacterResponse;
import com.meta.emogi.data.network.model.ChatResponse;
import com.meta.emogi.data.network.model.ChatLogResponse;
import com.meta.emogi.data.network.model.CreateChatResponse;
import com.meta.emogi.data.network.model.DeleteChatResponse;
import com.meta.emogi.data.network.model.ResponseModel;
import com.meta.emogi.data.network.model.TokenModel;
import com.meta.emogi.data.network.model.UserData;
import com.meta.emogi.data.network.model.CharacterImageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface ApiService {

    //로그인 권한 관련 api
    @POST("api/v2/auth/login/google")
    Call<TokenModel> createAccessToken(@Body TokenModel accessToken);

    //유저 관련 api
    @GET("api/v1/user/me")
    Call<UserData> getUserData(@Header("Authorization") String token);

    //캐릭터 관련 api
    @POST("api/v1/character")
    Call<CharacterResponse> createCharacter(@Header("Authorization") String token, @Body
    CharacterResponse characterResponse
    );

    @GET("api/v1/character/rank")
    Call<List<CharacterResponse>> getRankCharacterList();

    @GET("api/v1/character/me")
    Call<List<CharacterResponse>> getMyCharacterList(@Header("Authorization") String token);

    @GET("api/v1/character/{character_id}")
    Call<CharacterResponse> getCharacterDetails(@Header("Authorization") String token, @Path("character_id") int characterId);

    @PUT("api/v1/character/{character_id}")
    Call<CharacterResponse> updateCharacter(@Header("Authorization") String token, @Body
    CharacterResponse characterResponse, @Path("character_id") int characterId);

    @PATCH("api/v1/character/{character_id}/deactive")
    Call<ResponseModel> deactivateCharacter(@Header("Authorization") String token, @Path("character_id") int characterId);

    //기본 캐릭터 이미지 관련 api
    @GET("api/v1/default-image")
    Call<List<CharacterImageResponse>> getDefaultImageList();

    //캐릭터,유저 사이 관계 api
    @GET("api/v1/relationship")
    Call<List<CharacterResponse.CharacterRelationships>> getDefaultRelationshipList();

    //채팅방 관련 api
    @POST("api/v1/chat")
    Call<CreateChatResponse> createChatRoom(@Header("Authorization") String token, @Body
    CreateChatResponse createChatResponse
    );

    @GET("api/v1/chat/me")
    Call<List<ChatResponse>> getChatList(@Header("Authorization") String token);

    @DELETE("api/v1/chat/{chat_id}")
    Call<DeleteChatResponse> deleteChat(@Header("Authorization") String token, @Path("chat_id") int chat_id);

    //채팅 로그 관련 api
    @GET("api/v1/chat-log/chats/{chat_id}")
    Call<List<ChatLogResponse>> getChatLog(@Header("Authorization") String token, @Path("chat_id") int chatId);

}




