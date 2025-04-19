package com.meta.emogi.base;

import android.app.Application;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.data.repository.ApiRepository;

public class BaseViewModel extends AndroidViewModel {

    private long buttonLastClickTime;
    private static final int CLICK_INTERVAL = 500;
    protected ApiRepository apiRepository;

    private final SingleLiveEvent<Integer> _buttonClicked = new SingleLiveEvent<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> _isShowProgress = new MutableLiveData<>(true);
    private final MutableLiveData<String> _loadingText = new MutableLiveData<>("데이터 로딩중입니다.");

    public LiveData<Integer> buttonClicked() {
        return _buttonClicked;
    }
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }
    public LiveData<String> loadingText() {
        return _loadingText;
    }

    public LiveData<Boolean> isShowProgress() {
        return _isShowProgress;
    }


    public BaseViewModel(@NonNull Application application) {
        super(application);
        apiRepository = new ApiRepository();
    }

    public void onLoading(){
        _isLoading.setValue(true);
    }

    public void offLoading(){
        _isLoading.setValue(false);
    }

    public void failLoading(){
        _isShowProgress.setValue(false);
        _loadingText.setValue("데이터 로딩에 실패하였습니다.\n네트워크를 확인해주세요");
    }

    public void onButtonClicked(View v) {
        if (System.currentTimeMillis() - buttonLastClickTime > CLICK_INTERVAL) {
            buttonLastClickTime = System.currentTimeMillis();
            _buttonClicked.setValue(v.getId());
        }
    }
}