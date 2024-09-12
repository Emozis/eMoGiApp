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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterManageViewModel extends BaseViewModel {
    private final SingleLiveEvent<Void> _goToMyPage = new SingleLiveEvent<>();
    private final MutableLiveData<List<CharacterModel>> _myCharacterList = new MutableLiveData<>();

    public LiveData<List<CharacterModel>> myCharacterList() {
        return _myCharacterList;
    }
    public LiveData<Void> goToMyPage() {
        return _goToMyPage;
    }

    public void getMyCharacters(String accessToken) {
        repository.getMyCharacterList(accessToken, new Callback<List<CharacterModel>>() {
            @Override
            public void onResponse(Call<List<CharacterModel>> call, Response<List<CharacterModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _myCharacterList.setValue(response.body());
                }else{
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
        }
    }
}