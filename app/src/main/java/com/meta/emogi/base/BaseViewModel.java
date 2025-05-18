package com.meta.emogi.base;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.MyApplication;
import com.meta.emogi.R;
import com.meta.emogi.data.repository.ApiRepository;

public class BaseViewModel extends AndroidViewModel {

    public enum LoadingState {
        DEFAULT, LOADING,RETRY, SUCCESS, FAILED
    }

    private long buttonLastClickTime;
    private static final int CLICK_INTERVAL = 500;

    protected ApiRepository apiRepository;
    private final MutableLiveData<LoadingState> _loadingState = new MutableLiveData<>(LoadingState.DEFAULT);
    private final SingleLiveEvent<Integer> _buttonClicked = new SingleLiveEvent<>();
    private final MutableLiveData<String> _loadingMessage = new MutableLiveData<>("");
    private final SingleLiveEvent<Void> _goToInquiry = new SingleLiveEvent<>();

    public LiveData<String> loadingMessage() {
        return _loadingMessage;
    }

    public LiveData<LoadingState> loadingState() {
        return _loadingState;
    }

    public LiveData<Void> goToInquiryPage() {
        return _goToInquiry;
    }

    public void loading() {
            _loadingState.postValue(LoadingState.LOADING);
            _loadingMessage.postValue("데이터 로딩중입니다.");
    }

    public void loadingFailed(String error) {
            _loadingState.postValue(LoadingState.FAILED);
            _loadingMessage.postValue(error +" 중\n 데이터 로딩에 실패하였습니다.\n\n증상이 반복된다면 문의 해주시면 감사하겠습니다.");
    }

    public void loadingSuccess() {
            _loadingState.postValue(LoadingState.SUCCESS);
            _loadingMessage.postValue("");
    }

    public void loadingRetry(){
        _loadingState.postValue(LoadingState.RETRY);
        _loadingMessage.postValue("데이터 로딩에 문제가 생겼습니다.\n 재시도 중입니다.\n\n잠시만 기다려주세요.");
    }

    public LiveData<Integer> buttonClicked() {
        return _buttonClicked;
    }

    public BaseViewModel() {
        super(MyApplication.getInstance());
        apiRepository = new ApiRepository();
    }


    public boolean onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.go_to_inquiry) {
            Log.d("www", "onButtonClicked: ");
            _goToInquiry.call();
        }
        if (System.currentTimeMillis() - buttonLastClickTime > CLICK_INTERVAL) {
            buttonLastClickTime = System.currentTimeMillis();
            return true; // 클릭 허용
        }

        return false; // 클릭 무시
    }
}