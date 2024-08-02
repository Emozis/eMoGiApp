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
    public final MutableLiveData<String> _gender = new MutableLiveData<>("");
    public final MutableLiveData<String> _category = new MutableLiveData<>("");
    public final SingleLiveEvent<Void> _generate = new SingleLiveEvent<>();

    public LiveData<String> gender() {
        return _gender;
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
//            Log.d(TAG, name.getValue());
//            Log.d(TAG, personality.getValue());
//            Log.d(TAG, detail.getValue());
//            Log.d(TAG, _category.getValue());
//            Log.d(TAG, _gender.getValue());
        }

    }


}