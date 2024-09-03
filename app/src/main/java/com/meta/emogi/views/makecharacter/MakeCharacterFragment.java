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
    private ApiService apiService;
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

            if (selectedImageUrl == null || viewModel.personality.getValue() == null || viewModel.detail.getValue() == null || viewModel.isOpen().getValue() == null || relationshipList.size() == 0) {

                Toast.makeText(requireContext(), "설정하지 않은 값이 있습니다.", Toast.LENGTH_SHORT).show();
            } else {
                createCharacter(selectedImageUrl, gender, relationshipList);
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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MakeCharacterActivity) requireActivity();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);
    }
    @Override
    public void onResume() {
        super.onResume();

        getDefaultImages();
        getDefaultRelationship();
    }

    private void getDefaultRelationship() {
        Call<List<RelationshipModel>> call = apiService.getDefaultRelationship();

        call.enqueue(new Callback<List<RelationshipModel>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<RelationshipModel>> call,
                    @NonNull Response<List<RelationshipModel>> response) {
                if (response.isSuccessful()) {
                    List<RelationshipModel> createdRelationsip = response.body();
                    if (createdRelationsip != null) {
                        relationshipAdapter = new RelationshipAdapter(createdRelationsip);
                        binding.characterCategory.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
                        binding.characterCategory.setAdapter(relationshipAdapter);
                    }
                } else {
                    // 요청 실패 처리
                    Log.e("data요청 실패", "유저 데이터 가져오기 실패 :" + response.message());
                    viewModel.failLoading();
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<List<RelationshipModel>> call, @NonNull Throwable t) {
                // 네트워크 오류 처리
                Log.e("Character", "API 호출 실패: " + t.getMessage());
                viewModel.failLoading();
            }
        });
    }

    private void getDefaultImages() {
        Call<List<ImageModel>> call = apiService.getDefaultImage();

        call.enqueue(new Callback<List<ImageModel>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<ImageModel>> call,
                    @NonNull Response<List<ImageModel>> response) {
                if (response.isSuccessful()) {
                    List<ImageModel> createdCharacter = response.body();
                    if (createdCharacter != null) {
                        imageAdapter = new ImageAdapter(createdCharacter);
                        binding.characterImage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        binding.characterImage.setAdapter(imageAdapter);
                        viewModel.offLoading();
                    }
                } else {
                    // 요청 실패 처리
                    Log.e("data요청 실패", "유저 데이터 가져오기 실패 :" + response.message());
                    viewModel.failLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ImageModel>> call, @NonNull Throwable t) {
                // 네트워크 오류 처리
                Log.e("Character", "API 호출 실패: " + t.getMessage());
                viewModel.failLoading();
            }
        });
    }

    private void createCharacter(String selectedImgUrl, String gender, ArrayList<Integer> relationships) {
        // 예제 데이터 생성 (실제 데이터로 대체 필요)

        MakeCharacterModel makeCharacterModel = new MakeCharacterModel(viewModel.name.getValue(), selectedImgUrl, gender, viewModel.personality.getValue(), viewModel.detail.getValue(), viewModel.isOpen().getValue(), relationships);

        // 예시용 JWT 토큰 (실제 앱에서는 사용자 인증 후 받은 토큰 사용)
        String accessToken = activity.getAccessToken();

        Call<MakeCharacterModel> call = apiService.createCharacter(accessToken, makeCharacterModel);

        call.enqueue(new Callback<MakeCharacterModel>() {
            @Override
            public void onResponse(
                    @NonNull Call<MakeCharacterModel> call,
                    @NonNull Response<MakeCharacterModel> response) {
                if (response.isSuccessful()) {
                    MakeCharacterModel createdCharacter = response.body();
                    if (createdCharacter != null) {
                        // 성공적으로 생성된 캐릭터 처리
                        Log.d("Character", "캐릭터 생성 성공: ID=" + createdCharacter.getCharacterId());
                        viewModel.dataReset();
                        activity.moveToMyProfile();
                    }
                } else {
                    // 요청 실패 처리
                    Log.e("Character", "캐릭터 생성 실패: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MakeCharacterModel> call, @NonNull Throwable t) {
                // 네트워크 오류 처리
                Log.e("Character", "API 호출 실패: " + t.getMessage());
            }
        });
    }

}