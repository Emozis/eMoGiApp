package com.meta.emogi.network;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.TokenModel;

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
    Call<List<CharacterModel>> getCharactersMe(@Header("Authorization") String authToken);

    @GET("api/v1/characters/rank/")
    Call<List<CharacterModel>> getCharactersRank();

}

