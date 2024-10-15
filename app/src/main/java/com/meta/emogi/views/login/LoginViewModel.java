package com.meta.emogi.views.login;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.ImageModel;
import com.meta.emogi.network.datamodels.TokenModel;
import com.meta.emogi.network.datamodels.TokenModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel {

    private final MutableLiveData<TokenModel> _accessToken = new MutableLiveData<>();

    public LoginViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<TokenModel> accessToken() {
        return _accessToken;
    }

    public void createAccessToken(TokenModel requestToken) {
        repository.createAccessToken(requestToken, new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _accessToken.setValue(response.body());
                } else {
                    failLoading();
                    Log.e("www", "createAccessToken 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                failLoading();
                Log.e("www", "createAccessToken API 호출 실패: " + t.getMessage());
            }
        });
    }

}