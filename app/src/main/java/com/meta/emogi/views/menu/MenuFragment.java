package com.meta.emogi.views.menu;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.transition.Transition;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
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
    private ApiService apiService;
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

        viewModel.isMyLoading().observe(this,isMyLoading->{
            if(!isMyLoading&&!viewModel.isRankLoading().getValue()){
                viewModel.offLoading();
            }
        });

        viewModel.isRankLoading().observe(this,isRankLoading->{
            if(!isRankLoading&&!viewModel.isMyLoading().getValue()){
                viewModel.offLoading();
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MenuActivity) requireActivity();
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
        String key = activity.getAccessToken();
        getCharactersMe("Bearer " + key);
        getCharactersRank();
    }


    private void setClickListenerRecyclerView(MenuListAdapter menuListAdapter) {
        menuListAdapter.setOnItemClickListener(characterId -> {
            // 클릭된 아이템의 CharacterId를 가져와서 처리
            if (characterId != -1) {
                Log.d(TAG, "Selected CharacterId: " + characterId);
                activity.moveToCharacterDetail(characterId);
            }
        });
    }

    public void getCharactersMe(String authToken) {
        Call<List<CharacterModel>> call = apiService.getCharactersMe(authToken);

        call.enqueue(new Callback<List<CharacterModel>>() {
            @Override
            public void onResponse(Call<List<CharacterModel>> call, Response<List<CharacterModel>> response) {
                if (response.isSuccessful()) {
                    List<CharacterModel> characterList = response.body();
                    if (characterList != null) {
                        menuListAdapter = new MenuListAdapter(characterList);
                        binding.listMyCharacter.setAdapter(menuListAdapter);
                        setClickListenerRecyclerView(menuListAdapter);
                        viewModel.loadDoneMy();
                    }
                } else {
                    Log.e(TAG, "Request Failed. Error Code: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            Log.e(TAG, "Error Body: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    viewModel.failLoading();
                }

            }

            @Override
            public void onFailure(Call<List<CharacterModel>> call, Throwable t) {
                Log.e(TAG, "Request Failed: " + t.getMessage());
                viewModel.failLoading();
            }
        });
    }

    public void getCharactersRank() {
        Call<List<CharacterModel>> call = apiService.getCharactersRank();

        call.enqueue(new Callback<List<CharacterModel>>() {
            @Override
            public void onResponse(Call<List<CharacterModel>> call, Response<List<CharacterModel>> response) {
                if (response.isSuccessful()) {
                    List<CharacterModel> characterList = response.body();

                    if (characterList != null) {
                        menuListAdapter = new MenuListAdapter(characterList);
                        binding.listRankCharacter.setAdapter(menuListAdapter);
                        setClickListenerRecyclerView(menuListAdapter);
                        viewModel.loadDoneRank();
                    }
                } else {
                    Log.e(TAG, "Request Failed. Error Code: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            Log.e(TAG, "Error Body: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    viewModel.failLoading();
                }
            }

            @Override
            public void onFailure(Call<List<CharacterModel>> call, Throwable t) {
                Log.e(TAG, "Request Failed: " + t.getMessage());
                viewModel.failLoading();
            }
        });
    }

}