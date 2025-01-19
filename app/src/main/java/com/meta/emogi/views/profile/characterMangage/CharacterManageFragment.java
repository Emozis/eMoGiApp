package com.meta.emogi.views.profile.characterMangage;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentCharacterManageBinding;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.List;

public class CharacterManageFragment extends BaseFragment<FragmentCharacterManageBinding, CharacterManageViewModel> {

    private ProfileActivity activity;
    private CharacterAdapter characterAdapter;
    private String accessKey;

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
        viewModel.isActiveDeleteMode().observe(this, isActive -> {
            viewModel.setDeleteToggleString(isActive);
            characterAdapter.setDeleteMode(isActive);
        });

        viewModel.myCharacterList().observe(getViewLifecycleOwner(), myCharacterList -> {
            characterAdapter = new CharacterAdapter(myCharacterList);
            binding.rvCharacterList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            characterAdapter.updateCharacterList(myCharacterList);
            binding.rvCharacterList.setAdapter(characterAdapter);
            setClickListenerRecyclerView(characterAdapter);
            viewModel.offLoading();
            viewModel.setIsActiveDeleteMode();
        });

        viewModel.deleteCharacter().observe(this,unused -> {
            characterAdapter.notifyDataSetChanged();
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
        accessKey = activity.getAccessToken();
        viewModel.getMyCharacters(accessKey);
        //        viewModel.dummyCharacterModelList();
    }

    private void setClickListenerRecyclerView(CharacterAdapter chatListAdapter) {
        chatListAdapter.setOnItemClickListener((characterId,type) -> {
            if (characterId != -1) {
                if(type == 1){
                    activity.moveToDetail(characterId);
                }else if(type == 2){
                    viewModel.deleteCharacter(accessKey,characterId);
                }else if (type==3){
                    activity.moveToEditCharacter(characterId);
                }

            }
        });
    }
}