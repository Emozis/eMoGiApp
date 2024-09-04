package com.meta.emogi.util;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.CharacterModel;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
public class ApiRepository {
    private ApiService apiService;

    public ApiRepository(){
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);
    }

    //Menu
    public void getMyCharacters(String authToken, Callback<List<CharacterModel>> callback){
        apiService.getMyCharacters(authToken).enqueue(callback);
    }

    public void getRankCharacters(Callback<List<CharacterModel>> callback){
        apiService.getRankCharacters().enqueue(callback);
    }



}
