package com.meta.emogi.views.makecharacter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityMakeCharacterBinding;
import com.meta.emogi.util.ads.AdManager;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MakeCharacterActivity extends BaseActivity<ActivityMakeCharacterBinding> {

    private static final String TAG = "MakeCharacterActivity";
    private InterstitialAd mInterstitialAd;
    private int characterId;
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

    }
    @Override
    protected void onResume() {
        super.onResume();
        showAds(false);
        Intent intent = getIntent();
        characterId = intent.getIntExtra("CHARACTER_ID", -1);
        if(characterId!=-1){
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
            if(isGoProfile){
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