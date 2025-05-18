package com.meta.emogi.views.inquiry.createInquiry;

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
import com.meta.emogi.databinding.FragmentCreateInquiryBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

public class CreateInquiryFragment extends BaseFragment<FragmentCreateInquiryBinding,CreateInquiryViewModel> {

    private CreateInquiryViewModel mViewModel;

    public static CreateInquiryFragment newInstance() {
        return new CreateInquiryFragment();
    }

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("문의하기");
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_create_inquiry;
    }
    @Override
    protected Class<CreateInquiryViewModel> viewModelClass() {
        return null;
    }
    @Override
    protected void registerObservers() {

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_inquiry, container, false);
    }

}