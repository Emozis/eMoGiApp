package com.meta.emogi.views.menu;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.request.transition.Transition;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.meta.emogi.MyApplication;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentMenuBinding;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MenuFragment extends BaseFragment<FragmentMenuBinding, MenuViewModel> {
    private static final String TAG = "MenuFragment";
    private MenuActivity activity;
    private MenuListAdapter menuListAdapter;

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
        viewModel.type().observe(this, type -> {
            if (type == MenuViewModel.MoveType.CHAT_LIST) {
                activity.moveToChatList();
            } else {
                activity.moveToMakeCharacter();
            }
        });

        viewModel.menu2ManageProfile().observe(this, unused -> {
            activity.moveToManageProfile();
        });

        viewModel.menu2MyPageProfile().observe(this,unused -> {
            activity.moveToMyPageProfile();
        });

        viewModel.isMyLoading().observe(this, isMyLoading -> {
            if (!isMyLoading && !viewModel.isRankLoading().getValue()) {
                viewModel.offLoading();
            }
        });

        viewModel.isRankLoading().observe(this, isRankLoading -> {
            if (!isRankLoading && !viewModel.isMyLoading().getValue()) {
                viewModel.offLoading();
            }
        });

        viewModel.myCharacterList().observe(getViewLifecycleOwner(), myCharacterList -> {
            if (myCharacterList != null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                binding.listRankCharacter.setLayoutManager(layoutManager);
                menuListAdapter = new MenuListAdapter(myCharacterList);
                binding.listMyCharacter.setAdapter(menuListAdapter);
                setClickListenerRecyclerView(menuListAdapter);
                viewModel.loadDoneMy();
            } else {
                viewModel.failLoading();
                Log.e("www", "Menu myCharacter 통신 오류");
            }
        });

        viewModel.rankCharacterList().observe(getViewLifecycleOwner(), characterList -> {
            if (characterList != null) {
                menuListAdapter = new MenuListAdapter(characterList);
                binding.listRankCharacter.setAdapter(menuListAdapter);
                setClickListenerRecyclerView(menuListAdapter);
                viewModel.loadDoneRank();
            } else {
                viewModel.failLoading();
                Log.e("www", "Menu myCharacter 통신 오류");
            }
        });

        viewModel.userData().observe(this, userData -> {
            Glide.with(requireContext()).load(userData.getUserProfile()).placeholder(R.drawable.ic_profile) // 로딩 중일 때 보여줄 이미지
                    .error(R.drawable.ic_profile) // 로딩 실패 시 보여줄 이미지
                    .circleCrop() // 이미지를 동그랗게 만듭니다.
                    .into(binding.imageProfile);

            viewModel.offLoading();
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
        String key = activity.getAccessToken();
        viewModel.getUserData(key);
        viewModel.getMyCharacters(key);
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