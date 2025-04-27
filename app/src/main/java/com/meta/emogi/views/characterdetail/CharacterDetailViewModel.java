package com.meta.emogi.views.characterdetail;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.data.network.api.ApiCallBack;
import com.meta.emogi.data.network.model.CharacterResponse;
import com.meta.emogi.data.network.model.CreateChatResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailViewModel extends BaseViewModel {

    private final MutableLiveData<String> _nameAndGender = new MutableLiveData<>("이름");
    private final MutableLiveData<String> _personality = new MutableLiveData<>("성격");
    private final MutableLiveData<String> _category = new MutableLiveData<>("카테고리");
    private final MutableLiveData<String> _detail = new MutableLiveData<>();
    private final SingleLiveEvent<Void> _isChatStart = new SingleLiveEvent<>();
    private final MutableLiveData<CharacterResponse> _characterDetail = new MutableLiveData<>();
    private final MutableLiveData<CreateChatResponse> _chatRoom = new MutableLiveData<>();

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
    public LiveData<CharacterResponse> characterDetail() {return _characterDetail;}
    public LiveData<CreateChatResponse> chatRoom() {return _chatRoom;}

    public void getCharacterDetailData(
            String nameAndGender,
            String personality,
            String category,
            String detail
    ) {
        _nameAndGender.setValue(nameAndGender);
        _personality.setValue(personality);
        _category.setValue(category);
        _detail.setValue(detail);
    }

    public void chatStart() {
        _isChatStart.call();
    }

    public void getCharacterDetails(int characterId) {
        loading();
        apiRepository.getCharacterDetails(characterId,
                                          new ApiCallBack.ApiResultHandler<CharacterResponse>() {
                                              @Override
                                              public void onSuccess(CharacterResponse data) {
                                                  loadingSuccess();
                                                  _characterDetail.setValue(data);
                                              }
                                              @Override
                                              public void onFailed(Throwable t) {
                                                  loadingFailed("캐릭터 상세 정보 가져오기 작업");
                                              }
                                          }
        );
    }

    public void connectCreateChatRoom(int characterId) {
        CreateChatResponse createChatResponse = new CreateChatResponse(characterId);
        createChatRoom(createChatResponse);
    }

    public void createChatRoom(CreateChatResponse createChatResponse) {
        loading();
        apiRepository.createChatRoom(
                createChatResponse,
                new ApiCallBack.ApiResultHandler<CreateChatResponse>() {
                    @Override
                    public void onSuccess(CreateChatResponse data) {
                        loadingSuccess();
                        _chatRoom.setValue(data);
                    }
                    @Override
                    public void onFailed(Throwable t) {
                        loadingFailed("채팅방 생성 작업");
                    }
                }
        );
    }

}