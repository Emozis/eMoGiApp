package com.meta.emogi.base;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.MyApplication;
import com.meta.emogi.data.repository.ApiRepository;
import com.meta.emogi.views.loading.LoadingViewModel;

public class BaseViewModel extends AndroidViewModel {

    public enum LoadingState {
        DEFAULT,LOADING, SUCCESS, FAILED
    }

    private long buttonLastClickTime;
    private static final int CLICK_INTERVAL = 500;

    private LoadingViewModel loadingViewModel;
    protected ApiRepository apiRepository;
    private final MutableLiveData<LoadingState> _loadingState = new MutableLiveData<>(LoadingState.DEFAULT);
    private final SingleLiveEvent<Integer> _buttonClicked = new SingleLiveEvent<>();
    private final MutableLiveData<String> _loadingMessage = new MutableLiveData<>("");

    public LiveData<String> loadingMessage() {
        return _loadingMessage;
    }

    public LiveData<LoadingState> loadingState() {
        return _loadingState;
    }

    public void setLoadingViewModel(LoadingViewModel loadingViewModel) {
        this.loadingViewModel = loadingViewModel;
    }

    public void loading() {
        if (loadingViewModel != null) {
            _loadingState.postValue(LoadingViewModel.LoadingState.LOADING);
            _loadingMessage.postValue("데이터 로딩중입니다.");
        }
    }

    public void loadingFailed(String error) {
        if (loadingViewModel != null) {
            _loadingState.postValue(LoadingViewModel.LoadingState.FAILED);
            _loadingMessage.postValue(error +" 중 데이터 로딩에 실패하였습니다.\n네트워크를 확인해주세요");
        }
    }

    public void loadingSuccess() {
        if (loadingViewModel != null) {
            _loadingState.postValue(LoadingViewModel.LoadingState.SUCCESS);
            _loadingMessage.postValue("");
        }
    }

    public LiveData<Integer> buttonClicked() {
        return _buttonClicked;
    }

    public BaseViewModel() {
        super(MyApplication.getInstance());
        apiRepository = new ApiRepository();
    }


    public void onButtonClicked(View v) {
        if (System.currentTimeMillis() - buttonLastClickTime > CLICK_INTERVAL) {
            buttonLastClickTime = System.currentTimeMillis();
            _buttonClicked.setValue(v.getId());
        }
    }
}