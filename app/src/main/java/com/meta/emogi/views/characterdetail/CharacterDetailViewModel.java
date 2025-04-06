package com.meta.emogi.views.characterdetail;

import android.app.Application;
import android.text.Spanned;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.CharacterUserModel;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.network.datamodels.MakeChatRoom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailViewModel extends BaseViewModel {
    public CharacterDetailViewModel(@NonNull Application application) {
        super(application);
    }

    private final MutableLiveData<String> _nameAndGender = new MutableLiveData<>("이름");
    private final MutableLiveData<String> _personality = new MutableLiveData<>("성격");
    private final MutableLiveData<String> _category = new MutableLiveData<>("카테고리");
    private final MutableLiveData<String> _detail = new MutableLiveData<>();
    private final SingleLiveEvent<Void> _isChatStart = new SingleLiveEvent<>();
    private final MutableLiveData<CharacterModel> _characterDetail = new MutableLiveData<>();
    private final MutableLiveData<MakeChatRoom> _chatRoom = new MutableLiveData<>();

    public LiveData<String> nameAndGender() {
        return _nameAndGender;
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
    public LiveData<CharacterModel> characterDetail() {return _characterDetail;}
    public LiveData<MakeChatRoom> chatRoom() {return _chatRoom;}


    public void getCharacterDetailData(String nameAndGender, String personality, String category, String detail) {
        _nameAndGender.setValue(nameAndGender);
        _personality.setValue(personality);
        _category.setValue(category);
        _detail.setValue(detail);
    }

    public void chatStart() {
        _isChatStart.call();
    }

    public void getCharacterDetails( int characterId){
        repository.getCharacterDetails(characterId, new Callback<CharacterModel>() {
            @Override
            public void onResponse(Call<CharacterModel> call, Response<CharacterModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _characterDetail.setValue(response.body());
                    offLoading();
                } else {
                    failLoading();
                    Log.e("www", "getCharacterDetails 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<CharacterModel> call, Throwable t) {
                failLoading();
                Log.e("www", "getCharacterDetails API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void connectCreateChatRoom(int characterId){
        MakeChatRoom makeChatRoom =  new MakeChatRoom(characterId);
        createChatRoom(makeChatRoom);
    }

    public void createChatRoom(MakeChatRoom makeChatRoom){
        repository.createChatRoom(makeChatRoom, new Callback<MakeChatRoom>() {
            @Override
            public void onResponse(Call<MakeChatRoom> call, Response<MakeChatRoom> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _chatRoom.setValue(response.body());
                    offLoading();
                } else {
                    failLoading();
                    Log.e("www", "getCharacterDetails 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<MakeChatRoom> call, Throwable t) {
                Log.e("www", "getCharacterDetails API 호출 실패: " + t.getMessage());
            }
        });
    }

}