package com.meta.persona.network;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface ApiService {
    @POST("api/auth/login/google/code")
    Call<Void> sendIdToken(@Body IdTokenRequest request);
}

