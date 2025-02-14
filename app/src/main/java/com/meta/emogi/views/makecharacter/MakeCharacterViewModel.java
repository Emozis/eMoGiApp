package com.meta.emogi.views.makecharacter;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.ImageModel;
import com.meta.emogi.network.datamodels.RelationshipModel;
import com.meta.emogi.views.menu.MenuViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeCharacterViewModel extends BaseViewModel {
    private static final String TAG = "MakeCharacterViewModel";
    public MakeCharacterViewModel(Application application) {
        super(application);
    }
    public final MutableLiveData<String> name = new MutableLiveData<>("");
    public final MutableLiveData<String> personality = new MutableLiveData<>("");
    public final MutableLiveData<String> detail = new MutableLiveData<>("");
    public final MutableLiveData<String> _editBtn = new MutableLiveData<>("캐릭터 생성하기");
    public final MutableLiveData<Boolean> _isEdit = new MutableLiveData<>(false);
    public final MutableLiveData<Boolean> _isMan = new MutableLiveData<>(true);
    public final SingleLiveEvent<Void> _generate = new SingleLiveEvent<>();
    public final MutableLiveData<Boolean> _isOpen = new MutableLiveData<>(true);
    public final MutableLiveData<List<CharacterModel.CharacterRelationships>> _defaultRelationshipList = new MutableLiveData<>();
    public final MutableLiveData<List<ImageModel>> _defaultImageList = new MutableLiveData<>();
    public final MutableLiveData<CharacterModel> _createdCharacter = new MutableLiveData<>();
    public final MutableLiveData<CharacterModel> _currentCharacterData = new MutableLiveData<>();

    public LiveData<Boolean> isMan() {
        return _isMan;
    }
    public LiveData<Void> generate() {
        return _generate;
    }
    public LiveData<Boolean> isOpen() {
        return _isOpen;
    }

    public LiveData<String> editBtn() {
        return _editBtn;
    }
    public LiveData<Boolean> isEdit() {
        return _isEdit;
    }

    public LiveData<List<CharacterModel.CharacterRelationships>> defaultRelationshipList() {
        return _defaultRelationshipList;
    }

    public LiveData<List<ImageModel>> defaultImageList() {
        return _defaultImageList;
    }

    public LiveData<CharacterModel> createdCharacter() {
        return _createdCharacter;
    }
    public LiveData<CharacterModel> currentCharacterData() {
        return _currentCharacterData;
    }

    public void setIsEdit(boolean isEdit) {
        _isEdit.setValue(isEdit);
        if (isEdit) {
            _editBtn.setValue("캐릭터 수정하기");
        } else {
            _editBtn.setValue("캐릭터 생성하기");
        }
    }

    @Override
    public void onButtonClicked(View v) {
        Log.d(TAG, "onButtonClicked: ");
        int btnResId = v.getId();
        if (btnResId == R.id.generate) {
            _generate.call();
        } else if (btnResId == R.id.gender_man) {
            _isMan.setValue(true);
        } else if (btnResId == R.id.gender_woman) {
            _isMan.setValue(false);
        } else if (btnResId == R.id.iv_is_open) {
            _isOpen.setValue(!_isOpen.getValue());
        }
    }

    public void dataReset() {
        name.setValue("");
        personality.setValue("");
        detail.setValue("");
        _isMan.setValue(true);
    }

    public void getDefaultRelationshipList() {
        repository.getDefaultRelationshipList(new Callback<List<CharacterModel.CharacterRelationships>>() {
            @Override
            public void onResponse(Call<List<CharacterModel.CharacterRelationships>> call, Response<List<CharacterModel.CharacterRelationships>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _defaultRelationshipList.setValue(response.body());
                    for (CharacterModel.CharacterRelationships r : response.body()) {
                        Log.d(TAG, r.getRelationshipName());
                    }
                } else {
                    failLoading();
                    Log.e("www", "getDefaultRelationshipList 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<List<CharacterModel.CharacterRelationships>> call, Throwable t) {
                failLoading();
                Log.e("www", "getDefaultRelationshipList API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void getDefaultImageList() {
        repository.getDefaultImageList(new Callback<List<ImageModel>>() {
            @Override
            public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _defaultImageList.setValue(response.body());
                } else {
                    failLoading();
                    Log.e("www", "getDefaultImageList 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                failLoading();
                Log.e("www", "getDefaultImageList API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void createCharacter(String accessToken, CharacterModel characterModel) {
        Log.d("www", "accessToken: " + accessToken);
        Log.d("www", "characterModel: " + new Gson().toJson(characterModel));
        repository.createCharacter(accessToken, characterModel, new Callback<CharacterModel>() {
            @Override
            public void onResponse(Call<CharacterModel> call, Response<CharacterModel> response) {

                Log.d("www", "Response Code: " + response.code());
                Log.d("www", "Response Message: " + response.message());
                Log.d("www", "Response Body: " + new Gson().toJson(response.body()));

                if (response.isSuccessful() && response.body() != null) {
                    _createdCharacter.setValue(response.body());
                } else {
                    failLoading();
                    Log.e("www", "createCharacter 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<CharacterModel> call, Throwable t) {
                failLoading();
                Log.e("www", "createCharacter API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void updateCharacter(String accessToken, CharacterModel characterModel, int characterId) {
        Log.d("www", "accessToken: " + accessToken);
        Log.d("www", "characterModel: " + new Gson().toJson(characterModel));
        repository.updateCharacter(accessToken, characterModel, characterId, new Callback<CharacterModel>() {
            @Override
            public void onResponse(Call<CharacterModel> call, Response<CharacterModel> response) {
                Log.d("www", "Response Code: " + response.code());
                Log.d("www", "Response Message: " + response.message());
                Log.d("www", "Response Body: " + new Gson().toJson(response.body()));

                if (response.isSuccessful() && response.body() != null) {
                    _createdCharacter.setValue(response.body());
                } else {
                    failLoading();
                    Log.e("www", "updateCharacter 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<CharacterModel> call, Throwable t) {
                failLoading();
                Log.e("www", "updateCharacter API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void getCharacterDetails(String accessToken, int characterId) {
        repository.getCharacterDetails(accessToken, characterId, new Callback<CharacterModel>() {
            @Override
            public void onResponse(Call<CharacterModel> call, Response<CharacterModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transformCharacterModel(response.body());
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

    public void transformCharacterModel(CharacterModel characterModel) {
        _currentCharacterData.setValue( new CharacterModel(
                characterModel.getCharacterName(),
                characterModel.getCharacterProfile(),
                characterModel.getCharacterGender(),
                characterModel.getCharacterPersonality(),
                characterModel.getCharacterDetails(),
                "Default Description",
                "Default Greeting",
                true,
                characterModel.getCharacterRelationships()
        ));
    }

    public void setCurrentCharaterData(CharacterModel currentCharaterData) {
        name.setValue(currentCharaterData.getCharacterName());
        personality.setValue(currentCharaterData.getCharacterPersonality());
        detail.setValue(currentCharaterData.getCharacterDetails());
        _isMan.setValue(currentCharaterData.getCharacterGender() == "male" ? true : false);
    }


    public CharacterModel getCurrentCharacterData(String selectedImgUrl, String gender, List<CharacterModel.CharacterRelationships> relationships) {
        String characterDescription = "";
        String characterGreeting = "";
        return new CharacterModel(name.getValue(), selectedImgUrl, gender, personality.getValue(), detail.getValue(), characterDescription, characterGreeting, isOpen().getValue(), relationships);
    }

}

