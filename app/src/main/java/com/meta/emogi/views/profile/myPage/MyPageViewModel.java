package com.meta.emogi.views.profile.myPage;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageViewModel extends BaseViewModel {

    private final SingleLiveEvent<Void> _goToMyPage = new SingleLiveEvent<>();
    private final MutableLiveData<String> _email = new MutableLiveData<>();
    private final MutableLiveData<String> _nickName = new MutableLiveData<>();
    private final MutableLiveData<UserData> _userData = new MutableLiveData<>();

    public LiveData<Void> goToMyPage() {
        return _goToMyPage;
    }
    public LiveData<String> email() {
        return _email;
    }
    public LiveData<String> nickName() {
        return _nickName;
    }
    public LiveData<UserData> userData() {
        return _userData;
    }

    public MyPageViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.manage_character) {
            _goToMyPage.call();
        }
    }

    public void setUserData(String email, String nickName) {
        _email.setValue(email);
        _nickName.setValue(nickName);
    }

    public void getUserData(String accessToken) {
        repository.getUserData(accessToken, new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _userData.setValue(response.body());
                }else{
                    Log.e("www", "getUserData 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.e("www", "getUserData API 호출 실패: " + t.getMessage());
            }
        });
    }
}

