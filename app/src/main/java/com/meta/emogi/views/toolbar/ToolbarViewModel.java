package com.meta.emogi.views.toolbar;
import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
public class ToolbarViewModel extends BaseViewModel {

    private final MutableLiveData<String> _title = new MutableLiveData<>();
    private final MutableLiveData<String> _buttonText = new MutableLiveData<>("임시저장");
    private final MutableLiveData<Boolean> _visibleSaveButton = new MutableLiveData<>(false);
    private final SingleLiveEvent<Void> _back = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> _logout = new SingleLiveEvent<>();

    public LiveData<Boolean> visibleSaveButton() {
        return _visibleSaveButton;
    }

    public void setVisibleSaveButton(boolean isVisible){
        _visibleSaveButton.setValue(isVisible);
    }

    public LiveData<String> title() {
        return _title;
    }
    public LiveData<String> buttonText() {
        return _buttonText;
    }

    public void setTitle(String title) {
        _title.setValue(title);
    }
    public LiveData<Void> back() {
        return _back;
    }
    public LiveData<Void> logout() {
        return _logout;
    }

    public boolean onButtonClicked(View v) {
        if (!super.onButtonClicked(v)) {
            return false;
        }
        int btnResId = v.getId();
        if (btnResId == R.id.back) {
            _back.call();
        }else if (btnResId == R.id.logout) {
            _logout.call();
        }
        return true;
    }

    public void setButtonText(String buttonText) {_buttonText.setValue(buttonText);}
}
