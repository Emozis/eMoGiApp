package com.meta.emogi.views.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentMenuBinding;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.TokenModel;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.ArrayList;
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
        setupRecyclerView();
        getCharactersMe("Bearer " + key);
        getCharactersRank();

    }

    private void setupRecyclerView() {
        menuListAdapter = new MenuListAdapter(new ArrayList<>());
        binding.listRankCharacter.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.listRankCharacter.setAdapter(menuListAdapter);
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
                        binding.listRankCharacter.setAdapter(menuListAdapter);
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
                }
            }

            @Override
            public void onFailure(Call<List<CharacterModel>> call, Throwable t) {
                Log.e(TAG, "Request Failed: " + t.getMessage());
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
                        binding.listMyCharacter.setAdapter(menuListAdapter);
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
                }
            }

            @Override
            public void onFailure(Call<List<CharacterModel>> call, Throwable t) {
                Log.e(TAG, "Request Failed: " + t.getMessage());
            }
        });
    }

}