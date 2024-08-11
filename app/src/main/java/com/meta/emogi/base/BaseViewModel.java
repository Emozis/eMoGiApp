package com.meta.emogi.base;

import android.app.Application;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.views.toolbar.ToolbarViewModel;

public class BaseViewModel extends AndroidViewModel {

    private long buttonLastClickTime;
    private static final int CLICK_INTERVAL = 500;

    private final SingleLiveEvent<Integer> _buttonClicked = new SingleLiveEvent<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(true);

    public LiveData<Integer> buttonClicked() {
        return _buttonClicked;
    }
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public void onLoading(){
        _isLoading.setValue(true);
    }

    public void offLoading(){
        _isLoading.setValue(false);
    }

    public void onButtonClicked(View v) {
        if (System.currentTimeMillis() - buttonLastClickTime > CLICK_INTERVAL) {
            buttonLastClickTime = System.currentTimeMillis();
            _buttonClicked.setValue(v.getId());
        }
    }
}