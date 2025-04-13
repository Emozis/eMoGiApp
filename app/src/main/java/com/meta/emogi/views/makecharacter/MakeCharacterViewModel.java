package com.meta.emogi.views.makecharacter;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.data.network.model.CharacterResponse;
import com.meta.emogi.data.network.model.CharacterImageResponse;

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
    public final MutableLiveData<List<CharacterResponse.CharacterRelationships>> _defaultRelationshipList = new MutableLiveData<>();
    public final MutableLiveData<List<CharacterImageResponse>> _defaultImageList = new MutableLiveData<>();
    public final MutableLiveData<CharacterResponse> _createdCharacter = new MutableLiveData<>();
    public final MutableLiveData<CharacterResponse> _currentCharacterData = new MutableLiveData<>();

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

    public LiveData<List<CharacterResponse.CharacterRelationships>> defaultRelationshipList() {
        return _defaultRelationshipList;
    }

    public LiveData<List<CharacterImageResponse>> defaultImageList() {
        return _defaultImageList;
    }

    public LiveData<CharacterResponse> createdCharacter() {
        return _createdCharacter;
    }
    public LiveData<CharacterResponse> currentCharacterData() {
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
        Log.w(TAG, "onButtonClicked: ");
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
        apiRepository.getDefaultRelationshipList(new Callback<List<CharacterResponse.CharacterRelationships>>() {
            @Override
            public void onResponse(Call<List<CharacterResponse.CharacterRelationships>> call, Response<List<CharacterResponse.CharacterRelationships>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _defaultRelationshipList.setValue(response.body());
                    for (CharacterResponse.CharacterRelationships r : response.body()) {
                        Log.w(TAG, r.getRelationshipName());
                    }
                } else {
                    failLoading();
                    Log.e("www", "getDefaultRelationshipList 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<List<CharacterResponse.CharacterRelationships>> call, Throwable t) {
                failLoading();
                Log.e("www", "getDefaultRelationshipList API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void getDefaultImageList() {
        apiRepository.getDefaultImageList(new Callback<List<CharacterImageResponse>>() {
            @Override
            public void onResponse(Call<List<CharacterImageResponse>> call, Response<List<CharacterImageResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _defaultImageList.setValue(response.body());
                } else {
                    failLoading();
                    Log.e("www", "getDefaultImageList 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<List<CharacterImageResponse>> call, Throwable t) {
                failLoading();
                Log.e("www", "getDefaultImageList API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void createCharacter(CharacterResponse characterResponse) {
        Log.w("www", "characterModel: " + new Gson().toJson(characterResponse));
        apiRepository.createCharacter(characterResponse, new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {

                Log.w("www", "Response Code: " + response.code());
                Log.w("www", "Response Message: " + response.message());
                Log.w("www", "Response Body: " + new Gson().toJson(response.body()));

                if (response.isSuccessful() && response.body() != null) {
                    _createdCharacter.setValue(response.body());
                } else {
                    failLoading();
                    Log.e("www", "createCharacter 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                failLoading();
                Log.e("www", "createCharacter API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void updateCharacter(CharacterResponse characterResponse, int characterId) {
        Log.w("www", "characterModel: " + new Gson().toJson(characterResponse));
        apiRepository.updateCharacter(characterResponse, characterId, new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                Log.w("www", "Response Code: " + response.code());
                Log.w("www", "Response Message: " + response.message());
                Log.w("www", "Response Body: " + new Gson().toJson(response.body()));

                if (response.isSuccessful() && response.body() != null) {
                    _createdCharacter.setValue(response.body());
                } else {
                    failLoading();
                    Log.e("www", "updateCharacter 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                failLoading();
                Log.e("www", "updateCharacter API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void getCharacterDetails( int characterId) {
        apiRepository.getCharacterDetails(characterId, new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transformCharacterModel(response.body());
                } else {
                    failLoading();
                    Log.e("www", "getCharacterDetails 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                failLoading();
                Log.e("www", "getCharacterDetails API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void transformCharacterModel(CharacterResponse characterResponse) {
        _currentCharacterData.setValue( new CharacterResponse(
                characterResponse.getCharacterName(),
                characterResponse.getCharacterProfile(),
                characterResponse.getCharacterGender(),
                characterResponse.getCharacterPersonality(),
                characterResponse.getCharacterDetails(),
                "Default Description",
                "Default Greeting",
                true,
                characterResponse.getCharacterRelationships()
        ));
    }

    public void setCurrentCharaterData(CharacterResponse currentCharaterData) {
        name.setValue(currentCharaterData.getCharacterName());
        personality.setValue(currentCharaterData.getCharacterPersonality());
        detail.setValue(currentCharaterData.getCharacterDetails());
        _isMan.setValue(currentCharaterData.getCharacterGender() == "male" ? true : false);
    }


    public CharacterResponse getCurrentCharacterData(String selectedImgUrl, String gender, List<CharacterResponse.CharacterRelationships> relationships) {
        String characterDescription = "";
        String characterGreeting = "";
        return new CharacterResponse(name.getValue(), selectedImgUrl, gender, personality.getValue(), detail.getValue(), characterDescription, characterGreeting, isOpen().getValue(), relationships);
    }

}

