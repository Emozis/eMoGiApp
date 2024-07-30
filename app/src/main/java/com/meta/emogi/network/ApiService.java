package com.meta.emogi.network;
import com.meta.emogi.network.datamodels.TokenModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface ApiService {
    @POST("api/v1/auth/login/google/id-token")
    Call<TokenModel> sendIdToken(@Body TokenModel request);
}

