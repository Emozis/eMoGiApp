package com.meta.emogi.views.makecharacter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.widget.Toast;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentMakeCharacterBinding;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.ImageModel;
import com.meta.emogi.network.datamodels.RelationshipModel;
import com.meta.emogi.network.datamodels.MakeCharacterModel;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MakeCharacterFragment extends BaseFragment<FragmentMakeCharacterBinding, MakeCharacterViewModel> {

    private ImageAdapter imageAdapter;
    private RelationshipAdapter relationshipAdapter;
    private MakeCharacterActivity activity;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("캐릭터 생성");
    }

    public static MakeCharacterFragment newInstance() {
        return new MakeCharacterFragment();
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_make_character;
    }
    @Override
    protected Class<MakeCharacterViewModel> viewModelClass() {
        return MakeCharacterViewModel.class;
    }
    @Override
    protected void registerObservers() {
        viewModel.generate().observe(this, unused -> {

            String selectedImageUrl = imageAdapter.getSelectedImageUrl();
            String gender = viewModel.isMan().getValue() ? "male" : "female";
            ArrayList<Integer> relationshipList = (ArrayList<Integer>) relationshipAdapter.getSelectedRelationIds();
            MakeCharacterModel characterModel = viewModel.getSelectedCharacterOption(selectedImageUrl,gender,relationshipList);

            if (selectedImageUrl == null || viewModel.personality.getValue() == null || viewModel.detail.getValue() == null || viewModel.isOpen().getValue() == null || relationshipList.size() == 0) {

                Toast.makeText(requireContext(), "설정하지 않은 값이 있습니다.", Toast.LENGTH_SHORT).show();
            } else {
                String accessToken = activity.getAccessToken();
                viewModel.createCharacter(accessToken, characterModel);
            }
        });
        viewModel.isMan().observe(getViewLifecycleOwner(), isMan -> {
            binding.genderMan.setSelected(isMan);
            binding.genderWoman.setSelected(!isMan);
        });

        viewModel.isOpen().observe(getViewLifecycleOwner(), isOpen -> {
            if (isOpen) {
                binding.ivIsOpen.setSelected(true);
            } else {
                binding.ivIsOpen.setSelected(false);
            }
        });

        viewModel.defaultRelationshipList().observe(this, defaultRelationshipList -> {
            relationshipAdapter = new RelationshipAdapter(defaultRelationshipList);
            binding.characterCategory.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
            binding.characterCategory.setAdapter(relationshipAdapter);
        });

        viewModel.defaultImageList().observe(this,defaultImageList->{
            imageAdapter = new ImageAdapter(defaultImageList);
            binding.characterImage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.characterImage.setAdapter(imageAdapter);
            viewModel.offLoading();
        });

        viewModel.createdCharacter().observe(this,createdCharacter->{
            Log.d("Character", "캐릭터 생성 성공: ID=" + createdCharacter.getCharacterId());
            viewModel.dataReset();
            activity.moveToMyProfile();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MakeCharacterActivity) requireActivity();
    }
    @Override
    public void onResume() {
        super.onResume();

        viewModel.getDefaultImageList();
        viewModel.getDefaultRelationshipList();
    }

}