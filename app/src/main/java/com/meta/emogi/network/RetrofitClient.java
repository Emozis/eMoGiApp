package com.meta.emogi.network;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RetrofitClient {
    private static final String SERVER_URL = "http://122.128.54.136:8200/";

    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
