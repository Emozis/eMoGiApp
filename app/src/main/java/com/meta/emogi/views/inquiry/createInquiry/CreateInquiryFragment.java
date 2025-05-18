package com.meta.emogi.views.inquiry.createInquiry;

import androidx.navigation.Navigation;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentCreateInquiryBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

public class CreateInquiryFragment extends BaseFragment<FragmentCreateInquiryBinding,CreateInquiryViewModel> {

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
        return CreateInquiryViewModel.class;
    }
    @Override
    protected void registerObservers() {
        viewModel.goToCheckPage().observe(this, unused -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_createInquiryFragment_to_checkInquiryFragment);
        });
    }
}