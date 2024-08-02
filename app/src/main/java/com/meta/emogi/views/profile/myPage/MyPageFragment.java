package com.meta.emogi.views.profile.myPage;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentMenuBinding;
import com.meta.emogi.databinding.FragmentMyPageBinding;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.UserData;
import com.meta.emogi.network.datamodels.UserData;
import com.meta.emogi.views.makecharacter.MakeCharacterActivity;
import com.meta.emogi.views.menu.MenuViewModel;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyPageFragment extends BaseFragment<FragmentMyPageBinding, MyPageViewModel> {

    private ApiService apiService;
    private ProfileActivity activity;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("마이페이지");
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_my_page;
    }
    @Override
    protected Class<MyPageViewModel> viewModelClass() {
        return MyPageViewModel.class;
    }
    @Override
    protected void registerObservers() {
        viewModel.goToMyPage().observe(this, unused -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_myPageFragment_to_characterManageFragment);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        
        activity=(ProfileActivity) requireActivity();
        
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);
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
    }
    @Override
    public void onResume() {
        super.onResume();
        
//        getUserData();
        Log.d(TAG, "onResume: ");
    }
    
    private void getUserData(){
        String accessToken = "Bearer "+activity.getAccessToken();
        Call<UserData> call = apiService.getUserData(accessToken);

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call, @NonNull
            Response<UserData> response) {
                if (response.isSuccessful()) {
                    UserData createdCharacter = response.body();
                    if (createdCharacter != null) {
                        // 성공적으로 생성된 캐릭터 처리

                        Log.d(TAG, createdCharacter.getUserEmail());
                        Log.d(TAG, createdCharacter.getUserName());

                    }
                } else {
                    // 요청 실패 처리
                    Log.e("Character", "캐릭터 생성 실패: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                // 네트워크 오류 처리
                Log.e("Character", "API 호출 실패: " + t.getMessage());
            }
        });
        
        
    }
}