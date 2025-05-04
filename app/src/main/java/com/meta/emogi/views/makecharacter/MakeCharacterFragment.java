package com.meta.emogi.views.makecharacter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.data.network.model.CharacterResponse;
import com.meta.emogi.databinding.FragmentMakeCharacterBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.List;

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
            List<CharacterResponse.CharacterRelationships> relationshipList = relationshipAdapter.getSelectedRelationIds();

            if (selectedImageUrl == null || viewModel.personality.getValue() == null || viewModel.detail.getValue() == null || viewModel.isOpen().getValue() == null || relationshipList.size() == 0) {
                Toast.makeText(requireContext(), "설정하지 않은 값이 있습니다.", Toast.LENGTH_SHORT).show();
            } else {
                CharacterResponse characterResponse = viewModel.getCurrentCharacterData(selectedImageUrl,
                                                                                        gender,
                                                                                        relationshipList
                );
                if (viewModel.isEdit().getValue()) {
                    viewModel.updateCharacter(characterResponse, activity.getCharacterId());
                } else {
                    viewModel.createCharacter(characterResponse);
                }
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
            binding.characterCategory.setAdapter(relationshipAdapter);
            relationshipAdapter = new RelationshipAdapter(defaultRelationshipList);
            binding.characterCategory.setLayoutManager(new GridLayoutManager(getContext(),
                                                                             3,
                                                                             GridLayoutManager.HORIZONTAL,
                                                                             false
            ));
            binding.characterCategory.setAdapter(relationshipAdapter);
        });

        viewModel.defaultImageList().observe(this, defaultImageList -> {
            imageAdapter = new ImageAdapter(defaultImageList);
            binding.characterImage.setLayoutManager(new LinearLayoutManager(getContext(),
                                                                            LinearLayoutManager.HORIZONTAL,
                                                                            false
            ));
            binding.characterImage.setAdapter(imageAdapter);
            if (activity.getCharacterId() != -1) {
                viewModel.getCharacterDetails(activity.getCharacterId());
            }
        });

        viewModel.createdCharacter().observe(this, createdCharacter -> {
            viewModel.dataReset();
            activity.showAds();
        });

        viewModel.currentCharacterData().observe(this, currentCharacterData -> {
            if (currentCharacterData != null) {
                activity.refreshToolbar(new ToolbarView.ToolbarRequest("캐릭터 수정"));
                viewModel.setIsEdit(true);
                viewModel.setCurrentCharaterData(currentCharacterData);
                Gson gson = new Gson();
                String jsonMessage = gson.toJson(currentCharacterData);
                Log.w("www", jsonMessage);

                if (relationshipAdapter != null) {
                    relationshipAdapter.setSelectedItems(currentCharacterData.getCharacterRelationships());
                }
                if (imageAdapter != null) {
                    imageAdapter.setSelectedImageUrl(currentCharacterData.getCharacterProfile());
                }
            }
        });
        viewModel.isInitialDataLoaded().observe(this, currentLoadedState -> {
            Boolean firstState = currentLoadedState.first;
            Boolean secondState = currentLoadedState.second;
            if (firstState && secondState) {
                binding.generateBtn.setVisibility(View.VISIBLE);
            }

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
        binding.generateBtn.setVisibility(View.GONE);
        textViewMakesScroll();
        viewModel.getDefaultImageList();
        viewModel.getDefaultRelationshipList();
    }

    private void textViewMakesScroll() {
        binding.personality.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
        binding.detial.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.makeCharacterFragment.post(() -> {
            // 2. px 단위 높이 가져오기
            int scrollHeight = binding.makeCharacterFragment.getHeight();

            ViewGroup.LayoutParams paramsImage = binding.layoutImage.getLayoutParams();
            paramsImage.height = (int) (scrollHeight * 0.2f);
            binding.layoutImage.setLayoutParams(paramsImage);

            ViewGroup.LayoutParams paramsName = binding.layoutName.getLayoutParams();
            paramsName.height = (int) (scrollHeight * 0.13f);
            binding.layoutName.setLayoutParams(paramsName);

            ViewGroup.LayoutParams paramsPersonality = binding.layoutPersonality.getLayoutParams();
            paramsPersonality.height = (int) (scrollHeight * 0.13f);
            binding.layoutPersonality.setLayoutParams(paramsPersonality);

            ViewGroup.LayoutParams paramsCategory = binding.layoutCategory.getLayoutParams();
            paramsCategory.height = (int) (scrollHeight * 0.25f);
            binding.layoutCategory.setLayoutParams(paramsCategory);

            ViewGroup.LayoutParams paramsDetail = binding.layoutDetail.getLayoutParams();
            paramsDetail.height = (int) (scrollHeight * 0.3f);
            binding.layoutDetail.setLayoutParams(paramsDetail);

            ViewGroup.LayoutParams paramsIsOpen = binding.layoutIsOpen.getLayoutParams();
            paramsIsOpen.height = (int) (scrollHeight * 0.05f);
            binding.layoutIsOpen.setLayoutParams(paramsIsOpen);
        });

    }
}