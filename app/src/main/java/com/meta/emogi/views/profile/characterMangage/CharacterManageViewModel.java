package com.meta.emogi.views.profile.characterMangage;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.data.network.api.ApiCallBack;
import com.meta.emogi.data.network.model.CharacterResponse;
import com.meta.emogi.data.network.model.ResponseModel;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterManageViewModel extends BaseViewModel {
    private final SingleLiveEvent<Void> _goToMyPage = new SingleLiveEvent<>();
    private final MutableLiveData<List<CharacterResponse>> _myCharacterList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isActiveDeleteMode = new MutableLiveData<>();
    private final MutableLiveData<String> _deleteToggleString = new MutableLiveData<>("삭제");
    private final SingleLiveEvent<Void> _deleteCharacter = new SingleLiveEvent<>();

    public LiveData<Void> goToMyPage() {
        return _goToMyPage;
    }

    public LiveData<Void> deleteCharacter() {
        return _deleteCharacter;
    }
    public LiveData<List<CharacterResponse>> myCharacterList() {
        return _myCharacterList;
    }
    public LiveData<Boolean> isActiveDeleteMode() {
        return _isActiveDeleteMode;
    }

    public void setIsActiveDeleteMode() {
        _isActiveDeleteMode.setValue(false);
    }

    public LiveData<String> deleteToggleString() {
        return _deleteToggleString;
    }

    public void getMyCharacters() {
        loading();
        apiRepository.getMyCharacterList(new ApiCallBack.ApiResultHandler<List<CharacterResponse>>() {
            @Override
            public void onSuccess(List<CharacterResponse> data) {
                loadingSuccess();
                _myCharacterList.setValue(data);
            }

            @Override
            public void onFailed(Throwable t) {
                loadingFailed("내 캐릭터 가져오기 작업");
                Log.e("www", "getMyCharacters 실패: " + t.getMessage());
            }
            @Override
            public void onRetry() {
                loadingRetry();
            }
        });
    }

    public void deleteCharacter(int characterId) {
        loading();
        apiRepository.deleteCharacter(characterId,
                                      new ApiCallBack.ApiResultHandler<ResponseModel>() {
                                          @Override
                                          public void onSuccess(ResponseModel data) {
                                              loadingSuccess();
                                              List<CharacterResponse> updatedList = new ArrayList<>(
                                                      _myCharacterList.getValue());
                                              for (int i = 0; i < updatedList.size(); i++) {
                                                  if (updatedList.get(i).getCharacterId() == characterId) {
                                                      updatedList.remove(i);
                                                      break;
                                                  }
                                              }
                                              _myCharacterList.setValue(updatedList); // 리스트 갱신
                                              _deleteCharacter.call();
                                          }
                                          @Override
                                          public void onFailed(Throwable t) {
                                              loadingFailed("캐릭터 삭제 작업");
                                          }

                                          @Override
                                          public void onRetry() {
                                              loadingRetry();
                                          }
                                      }
        );
    }

    @Override
    public boolean onButtonClicked(View v) {
        if (!super.onButtonClicked(v)) {
            return false;
        }

        int btnResId = v.getId();
        if (btnResId == R.id.my_page) {
            _goToMyPage.call();
        } else if (btnResId == R.id.toggle_delete) {
            boolean isDelete = !_isActiveDeleteMode.getValue();
            _isActiveDeleteMode.setValue(isDelete);
        }
        return true;
    }

    public void setDeleteToggleString(boolean isDelete) {
        if (isDelete) {
            _deleteToggleString.setValue("완료");
        } else {
            _deleteToggleString.setValue("삭제");
        }
    }

}