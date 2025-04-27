package com.meta.emogi.views.menu;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.data.network.api.ApiCallBack;
import com.meta.emogi.data.network.model.CharacterResponse;
import com.meta.emogi.data.network.model.UserData;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuViewModel extends BaseViewModel {
    private final MutableLiveData<MoveType> _type = new MutableLiveData<>();
    private final SingleLiveEvent<Void> _menu2ManageProfile = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> _menu2MyPageProfile = new SingleLiveEvent<>();
    private final MutableLiveData<UserData> _userData = new MutableLiveData<>();

    private final MutableLiveData<List<CharacterResponse>> _myCharacterList = new MutableLiveData<>();
    private final MutableLiveData<List<CharacterResponse>> _rankCharacterList = new MutableLiveData<>();

    public LiveData<MoveType> type() {
        return _type;
    }
    public LiveData<Void> menu2ManageProfile() {
        return _menu2ManageProfile;
    }

    public LiveData<Void> menu2MyPageProfile() {
        return _menu2MyPageProfile;
    }
    public LiveData<List<CharacterResponse>> myCharacterList() {
        return _myCharacterList;
    }
    public LiveData<List<CharacterResponse>> rankCharacterList() {
        return _rankCharacterList;
    }
    public LiveData<UserData> userData() {
        return _userData;
    }

    public void getUserData() {
        loading();
        apiRepository.getUserData(new ApiCallBack.ApiResultHandler<UserData>() {
            @Override
            public void onSuccess(UserData data) {
                loadingSuccess();
                _userData.setValue(data);
            }
            @Override
            public void onFailed(Throwable t) {
                loadingFailed("유저 데이터 가져오는 작업");
            }
        });
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
            }
        });

    }

    public void getRankCharacterList() {
        loading();
        apiRepository.getRankCharacterList(new ApiCallBack.ApiResultHandler<List<CharacterResponse>>() {
            @Override
            public void onSuccess(List<CharacterResponse> data) {
                loadingSuccess();
                _rankCharacterList.setValue(data);
            }
            @Override
            public void onFailed(Throwable t) {
                loadingFailed("캐릭터 랭킹 가져오기 작업");
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
        } else if (btnResId == R.id.image_profile) {
            _menu2MyPageProfile.call();
        }
    }

    private boolean isMyCharacterLoaded = false;
    private boolean isRankCharacterLoaded = false;

    public void loadDoneMy() {
        isMyCharacterLoaded = true;
        isAllLoaded();
    }

    public void loadDoneRank() {
        isRankCharacterLoaded = true;
        isAllLoaded();
    }

    private void isAllLoaded() {
        if (isMyCharacterLoaded && isRankCharacterLoaded) {
            loadingSuccess();
        }
    }

    public enum MoveType {
        CHAT_LIST, MAKE_CHARACTER
    }

}