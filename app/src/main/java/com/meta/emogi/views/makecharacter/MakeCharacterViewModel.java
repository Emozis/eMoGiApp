package com.meta.emogi.views.makecharacter;

import android.app.Application;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.data.network.api.ApiCallBack;
import com.meta.emogi.data.network.model.CharacterResponse;
import com.meta.emogi.data.network.model.CharacterImageResponse;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeCharacterViewModel extends BaseViewModel {
    private static final String TAG = "MakeCharacterViewModel";

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

    // <이미지, 관계>
    public final MutableLiveData<Pair<Boolean,Boolean>> _isInitialDataLoaded = new MutableLiveData<>(new Pair<>(false,false));

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

    public LiveData<Pair<Boolean,Boolean>> isInitialDataLoaded() {
        return _isInitialDataLoaded;
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
    public boolean onButtonClicked(View v) {
        if (!super.onButtonClicked(v)) {
            return false;
        }
        Log.w(TAG, "onButtonClicked: ");
        int btnResId = v.getId();
        if (btnResId == R.id.generate_btn) {
            _generate.call();
        } else if (btnResId == R.id.gender_man) {
            _isMan.setValue(true);
        } else if (btnResId == R.id.gender_woman) {
            _isMan.setValue(false);
        } else if (btnResId == R.id.iv_is_open) {
            _isOpen.setValue(!_isOpen.getValue());
        }
        return true;
    }

    public void dataReset() {
        name.setValue("");
        personality.setValue("");
        detail.setValue("");
        _isMan.setValue(true);
    }

    public void getDefaultRelationshipList() {
        loading();
        apiRepository.getDefaultRelationshipList(new ApiCallBack.ApiResultHandler<List<CharacterResponse.CharacterRelationships>>() {
            @Override
            public void onSuccess(List<CharacterResponse.CharacterRelationships> data) {
                _defaultRelationshipList.setValue(data);
                loadingSuccess();
                Pair<Boolean,Boolean> currentState=_isInitialDataLoaded.getValue();
                _isInitialDataLoaded.postValue(new Pair<>(true,currentState.second));
            }
            @Override
            public void onFailed(Throwable t) {
                loadingFailed("관계 가져오기 작업");
            }
            @Override
            public void onRetry() {
                loadingRetry();
            }
        });
    }

    public void getDefaultImageList() {
        loading();
        apiRepository.getDefaultImageList(new ApiCallBack.ApiResultHandler<List<CharacterImageResponse>>() {
            @Override
            public void onSuccess(List<CharacterImageResponse> data) {
                _defaultImageList.setValue(data);
                loadingSuccess();
                Pair<Boolean,Boolean> currentState=_isInitialDataLoaded.getValue();
                _isInitialDataLoaded.postValue(new Pair<>(currentState.first,true));
            }
            @Override
            public void onFailed(Throwable t) {
                loadingFailed("이미지 가져오기 작업");
            }
            @Override
            public void onRetry() {
                loadingRetry();
            }
        });
    }

    public void createCharacter(CharacterResponse characterResponse) {
        loading();
        Log.w("www", "characterModel: " + new Gson().toJson(characterResponse));
        apiRepository.createCharacter(characterResponse, new ApiCallBack.ApiResultHandler<CharacterResponse>() {
            @Override
            public void onSuccess(CharacterResponse data) {
                Log.w("www", "Response Body: " + new Gson().toJson(data));
                _createdCharacter.setValue(data);
                loadingSuccess();
            }
            @Override
            public void onFailed(Throwable t) {
                loadingFailed("캐릭터 생성하기 작업");
            }
            @Override
            public void onRetry() {
                loadingRetry();
            }
        });
    }

    public void updateCharacter(CharacterResponse characterResponse, int characterId) {
        loading();
        Log.w("www", "characterModel: " + new Gson().toJson(characterResponse));
        apiRepository.updateCharacter(characterResponse, characterId, new ApiCallBack.ApiResultHandler<CharacterResponse>() {
            @Override
            public void onSuccess(CharacterResponse data) {
                _createdCharacter.setValue(data);
                loadingSuccess();
            }
            @Override
            public void onFailed(Throwable t) {
                loadingFailed("캐릭터 수정하기 작업");
            }
            @Override
            public void onRetry() {
                loadingRetry();
            }
        });
    }

    public void getCharacterDetails( int characterId) {
        loading();
        apiRepository.getCharacterDetails(characterId, new ApiCallBack.ApiResultHandler<CharacterResponse>() {
            @Override
            public void onSuccess(CharacterResponse data) {
                transformCharacterModel(data);
                loadingSuccess();
            }
            @Override
            public void onFailed(Throwable t) {
                loadingFailed("캐릭터 상세정보 가져오기 작업");
            }
            @Override
            public void onRetry() {
                loadingRetry();
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

