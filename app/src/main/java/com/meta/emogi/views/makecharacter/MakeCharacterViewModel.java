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
import com.meta.emogi.views.menu.MenuViewModel;

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

    public LiveData<Boolean> isMan() {
        return _isMan;
    }
    public LiveData<String> category() {
        return _category;
    }

    public LiveData<Void> generate() {
        return _generate;
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
        }
    }

    public void dataReset(){
        name.setValue("");
        personality.setValue("");
        detail.setValue("");
        _isMan.setValue(true);
        _category.setValue("");
    }

}

