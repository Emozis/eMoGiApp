package com.meta.emogi.views.login;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;

public class LoginViewModel extends BaseViewModel {

    private final SingleLiveEvent<Void> _isClicked = new SingleLiveEvent<>();

    public LiveData<Void> isClicked() { return _isClicked;}

    public LoginViewModel(Application application) {
        super(application);
    }

    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if(btnResId == R.id.login_button){
            _isClicked.call();
        }
    }

}