package com.meta.emogi.views.menu;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;

public class MenuViewModel extends BaseViewModel {
    public MenuViewModel(Application application) {super(application);}
    private final MutableLiveData<MoveType> _type = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final SingleLiveEvent<Void> _menu2ManageProfile = new SingleLiveEvent<>();

    LiveData<MoveType> type(){
        return _type;
    }
    LiveData<Boolean> isLoading(){
        return _isLoading;
    }
    LiveData<Void> menu2ManageProfile(){
        return _menu2ManageProfile;
    }

    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.move_to_chat_list) {
            _type.setValue(MoveType.CHAT_LIST);
        } else if (btnResId == R.id.move_to_make_character) {
            _type.setValue(MoveType.MAKE_CHARACTER);
        } else if (btnResId == R.id.button_go_manage_profile) {
            _menu2ManageProfile.call();
        }
    }

    public enum MoveType{
        CHAT_LIST,
        MAKE_CHARACTER
    }



}