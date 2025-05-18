package com.meta.emogi.views.inquiry.checkInquiry;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentCheckInquiryBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

public class CheckInquiryFragment extends BaseFragment<FragmentCheckInquiryBinding,CheckInquiryViewModel> {


    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("문의하기");
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_check_inquiry;
    }
    @Override
    protected Class<CheckInquiryViewModel> viewModelClass() {
        return CheckInquiryViewModel.class;
    }
    @Override
    protected void registerObservers() {

    }


}