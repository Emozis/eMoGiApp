package com.meta.emogi.views.characterdetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;

public class CharacterDetailViewModel extends BaseViewModel {
    public CharacterDetailViewModel(@NonNull Application application) {
        super(application);
    }

    private final MutableLiveData<String> _name = new MutableLiveData<>("이름");
    private final MutableLiveData<String> _personality = new MutableLiveData<>("성격");
    private final MutableLiveData<String> _category = new MutableLiveData<>("카테고리");
    private final MutableLiveData<String> _detail = new MutableLiveData<>("세부사항");
    private final SingleLiveEvent<Void> _isChatStart = new SingleLiveEvent<>();

    public LiveData<String> name() {
        return _name;
    }

    public LiveData<String> personality() {
        return _personality;
    }
    public LiveData<String> category() {
        return _category;
    }

    public LiveData<String> detail() {
        return _detail;
    }

    public LiveData<Void> isChatStart() {return _isChatStart;}

    public void getCharacterDetailData(String name, String personality, String category, String detail) {
        _name.setValue(name);
        _personality.setValue(personality);
        _category.setValue(category);
        _detail.setValue(detail);
    }

    public void chatStart() {
        _isChatStart.call();
    }
}