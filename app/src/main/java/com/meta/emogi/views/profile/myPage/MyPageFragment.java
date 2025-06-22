package com.meta.emogi.views.profile.myPage;

import androidx.activity.OnBackPressedCallback;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentMyPageBinding;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

public class MyPageFragment extends BaseFragment<FragmentMyPageBinding, MyPageViewModel> {

    private ProfileActivity activity;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("마이페이지");
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_my_page;
    }
    @Override
    protected Class<MyPageViewModel> viewModelClass() {
        return MyPageViewModel.class;
    }
    @Override
    protected void registerObservers() {
        viewModel.goToMyPage().observe(this, unused -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_myPageFragment_to_characterManageFragment);
        });
        viewModel.userData().observe(this, userData -> {
            viewModel.setUserData(userData.getUserEmail(), userData.getUserName());

            Glide.with(requireContext()).load(userData.getUserProfile()).placeholder(R.drawable.ic_profile) // 로딩 중일 때 보여줄 이미지
                    .error(R.drawable.ic_profile) // 로딩 실패 시 보여줄 이미지
                    .circleCrop() // 이미지를 동그랗게 만듭니다.
                    .into(binding.imageProfile);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate: ");
        activity = (ProfileActivity) requireActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();  // 현재 액티비티 종료
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getUserData();
    }
}