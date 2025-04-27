package com.meta.emogi.data.network.api;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCallBack<T> implements Callback<T>{

    private int retryCount = 0;
    private int maxRetryCount = 0;
    private final ApiResultHandler<T> handler;
    private final Call<T> originalCall;

    public ApiCallBack(Call<T> call, ApiResultHandler<T> handler,Integer maxRetryCount){
        this.originalCall = call;
        this.handler = handler;
        this.maxRetryCount = maxRetryCount;
    }

    public ApiCallBack(Call<T> call, ApiResultHandler<T> handler){
        this.originalCall = call;
        this.handler = handler;
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){
            handler.onSuccess(response.body());
        }else{
            Log.e("API_ERROR", "응답 실패 API: " + call.request().url()
                    + ", HTTP Code: " + response.code()
                    + ", Error Body: " + getErrorBody(response));
            handler.onFailed(new Throwable("HTTP " + response.code() + " " + response.message()));
        }
    }
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                originalCall.clone().enqueue(this);
            }, 3000);
        } else {
            Log.e("API_ERROR", "통신 실패 API: " + call.request().url()
                    + ", 에러: " + t.getMessage());
            handler.onFailed(t);
        }
    }

    private String getErrorBody(Response<T> response) {
        try {
            if (response.errorBody() != null) {
                return response.errorBody().string();
            }
        } catch (Exception e) {
            return "Error parsing errorBody";
        }
        return "No error body";
    }

    public interface ApiResultHandler<T> {
        void onSuccess(T data);
        void onFailed(Throwable t);
    }
}
