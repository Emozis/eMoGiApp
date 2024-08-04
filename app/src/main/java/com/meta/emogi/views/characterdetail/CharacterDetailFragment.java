package com.meta.emogi.views.characterdetail;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentCharacterDetailBinding;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.MakeChatRoom;
import com.meta.emogi.views.makecharacter.ImageAdapter;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CharacterDetailFragment extends BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel> {

    private ApiService apiService;
    private CharacterDetailActivity activity;
    private int characterId;
    private String accessKey;

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
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        accessKey = "Bearer "+activity.getAccessToken();
        characterId = activity.getCharacterId();
        setCharacterDetail();
    }
    @Override
    protected void registerObservers() {
        viewModel.isChatStart().observe(this,unused -> {
            makeChatRoom();
//            activity.moveToChatRoom();
        });
    }

    public void setCharacterDetail() {
        Log.d("키값확인", accessKey);
        Call<CharacterModel> call = apiService.getCharacterDetails(accessKey, characterId);

        call.enqueue(new Callback<CharacterModel>() {
            @Override
            public void onResponse(
                    @NonNull Call<CharacterModel> call,
                    @NonNull Response<CharacterModel> response) {
                if (response.isSuccessful()) {
                    CharacterModel createdCharacter = response.body();
                    if (createdCharacter != null) {
                        Log.d(TAG, createdCharacter.getCharacterName());
                        viewModel.getCharacterDetailData(createdCharacter.getCharacterName(), createdCharacter.getCharacterPersonality(), "test", createdCharacter.getCharacterDetails());
                        //                               createdCharacter.getCharacterRelationships(),
                    }
                } else {
                    // 요청 실패 처리
                    Log.e("data요청 실패", "유저 데이터 가져오기 실패 :" + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CharacterModel> call, @NonNull Throwable t) {
                // 네트워크 오류 처리
                Log.e("Character", "API 호출 실패: " + t.getMessage());
            }
        });
    }

    public void makeChatRoom(){
        Call<MakeChatRoom> call = apiService.createChatRoom(accessKey,characterId);

        call.enqueue(new Callback<MakeChatRoom>() {
            @Override
            public void onResponse(
                    @NonNull Call<MakeChatRoom> call,
                    @NonNull Response<MakeChatRoom> response) {
                if (response.isSuccessful()) {
                    MakeChatRoom createdCharacter = response.body();
                    if (createdCharacter != null) {
                        activity.moveToChatRoom(createdCharacter.getChatId());
                        Log.d("방만들기 성공", String.valueOf(createdCharacter.getChatId()));
                    }
                } else {
                    // 요청 실패 처리
                    Log.e("방만들기 요청 실패", "방만들기 실패 :" + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MakeChatRoom> call, @NonNull Throwable t) {
                // 네트워크 오류 처리
                Log.e("Character", "API 호출 실패: " + t.getMessage());
            }
        });

    }



}