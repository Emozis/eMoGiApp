package com.meta.emogi.views.profile.characterMangage;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.views.menu.MenuViewModel;

public class CharacterManageViewModel extends BaseViewModel {
    private final SingleLiveEvent<Void> _goToMyPage = new SingleLiveEvent<>();

    public LiveData<Void> goToMyPage(){
        return _goToMyPage;
    }

    public CharacterManageViewModel(@NonNull Application application) {
        super(application);
    }
    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.my_page) {
            _goToMyPage.call();
        }
    }
}