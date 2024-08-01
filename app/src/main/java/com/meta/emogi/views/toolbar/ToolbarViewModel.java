package com.meta.emogi.views.toolbar;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.base.BaseViewModel;
public class ToolbarViewModel extends BaseViewModel {
    public ToolbarViewModel(@NonNull Application application) {
        super(application);
    }

    private final MutableLiveData<String> _title = new MutableLiveData<>();

    public LiveData<String> title() {
        return _title;
    }

    public void setTitle(String title) {
        _title.setValue(title);
    }



}
