package com.meta.emogi.views.profile.characterMangage;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentCharacterManageBinding;
import com.meta.emogi.network.recyclerview.CharacterItem;
import com.meta.emogi.network.recyclerview.ImageItem;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.ArrayList;
import java.util.List;

public class CharacterManageFragment extends BaseFragment<FragmentCharacterManageBinding, CharacterManageViewModel> {

    private CharacterAdapter characterAdapter;

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

    @Override
    public void onResume() {
        super.onResume();

        List<CharacterItem> characterItems = new ArrayList<>();
        characterItems.add(new CharacterItem(R.drawable.ic_launcher_background, "새로운 캐릭터 이름","새로운 캐릭터 설명"));

        characterAdapter = new CharacterAdapter(characterItems);
        binding.rvCharacterList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvCharacterList.setAdapter(characterAdapter);

    }
}