package com.meta.emogi.views.profile.myPage;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.data.network.api.ApiCallBack;
import com.meta.emogi.data.network.model.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.meta.emogi.data.repository.ApiRepository;

import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import com.meta.emogi.data.repository.ApiRepository;

@HiltViewModel
public class MyPageViewModel extends BaseViewModel {

    @Inject
    public MyPageViewModel() {
        super();
    }

    private static final String TAG = "MyPageViewModel";

    private final SingleLiveEvent<Void> _goToMyPage = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> _goToEditProfile = new SingleLiveEvent<>();

    private final MutableLiveData<String> _email = new MutableLiveData<>();
    private final MutableLiveData<String> _nickName = new MutableLiveData<>();
    private final MutableLiveData<UserData> _userData = new MutableLiveData<>();

    public LiveData<Void> goToMyPage() {
        return _goToMyPage;
    }

    public LiveData<Void> goToEditProfile() {
        return _goToEditProfile;
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

    @Override
    public boolean onButtonClicked(View v) {
        if (!super.onButtonClicked(v)) {
            return false;
        }
        int btnResId = v.getId();
        if (btnResId == R.id.manage_character) {
            _goToMyPage.call();
        }else if(btnResId == R.id.btn_edit_profile){
            _goToEditProfile.call();
        }

        return true;
    }

    public void setUserData(String email, String nickName) {
        _email.setValue(email);
        _nickName.setValue(nickName);
    }

    public void getUserData() {
        loading();
        apiRepository.getUserData(new ApiCallBack.ApiResultHandler<UserData>() {
            @Override
            public void onSuccess(UserData data) {
                loadingSuccess();
                _userData.setValue(data);
            }

            @Override
            public void onFailed(Throwable t) {
                loadingFailed("유저 데이터 가져오기 작업");
            }

            @Override
            public void onRetry() {
                loadingRetry();
            }
        });
    }
}
