package com.meta.emogi.views.chatlist;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.ChatListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListViewModel extends BaseViewModel {
    public ChatListViewModel(Application application) {super(application);}

    private final SingleLiveEvent<Void> _goToProfile = new SingleLiveEvent<>();
    private final MutableLiveData<List<ChatListModel>> _chatList = new MutableLiveData<>();

    public LiveData<Void> goToProfile(){
        return _goToProfile;
    }
    public LiveData<List<ChatListModel>> chatList(){return _chatList;}

    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.add_chat) {
            _goToProfile.call();
        }
    }

    public void getChatList(String accessToken){
        repository.getChatList(accessToken, new Callback<List<ChatListModel>>() {
            @Override
            public void onResponse(Call<List<ChatListModel>> call, Response<List<ChatListModel>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    _chatList.setValue(response.body());
                }
                else{
                    failLoading();
                    Log.e("www", "getChatList 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<List<ChatListModel>> call, Throwable t) {
                failLoading();
                Log.e("www", "getChatList API 호출 실패: " + t.getMessage());
            }
        });
    }

}