package com.meta;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface ApiService {
    @POST("your_endpoint_here")
    Call<Void> sendIdToken(@Body IdTokenRequest request);
}

