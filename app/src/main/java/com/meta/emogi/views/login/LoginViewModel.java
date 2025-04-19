package com.meta.emogi.views.login;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.data.network.api.ApiCallBack;
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

        apiRepository.createAccessToken(requestToken, new ApiCallBack.ApiResultHandler<TokenModel>(){
            @Override
            public void onSuccess(TokenModel data) {
                Log.d(TAG, "accessToken: "+data.getAccessToken());
                _accessToken.setValue(data);
            }
            @Override
            public void onFailed(Throwable t) {
                failLoading();
            }
        });
    }

}