package com.meta.emogi.views.toolbar;
import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
public class ToolbarViewModel extends BaseViewModel {
    public ToolbarViewModel(@NonNull Application application) {
        super(application);
    }

    private final MutableLiveData<String> _title = new MutableLiveData<>();
    private final SingleLiveEvent<Void> _back = new SingleLiveEvent<>();

    public LiveData<String> title() {
        return _title;
    }

    public void setTitle(String title) {
        _title.setValue(title);
    }
    public LiveData<Void> back() {
        return _back;
    }

    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.back) {
            _back.call();
        }
    }


}
