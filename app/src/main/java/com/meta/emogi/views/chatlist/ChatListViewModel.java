package com.meta.emogi.views.chatlist;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;

import java.util.List;

public class ChatListViewModel extends BaseViewModel {
    public ChatListViewModel(Application application) {super(application);}

    private final SingleLiveEvent<Void> _goToProfile = new SingleLiveEvent<>();

    public LiveData<Void> goToProfile(){
        return _goToProfile;
    }

    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.add_chat) {
            _goToProfile.call();
        }
    }

}