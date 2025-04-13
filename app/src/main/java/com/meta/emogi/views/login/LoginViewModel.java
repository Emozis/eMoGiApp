package com.meta.emogi.views.login;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.data.network.model.TokenModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel {

    private static final String TAG = "LoginViewModel";
    private final MutableLiveData<TokenModel> _accessToken = new MutableLiveData<>();
    private final MutableLiveData<String> _appVersion = new MutableLiveData<>();

    public LoginViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<TokenModel> accessToken() {
        return _accessToken;
    }

    public MutableLiveData<String> appVersion() {
        return _appVersion;
    }

    public void setAppVersion(String version) {
        _appVersion.setValue(version);
    }

    public void createAccessToken(TokenModel requestToken) {
        apiRepository.createAccessToken(requestToken, new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _accessToken.setValue(response.body());
                } else {
                    failLoading();
                    try {
                        if (response.errorBody() != null) {
                            Log.e(
                                    TAG,
                                    "createAccessToken 응답이 정상적이지 않음. "
                                            + "Status Code: " + response.code()
                                            + ", Headers: " + response.headers()
                                            + ", Error Body: " + response.errorBody()
                            );
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing errorBody: " + e.getMessage());
                    }

                }
            }
            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                failLoading();
                Log.e(TAG, "createAccessToken API 호출 실패: " + t.getMessage());
            }
        });
    }

}