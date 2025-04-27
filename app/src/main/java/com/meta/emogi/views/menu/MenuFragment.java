package com.meta.emogi.views.menu;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.databinding.FragmentMenuBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

public class MenuFragment extends BaseFragment<FragmentMenuBinding, MenuViewModel> {
    private static final String TAG = "MenuFragment";
    private MenuActivity activity;
    private MenuListAdapter myCharacterListAdapter;
    private MenuListAdapter rankCharacterListAdapter;


    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("EmoGi");
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    protected Class<MenuViewModel> viewModelClass() {
        return MenuViewModel.class;
    }

    @Override
    protected void registerObservers() {
        viewModel.type().observe(getViewLifecycleOwner(), type -> {
            if (type == MenuViewModel.MoveType.CHAT_LIST) {
                activity.moveToChatList();
            } else {
                activity.moveToMakeCharacter();
            }
        });

        viewModel.menu2ManageProfile().observe(getViewLifecycleOwner(), unused ->
            activity.moveToManageProfile());

        viewModel.menu2MyPageProfile().observe(getViewLifecycleOwner(), unused -> {
            activity.moveToMyPageProfile();
        });

        viewModel.userData().observe(this, userData -> {
            Glide.with(requireContext()).load(userData.getUserProfile()).placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .circleCrop()
                    .into(binding.imageProfile);
        });

        viewModel.myCharacterList().observe(getViewLifecycleOwner(), myCharacterList -> {
            if (myCharacterList != null) {
                myCharacterListAdapter = new MenuListAdapter(myCharacterList);
                binding.listMyCharacter.setAdapter(myCharacterListAdapter);
                setClickListenerRecyclerView(myCharacterListAdapter);
                viewModel.loadDoneMy();
            }
        });

        viewModel.rankCharacterList().observe(getViewLifecycleOwner(), characterList -> {
            if (characterList != null) {
                rankCharacterListAdapter = new MenuListAdapter(characterList);
                binding.listRankCharacter.setAdapter(rankCharacterListAdapter);
                setClickListenerRecyclerView(rankCharacterListAdapter);
                viewModel.loadDoneRank();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MenuActivity) requireActivity();
    }
    @Override
    public void onResume() {
        super.onResume();
        viewModel.getUserData();
        viewModel.getMyCharacters();
        viewModel.getRankCharacterList();
    }

    private void setClickListenerRecyclerView(MenuListAdapter menuListAdapter) {
        menuListAdapter.setOnItemClickListener(characterId -> {
            // 클릭된 아이템의 CharacterId를 가져와서 처리
            if (characterId != -1) {
                Log.w(TAG, "Selected CharacterId: " + characterId);
                activity.moveToCharacterDetail(characterId);
            }
        });
    }
}