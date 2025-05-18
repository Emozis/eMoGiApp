package com.meta.emogi.views.inquiry.createInquiry;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;

public class CreateInquiryViewModel extends BaseViewModel {
    public final MutableLiveData<String> type = new MutableLiveData<>("");
    public final MutableLiveData<String> title = new MutableLiveData<>("");
    public final MutableLiveData<String> content = new MutableLiveData<>("");
    private final SingleLiveEvent<Void> _goToCheckPage = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> _createInquiry = new SingleLiveEvent<>();

    public LiveData<Void> goToCheckPage() {
        return _goToCheckPage;
    }

    @Override
    public boolean onButtonClicked(View v) {
        if (!super.onButtonClicked(v)) {
            return false;
        }
        int btnResId = v.getId();
        if (btnResId == R.id.check_inquiry) {
            _goToCheckPage.call();
        } else if (btnResId == R.id.inquiry_create_btn) {
            _createInquiry.call();
        }
        return true;
    }


}