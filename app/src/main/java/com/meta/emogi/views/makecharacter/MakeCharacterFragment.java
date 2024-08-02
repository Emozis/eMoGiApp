package com.meta.emogi.views.makecharacter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentMakeCharacterBinding;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.recyclerview.CategoryItem;
import com.meta.emogi.network.datamodels.MakeCharacterModel;
import com.meta.emogi.network.recyclerview.ImageItem;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MakeCharacterFragment extends BaseFragment<FragmentMakeCharacterBinding,MakeCharacterViewModel> {


    private ImageAdapter imageAdapter;
    private CategoryAdapter categoryAdapter;
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
        viewModel.generate().observe(this,unused -> {
            createCharacter("친구");
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity=(MakeCharacterActivity)requireActivity();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);
    }
    @Override
    public void onResume() {
        super.onResume();

        List<ImageItem> imageItems = new ArrayList<>();
        imageItems.add(new ImageItem(R.drawable.ic_launcher_background));
        imageItems.add(new ImageItem(R.drawable.ic_launcher_background));
        imageItems.add(new ImageItem(R.drawable.ic_launcher_background));

        imageAdapter= new ImageAdapter(imageItems);
        binding.characterImage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.characterImage.setAdapter(imageAdapter);


        List<CategoryItem> categoryItemList = new ArrayList<>();
        categoryItemList.add(new CategoryItem("친구"));
        categoryItemList.add(new CategoryItem("직장동료"));
        categoryItemList.add(new CategoryItem("연인"));
        categoryItemList.add(new CategoryItem("비밀친구"));
        categoryItemList.add(new CategoryItem("고용주"));
        categoryItemList.add(new CategoryItem("인터넷친구"));
        categoryItemList.add(new CategoryItem("운동메이트"));
        categoryItemList.add(new CategoryItem("가족"));

        categoryAdapter= new CategoryAdapter(categoryItemList);
        binding.characterCategory.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.HORIZONTAL, false));
        binding.characterCategory.setAdapter(categoryAdapter);

        String selectedCategory = categoryAdapter.getSelectedCategoryText();


    }

    private void createCharacter(String selectedCategory) {
        // 예제 데이터 생성 (실제 데이터로 대체 필요)
        List<String> relationships = new ArrayList<>();
//        relationships.add(selectedCategory); // 선택된 카테고리를 관계로 추가
        relationships.add("ㅇㅈ"); // 선택된 카테고리를 관계로 추가

        MakeCharacterModel makeCharacterModel = new MakeCharacterModel(
                "name",
                "profile",
                "male",
                "Personality",
                "detailZ",
                true,
                relationships
        );

        // 예시용 JWT 토큰 (실제 앱에서는 사용자 인증 후 받은 토큰 사용)
        String accessToken = "Bearer "+activity.getAccessToken();

        Call<MakeCharacterModel> call = apiService.createCharacter(accessToken,makeCharacterModel);

        call.enqueue(new Callback<MakeCharacterModel>() {
            @Override
            public void onResponse(@NonNull Call<MakeCharacterModel> call, @NonNull
            Response<MakeCharacterModel> response) {
                if (response.isSuccessful()) {
                    MakeCharacterModel createdCharacter = response.body();
                    if (createdCharacter != null) {
                        // 성공적으로 생성된 캐릭터 처리
                        Log.d("Character", "캐릭터 생성 성공: ID=" + createdCharacter.getCharacterId());

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