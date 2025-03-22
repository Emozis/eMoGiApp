package com.meta.emogi.network;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RetrofitClient {
//    private static final String SERVER_URL = "https://emogi.site/";
private static final String SERVER_URL = "http://122.128.54.136:8200/";

    public static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging) // HTTP 로그 출력
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .client(client) // OkHttpClient 추가
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
