package com.meta.emogi.views.makecharacter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityMakeCharacterBinding;
import com.meta.emogi.util.ads.AdManager;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

public class MakeCharacterActivity extends BaseActivity<ActivityMakeCharacterBinding> {

    private static final String TAG = "MakeCharacterActivity";
    private int characterId;
    private NavController navController;

    @Override
    protected int layoutId() {
        return R.layout.activity_make_character;
    }

    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
        binding.toolbar.settingView(toolbarRequest);
    }

    @Override
    protected boolean isMainActivity() {
        return false;
    }

    @Override
    protected boolean hasBottomNavigation() {
        return true;
    }

    public void refreshToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
        binding.toolbar.settingView(toolbarRequest);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // NavController 설정
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView5);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        // 탭 클릭 리스너
        binding.tabContent.setOnClickListener(v -> {
            try {
                navController.navigate(R.id.action_to_content);
            } catch (Exception e) {
                // 이미 content 프래그먼트인 경우
            }
            updateTabUI(binding.tabContent);
        });

        binding.tabIntro.setOnClickListener(v -> {
            navController.navigate(R.id.action_to_intro);
            updateTabUI(binding.tabIntro);
        });

        binding.tabSituation.setOnClickListener(v -> {
            navController.navigate(R.id.action_to_situation);
            updateTabUI(binding.tabSituation);
        });

        binding.tabIntroduce.setOnClickListener(v -> {
            navController.navigate(R.id.action_to_introduce);
            updateTabUI(binding.tabIntroduce);
        });

        // 가이드 확인 버튼
        binding.btnConfirmGuide.setOnClickListener(v -> {
            binding.guideLayout.setVisibility(View.GONE);
        });

        // 툴바 설정
        ToolbarView.ToolbarRequest request = new ToolbarView.ToolbarRequest("제작", btnId -> {
            if (btnId == R.id.btn_temp_save) {
                // TODO: 임시저장 로직
                Log.d(TAG, "임시저장 클릭");
            }
        });
        binding.toolbar.settingView(request);
        binding.toolbar.setTemporarySave(request);

    }

    private void updateTabUI(View selectedTab) {
        // 모든 탭 회색으로
        binding.tabContent.setTextColor(getColor(R.color.gray));
        binding.tabIntro.setTextColor(getColor(R.color.gray));
        binding.tabSituation.setTextColor(getColor(R.color.gray));
        binding.tabIntroduce.setTextColor(getColor(R.color.gray));

        // 선택된 탭 흰색으로
        ((android.widget.TextView) selectedTab).setTextColor(getColor(R.color.white));

        // 인디케이터 이동
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) binding.tabIndicator.getLayoutParams();
        params.startToStart = selectedTab.getId();
        params.endToEnd = selectedTab.getId();
        binding.tabIndicator.setLayoutParams(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // showAds(false);
        Intent intent = getIntent();
        characterId = intent.getIntExtra("CHARACTER_ID", -1);
        if (characterId != -1) {
            changeBackStatus();
        }
        setToolbarHeight(binding.toolbar);
    }

    public void moveToMyProfile() {
        Intent intent = new Intent(MakeCharacterActivity.this, ProfileActivity.class);
        intent.putExtra("INIT_FRAGMENT", "Character");
        startActivity(intent);
    }

    public void showAds(boolean isGoProfile) {
        AdManager.showAdIfAvailable(this, () -> {
            if (isGoProfile) {
                moveToMyProfile();
            }
        });
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

}