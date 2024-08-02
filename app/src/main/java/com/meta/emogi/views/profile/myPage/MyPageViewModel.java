package com.meta.emogi.views.profile.myPage;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;

public class MyPageViewModel extends BaseViewModel {

    private final SingleLiveEvent<Void> _goToMyPage = new SingleLiveEvent<>();

    public LiveData<Void> goToMyPage(){
        return _goToMyPage;
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
}