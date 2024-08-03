package com.meta.emogi.views.profile.characterMangage;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentCharacterManageBinding;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CharacterManageFragment extends BaseFragment<FragmentCharacterManageBinding, CharacterManageViewModel> {

    private ApiService apiService;
    private ProfileActivity activity;
    private CharacterAdapter characterAdapter;

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ProfileActivity) requireActivity();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();  // 현재 액티비티 종료
            }
        });

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        String key = activity.getAccessToken();
        getCharactersMe("Bearer " + key);
    }

    public void getCharactersMe(String authToken) {
        Call<List<CharacterModel>> call = apiService.getCharactersMe(authToken);

        call.enqueue(new Callback<List<CharacterModel>>() {
            @Override
            public void onResponse(Call<List<CharacterModel>> call, Response<List<CharacterModel>> response) {
                if (response.isSuccessful()) {
                    List<CharacterModel> characterList = response.body();
                    if (characterList != null) {
                        characterAdapter = new CharacterAdapter(characterList);
                        binding.rvCharacterList.setAdapter(characterAdapter);
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