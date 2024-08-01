package com.meta.emogi.base;

import android.app.Application;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BaseViewModel extends AndroidViewModel {

    private long buttonLastClickTime;
    private static final int CLICK_INTERVAL = 500;

    private final SingleLiveEvent<Integer> _buttonClicked = new SingleLiveEvent<>();

    public LiveData<Integer> buttonClicked() {
        return _buttonClicked;
    }

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public void onButtonClicked(View v) {
        if (System.currentTimeMillis() - buttonLastClickTime > CLICK_INTERVAL) {
            buttonLastClickTime = System.currentTimeMillis();
            _buttonClicked.setValue(v.getId());
        }
    }
}