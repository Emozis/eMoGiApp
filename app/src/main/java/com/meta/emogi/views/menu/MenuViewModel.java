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

    public MenuViewModel(Application application) {
        super(application);
    }

    public void getUserData() {
        apiRepository.getUserData(new ApiCallBack.ApiResultHandler<UserData>() {
            @Override
            public void onSuccess(UserData data) {
                _userData.setValue(data);
            }
            @Override
            public void onFailed(Throwable t) {
                failLoading();
            }
        });
    }

    public void getMyCharacters() {
        apiRepository.getMyCharacterList(new ApiCallBack.ApiResultHandler<List<CharacterResponse>>() {
            @Override
            public void onSuccess(List<CharacterResponse> data) {
                _myCharacterList.setValue(data);
            }

            @Override
            public void onFailed(Throwable t) {
                failLoading();
            }
        });

    }

    public void getRankCharacterList() {
        apiRepository.getRankCharacterList(new ApiCallBack.ApiResultHandler<List<CharacterResponse>>() {
            @Override
            public void onSuccess(List<CharacterResponse> data) {
                _rankCharacterList.setValue(data);
            }
            @Override
            public void onFailed(Throwable t) {
                failLoading();
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
            offLoading();
        }
    }

    public enum MoveType {
        CHAT_LIST, MAKE_CHARACTER
    }

}