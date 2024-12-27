package com.meta.emogi.views.makecharacter;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.ImageModel;
import com.meta.emogi.network.datamodels.MakeCharacterModel;
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
    public final MutableLiveData<Boolean> _isMan = new MutableLiveData<>(true);
    public final MutableLiveData<String> _category = new MutableLiveData<>("");
    public final SingleLiveEvent<Void> _generate = new SingleLiveEvent<>();
    public final MutableLiveData<Boolean> _isOpen = new MutableLiveData<>(true);
    public final MutableLiveData<List<RelationshipModel>> _defaultRelationshipList = new MutableLiveData<>();
    public final MutableLiveData<List<ImageModel>> _defaultImageList = new MutableLiveData<>();
    public final MutableLiveData<MakeCharacterModel> _createdCharacter = new MutableLiveData<>();

    public LiveData<Boolean> isMan() {
        return _isMan;
    }
    public LiveData<String> category() {
        return _category;
    }
    public LiveData<Void> generate() {
        return _generate;
    }
    public LiveData<Boolean> isOpen() {
        return _isOpen;
    }

    public LiveData<List<RelationshipModel>> defaultRelationshipList() {
        return _defaultRelationshipList;
    }

    public LiveData<List<ImageModel>> defaultImageList() {
        return _defaultImageList;
    }

    public LiveData<MakeCharacterModel> createdCharacter() {
        return _createdCharacter;
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
        _category.setValue("");
    }

    public void getDefaultRelationshipList() {
        repository.getDefaultRelationshipList(new Callback<List<RelationshipModel>>() {
            @Override
            public void onResponse(Call<List<RelationshipModel>> call, Response<List<RelationshipModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _defaultRelationshipList.setValue(response.body());
                    for (RelationshipModel r : response.body()) {
                        Log.d(TAG, r.getRelationshipName());
                    }
                } else {
                    failLoading();
                    Log.e("www", "getDefaultRelationshipList 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<List<RelationshipModel>> call, Throwable t) {
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

    public void createCharacter(String accessToken, MakeCharacterModel characterModel) {
        repository.createCharacter(accessToken, characterModel, new Callback<MakeCharacterModel>() {
            @Override
            public void onResponse(Call<MakeCharacterModel> call, Response<MakeCharacterModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _createdCharacter.setValue(response.body());
                } else {
                    failLoading();
                    Log.e("www", "createCharacter 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<MakeCharacterModel> call, Throwable t) {
                failLoading();
                Log.e("www", "createCharacter API 호출 실패: " + t.getMessage());
            }
        });
    }

    public MakeCharacterModel getSelectedCharacterOption(String selectedImgUrl, String gender, ArrayList<Integer> relationships) {
        return new MakeCharacterModel(name.getValue(), selectedImgUrl, gender, personality.getValue(), detail.getValue(), isOpen().getValue(), relationships);
    }

}

