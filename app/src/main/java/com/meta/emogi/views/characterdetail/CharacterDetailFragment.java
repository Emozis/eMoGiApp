package com.meta.emogi.views.characterdetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.method.ScrollingMovementMethod;
import android.view.View;

import com.bumptech.glide.Glide;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.data.network.model.CharacterResponse;
import com.meta.emogi.databinding.FragmentCharacterDetailBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.List;

public class CharacterDetailFragment extends BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel> {

    private CharacterDetailActivity activity;
    private int characterId;
    private String chatUrl;
    private String characterName;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("캐릭터 상세");
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_character_detail;
    }
    @Override
    protected Class<CharacterDetailViewModel> viewModelClass() {
        return CharacterDetailViewModel.class;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (CharacterDetailActivity) requireActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.contentPersonality.setMovementMethod(new ScrollingMovementMethod());
        characterId = activity.getCharacterId();
        viewModel.getCharacterDetails(characterId);
    }
    @Override
    protected void registerObservers() {
        viewModel.isChatStart().observe(this, unused -> {
            viewModel.connectCreateChatRoom(characterId);
        });
        viewModel.characterDetail().observe(this, characterDetail -> {
            chatUrl = characterDetail.getCharacterProfile();
            characterName = characterDetail.getCharacterName();

            // 이미지 URL을 ImageView에 로드
            Glide.with(requireContext()).load(chatUrl).placeholder(R.drawable.drawable_background_toolbar_profile) // 로딩 중일 때 보여줄 이미지
                    .error(R.drawable.drawable_background_toolbar_profile) // 로딩 실패 시 보여줄 이미지
                    .circleCrop()
                    .into(binding.characterProfileImage);

            List<CharacterResponse.CharacterRelationships> relationshipList = characterDetail.getCharacterRelationships();

            StringBuilder sendRelationship = new StringBuilder();
            for (CharacterResponse.CharacterRelationships relationship : relationshipList) {
                sendRelationship.append("#").append(relationship.getRelationshipName()).append(" ");
            }
            characterDetail.getCharacterProfile();

            String nameAndGender = "#" + characterDetail.getCharacterName() + " #" + (characterDetail.getCharacterGender().equals("male") ? "남자" : "여자");

//            Markwon markwon = Markwon.create(requireContext());
//            Spanned markdownDetail = markwon.toMarkdown(characterDetail.getCharacterDetails());
            String Detail = characterDetail.getCharacterDetails();

            //마크다운으로 변환
            viewModel.getCharacterDetailData(nameAndGender, characterDetail.getCharacterPersonality(), String.valueOf(sendRelationship), Detail);

            //                        viewModel.getCharacterDetailData(nameAndGender, characterDetail.getCharacterPersonality(), String.valueOf(sendRelationship), characterDetail.getCharacterDetails());

            viewModel.offLoading();
        });

        viewModel.chatRoom().observe(this, chatRoom -> {
            activity.moveToChatRoom(chatRoom.getData().getChatId(), chatUrl,characterName);
        });
    }

}