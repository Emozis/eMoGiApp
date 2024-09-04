package com.meta.emogi.views.menu;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.util.ApiRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuViewModel extends BaseViewModel {
    private final MutableLiveData<MoveType> _type = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isMyLoading = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> _isRankLoading = new MutableLiveData<>(true);
    private final SingleLiveEvent<Void> _menu2ManageProfile = new SingleLiveEvent<>();

    private ApiRepository repository;
    private final MutableLiveData<List<CharacterModel>> _myCharacters = new MutableLiveData<>();
    private final MutableLiveData<List<CharacterModel>> _rankCharacters = new MutableLiveData<>();

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
    public LiveData<List<CharacterModel>> myCharacters(){
        return _myCharacters;
    }
    public LiveData<List<CharacterModel>> rankCharacters(){
        return _rankCharacters;
    }

    public MenuViewModel(Application application) {
        super(application);
        repository = new ApiRepository();
    }

    public void getMyCharacters(String authToken) {
        repository.getMyCharacters(authToken, new Callback<List<CharacterModel>>() {
            @Override
            public void onResponse(Call<List<CharacterModel>> call, Response<List<CharacterModel>> response) {
                if(response.isSuccessful()){
                    _myCharacters.setValue(response.body());
                }else{
                    _myCharacters.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<List<CharacterModel>> call, Throwable t) {
                _myCharacters.setValue(null);
            }
        });
    }

    public void getRankCharacters(){
        repository.getRankCharacters(new Callback<List<CharacterModel>>() {
            @Override
            public void onResponse(Call<List<CharacterModel>> call, Response<List<CharacterModel>> response) {
                if(response.isSuccessful()){
                    _rankCharacters.setValue(response.body());
                }else{
                    _rankCharacters.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<List<CharacterModel>> call, Throwable t) {
                _rankCharacters.setValue(null);
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