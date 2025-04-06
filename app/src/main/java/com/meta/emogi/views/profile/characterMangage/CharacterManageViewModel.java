package com.meta.emogi.views.profile.characterMangage;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.ResponseModel;
import com.meta.emogi.util.ApiRepository;
import com.meta.emogi.views.menu.MenuViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterManageViewModel extends BaseViewModel {
    private final SingleLiveEvent<Void> _goToMyPage = new SingleLiveEvent<>();
    private final MutableLiveData<List<CharacterModel>> _myCharacterList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isActiveDeleteMode = new MutableLiveData<>();
    private final MutableLiveData<String> _deleteToggleString = new MutableLiveData<>("삭제");
    private final SingleLiveEvent<Void> _deleteCharacter = new SingleLiveEvent<>();

    public LiveData<Void> goToMyPage() {
        return _goToMyPage;
    }

    public LiveData<Void> deleteCharacter() {
        return _deleteCharacter;
    }
    public LiveData<List<CharacterModel>> myCharacterList() {
        return _myCharacterList;
    }
    public LiveData<Boolean> isActiveDeleteMode() {
        return _isActiveDeleteMode;
    }

    public void setIsActiveDeleteMode(){
        _isActiveDeleteMode.setValue(false);
    }

    public LiveData<String> deleteToggleString() {
        return _deleteToggleString;
    }

    public void getMyCharacters() {
        repository.getMyCharacterList(new Callback<List<CharacterModel>>() {
            @Override
            public void onResponse(Call<List<CharacterModel>> call, Response<List<CharacterModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _myCharacterList.setValue(response.body());
                } else {
                    Log.e("www", "getMyCharacters 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<List<CharacterModel>> call, Throwable t) {
                Log.e("www", "getMyCharacters API 호출 실패: " + t.getMessage());
            }
        });
    }


    public void deleteCharacter(int characterId) {
        repository.deleteCharacter(characterId, new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CharacterModel> updatedList = new ArrayList<>(_myCharacterList.getValue());
                    for (int i = 0; i < updatedList.size(); i++) {
                        if (updatedList.get(i).getCharacterId() == characterId) {
                            updatedList.remove(i);
                            break;
                        }
                    }
                    _myCharacterList.setValue(updatedList); // 리스트 갱신
                    _deleteCharacter.call();
                } else {
                    Log.e("www", "deleteCharacter 응답이 정상적이지 않음");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("www", "deleteCharacter API 호출 실패: " + t.getMessage());
            }
        });
    }


    public CharacterManageViewModel(@NonNull Application application) {
        super(application);
    }
    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.my_page) {
            _goToMyPage.call();
        } else if (btnResId == R.id.toggle_delete) {
            boolean isDelete = !_isActiveDeleteMode.getValue();
            _isActiveDeleteMode.setValue(isDelete);
        }
    }

    public void setDeleteToggleString(boolean isDelete){
        if(isDelete){
            _deleteToggleString.setValue("완료");
        }else{
            _deleteToggleString.setValue("삭제");
        }
    }


}