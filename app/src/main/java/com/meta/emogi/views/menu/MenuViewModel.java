package com.meta.emogi.views.menu;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;

public class MenuViewModel extends BaseViewModel {
    public MenuViewModel(Application application) {super(application);}
    private final MutableLiveData<MoveType> _type = new MutableLiveData<>();

    LiveData<MoveType> type(){
        return _type;
    }

    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.move_to_chat_list) {
            _type.setValue(MoveType.CHAT_LIST);
        } else if (btnResId == R.id.move_to_make_character) {
            _type.setValue(MoveType.MAKE_CHARACTER);
        }
    }

    public enum MoveType{
        CHAT_LIST,
        MAKE_CHARACTER
    }

}