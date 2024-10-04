package com.meta.emogi.views.menu;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.util.ApiRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuViewModel extends BaseViewModel {
    private final MutableLiveData<MoveType> _type = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isMyLoading = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> _isRankLoading = new MutableLiveData<>(true);
    private final SingleLiveEvent<Void> _menu2ManageProfile = new SingleLiveEvent<>();

    private final MutableLiveData<List<CharacterModel>> _myCharacterList = new MutableLiveData<>();
    private final MutableLiveData<List<CharacterModel>> _rankCharacterList = new MutableLiveData<>();

    public LiveData<MoveType> type() {
        return _type;
    }
    public LiveData<Boolean> isMyLoading() {
        return _isMyLoading;
    }
    public LiveData<Boolean> isRankLoading() {
        return _isRankLoading;
    }
    public LiveData<Void> menu2ManageProfile() {
        return _menu2ManageProfile;
    }
    public LiveData<List<CharacterModel>> myCharacterList(){
        return _myCharacterList;
    }
    public LiveData<List<CharacterModel>> rankCharacterList(){
        return _rankCharacterList;
    }

    public MenuViewModel(Application application) {
        super(application);
    }

    public void getMyCharacters(String accessToken) {
        Log.d("www", accessToken);
        repository.getMyCharacterList(accessToken, new Callback<List<CharacterModel>>() {
            @Override
            public void onResponse(Call<List<CharacterModel>> call, Response<List<CharacterModel>> response) {
                if(response.isSuccessful()){
                    _myCharacterList.setValue(response.body());
                }else {
                    // HTTP 상태 코드와 에러 메시지 로그 추가
                    int statusCode = response.code();
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // 로그에 상태 코드와 에러 메시지 출력
                    Log.e("www", "getMyCharacters 응답이 정상적이지 않음. 상태 코드: " + statusCode + ", 에러 본문: " + errorBody);
                }
            }
            @Override
            public void onFailure(Call<List<CharacterModel>> call, Throwable t) {
                Log.e("www", "getMyCharacters API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void getRankCharacterList(){
        repository.getRankCharacterList(new Callback<List<CharacterModel>>() {
            @Override
            public void onResponse(Call<List<CharacterModel>> call, Response<List<CharacterModel>> response) {
                if(response.isSuccessful()){
                    _rankCharacterList.setValue(response.body());
                }else{
                    Log.e("www", "getRankCharacters 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<List<CharacterModel>> call, Throwable t) {
                Log.e("www", "getRankCharacters API 호출 실패: " + t.getMessage());
            }
        });
    }

    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.move_to_chat_list) {
            _type.setValue(MoveType.CHAT_LIST);
        } else if (btnResId == R.id.move_to_make_character) {
            _type.setValue(MoveType.MAKE_CHARACTER);
        } else if (btnResId == R.id.button_go_manage_profile) {
            _menu2ManageProfile.call();
        }
    }

    public void loadDoneMy() {
        _isMyLoading.setValue(false);
    }

    public void loadDoneRank() {
        _isRankLoading.setValue(false);
    }

    public enum MoveType {
        CHAT_LIST, MAKE_CHARACTER
    }

}