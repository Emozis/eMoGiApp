package com.meta.emogi.views.profile.characterMangage;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentCharacterManageBinding;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.views.chatlist.ChatListAdapter;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CharacterManageFragment extends BaseFragment<FragmentCharacterManageBinding, CharacterManageViewModel> {

    private ProfileActivity activity;
    private CharacterAdapter characterAdapter;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("프로필");
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

        viewModel.myCharacterList().observe(getViewLifecycleOwner(), myCharacterList -> {
            characterAdapter = new CharacterAdapter(myCharacterList);
            binding.rvCharacterList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            binding.rvCharacterList.setAdapter(characterAdapter);
            setClickListenerRecyclerView(characterAdapter);
            viewModel.offLoading();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ProfileActivity) requireActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        String key = activity.getAccessToken();
        viewModel.getMyCharacters(key);
    }

    private void setClickListenerRecyclerView(CharacterAdapter chatListAdapter) {
        chatListAdapter.setOnItemClickListener(characterId -> {
            if (characterId != -1) {
                activity.moveToDetail(characterId);
            }
        });
    }
}