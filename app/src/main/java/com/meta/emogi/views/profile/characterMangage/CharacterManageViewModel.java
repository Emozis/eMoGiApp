package com.meta.emogi.views.profile.characterMangage;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.CharacterModel;
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

    public LiveData<Void> goToMyPage() {
        return _goToMyPage;
    }
    public LiveData<List<CharacterModel>> myCharacterList() {
        return _myCharacterList;
    }
    public LiveData<Boolean> isActiveDeleteMode() {
        return _isActiveDeleteMode;
    }

    public void dummyCharacterModelList() {
        // CharacterModel 객체 리스트 선언
        List<CharacterModel> characterList = new ArrayList<>();

        // CharacterRelationship.Relationship 더미 데이터 생성
        CharacterModel.CharacterRelationship.Relationship relationship = new CharacterModel.CharacterRelationship.Relationship();
        relationship.setRelationshipId(1);
        relationship.setRelationshipName("Friend");

        // CharacterRelationship 더미 데이터 생성
        CharacterModel.CharacterRelationship characterRelationship = new CharacterModel.CharacterRelationship();
        characterRelationship.setRelationship(relationship);

        // User 더미 데이터 생성
        CharacterModel.User user = new CharacterModel.User();
        user.setUserId(1);
        user.setUserEmail("dummyuser@example.com");
        user.setUserName("Dummy User");
        user.setUserProfile("dummy_profile_url");

        // CharacterModel 더미 데이터 생성
        CharacterModel dummyCharacter = new CharacterModel(
                101, // characterId
                "Dummy Character", // characterName
                "dummy_profile_url", // characterProfile
                "Male", // characterGender
                "Brave", // characterPersonality
                "This is a detailed description of the dummy character.", // characterDetails
                List.of(characterRelationship), // characterRelationships
                "2024-12-26T12:00:00Z", // characterCreatedAt
                user, // user
                false // isSelected
        );

        // CharacterModel 객체 추가
        characterList.add(dummyCharacter);
        _myCharacterList.setValue(characterList);
        _isActiveDeleteMode.setValue(false);
    }

    public LiveData<String> deleteToggleString() {
        return _deleteToggleString;
    }

    public void getMyCharacters(String accessToken) {
        repository.getMyCharacterList(accessToken, new Callback<List<CharacterModel>>() {
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
            _deleteToggleString.setValue("삭제");
        }else{
            _deleteToggleString.setValue("완료");
        }
    }


}