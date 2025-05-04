package com.meta.emogi.data.network.api;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCallBack<T> implements Callback<T> {

    private int retryCount = 1;
    private int maxRetryCount = 2;
    private final ApiResultHandler<T> handler;
    private final Call<T> originalCall;

    public ApiCallBack(Call<T> call, ApiResultHandler<T> handler, Integer maxRetryCount) {
        this.originalCall = call;
        this.handler = handler;
        this.maxRetryCount = maxRetryCount;
    }

    public ApiCallBack(Call<T> call, ApiResultHandler<T> handler) {
        this.originalCall = call;
        this.handler = handler;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            handler.onSuccess(response.body());
        } else if (retryCount < maxRetryCount) {
            handler.onRetry();
            Log.e(
                    "API_ERROR",
                    "API 응답 실패 / 통신 재시도" + retryCount + "회" + "API: " + call.request().url() + ", HTTP Code: " + response.code() + ", Error Body: " + getErrorBody(
                            response)
            );
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                originalCall.clone().enqueue(this);
            }, 3000);
            retryCount++;
        } else {
            retryCount = 0;
            Log.e(
                    "API_ERROR",
                    "총 " + maxRetryCount + " 회 통신시도 API 응답 실패  / API: " + call.request().url() + ", HTTP Code: " + response.code() + ", Error Body: " + getErrorBody(
                            response)
            );
            handler.onFailed(new Throwable("HTTP " + response.code() + " " + response.message()));
        }
    }
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d("www", "재시도" + retryCount + " " + "최대" + maxRetryCount);
        if (retryCount <= maxRetryCount) {
            handler.onRetry();
            Log.d("www", "통신 재시도" + retryCount + "회");
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                originalCall.clone().enqueue(this);
            }, 3000);
            retryCount++;
        } else {
            retryCount = 0;
            Log.e("API_ERROR", "통신 실패 API: " + call.request().url() + ", 에러: " + t.getMessage());
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

        void onRetry();
    }
}
