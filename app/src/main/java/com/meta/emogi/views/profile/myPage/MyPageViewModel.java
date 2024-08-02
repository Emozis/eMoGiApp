package com.meta.emogi.views.profile.myPage;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;

public class MyPageViewModel extends BaseViewModel {

    private final SingleLiveEvent<Void> _goToMyPage = new SingleLiveEvent<>();
    private final MutableLiveData<String> _email = new MutableLiveData<>();
    private final MutableLiveData<String> _nickName = new MutableLiveData<>();

    public LiveData<Void> goToMyPage() {
        return _goToMyPage;
    }

    public LiveData<String> email() {
        return _email;
    }
    public LiveData<String> nickName() {
        return _nickName;
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
}

