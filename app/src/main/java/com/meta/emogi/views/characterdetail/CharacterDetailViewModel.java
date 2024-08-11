package com.meta.emogi.views.characterdetail;

import android.app.Application;
import android.text.Spanned;

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

    private final MutableLiveData<String> _nameAndGender = new MutableLiveData<>("이름");
    private final MutableLiveData<String> _personality = new MutableLiveData<>("성격");
    private final MutableLiveData<String> _category = new MutableLiveData<>("카테고리");
//    private final MutableLiveData<String> _detail = new MutableLiveData<>("세부사항");
    private final MutableLiveData<Spanned> _detail = new MutableLiveData<>();
    private final SingleLiveEvent<Void> _isChatStart = new SingleLiveEvent<>();

    public LiveData<String> nameAndGender() {
        return _nameAndGender;
    }

    public LiveData<String> personality() {
        return _personality;
    }
    public LiveData<String> category() {
        return _category;
    }

//    public LiveData<String> detail() {
//        return _detail;
//    }

    public LiveData<Spanned> detail() {
        return _detail;
    }

    public LiveData<Void> isChatStart() {return _isChatStart;}

//    public void getCharacterDetailData(String nameAndGender, String personality, String category, String detail) {
//        _nameAndGender.setValue(nameAndGender);
//        _personality.setValue(personality);
//        _category.setValue(category);
//        _detail.setValue(detail);
//    }

    public void getCharacterDetailData(String nameAndGender, String personality, String category, Spanned detail) {
        _nameAndGender.setValue(nameAndGender);
        _personality.setValue(personality);
        _category.setValue(category);
        _detail.setValue(detail);
    }

    public void chatStart() {
        _isChatStart.call();
    }
}