package com.meta.emogi.views.profile.characterMangage;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentCharacterManageBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

public class CharacterManageFragment extends BaseFragment<FragmentCharacterManageBinding, CharacterManageViewModel> {

    private CharacterManageViewModel mViewModel;
    public static CharacterManageFragment newInstance() {
        return new CharacterManageFragment();
    }
    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("마이페이지");
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_character_manage;
    }
    @Override
    protected Class<CharacterManageViewModel> viewModelClass() {
        return CharacterManageViewModel.class;
    }
    @Override
    protected void registerObservers() {
        viewModel.goToMyPage().observe(this, unused -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_characterManageFragment_to_myPageFragment);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();  // 현재 액티비티 종료
            }
        });
    }
}