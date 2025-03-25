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

    public void refreshToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
        binding.toolbar.settingView(toolbarRequest);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            MobileAds.initialize(this, initializationStatus -> {});
        }).start();

        AdRequest adRequest = new AdRequest.Builder().build();

        //실제 id
//        String adId = "ca-app-pub-2352851052199103/1924810392";
        //테스트용 id
        String adId = "ca-app-pub-3940256099942544/1033173712";

        InterstitialAd.load(this,
                            adId,
                            adRequest,
                            new InterstitialAdLoadCallback() {
                                @Override
                                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                    // The mInterstitialAd reference will be null until
                                    // an ad is loaded.
                                    mInterstitialAd = interstitialAd;
                                    Log.d("wwwM", "mInterstitialAd 객체 생성 성공");
                                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                                        @Override
                                        public void onAdClicked() {
                                            // Called when a click is recorded for an ad.
                                            Log.d(TAG, "Ad was clicked.");
                                        }

                                        @Override
                                        public void onAdDismissedFullScreenContent() {
                                            // Called when ad is dismissed.
                                            // Set the ad reference to null so you don't show the ad a second time.
                                            Log.d(TAG, "Ad dismissed fullscreen content.");
                                            mInterstitialAd = null;
                                            moveToMyProfile();
                                        }

                                        @Override
                                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                                            // Called when ad fails to show.
                                            Log.e(TAG, "Ad failed to show fullscreen content.");
                                            mInterstitialAd = null;
                                            moveToMyProfile();
                                        }

                                        @Override
                                        public void onAdImpression() {
                                            // Called when an impression is recorded for an ad.
                                            Log.d(TAG, "Ad recorded an impression.");
                                        }

                                        @Override
                                        public void onAdShowedFullScreenContent() {
                                            // Called when ad is shown.
                                            Log.d(TAG, "Ad showed fullscreen content.");
                                        }
                                    });
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                    // Handle the error
                                    String error =
                                            String.format(
                                                    java.util.Locale.US,
                                                    "domain: %s, code: %d, message: %s",
                                                    loadAdError.getDomain(),
                                                    loadAdError.getCode(),
                                                    loadAdError.getMessage());
                                    Log.d("wwwM", "mInterstitialAd 객체 생성 실패");
                                    Log.d("wwwM", error);
                                    mInterstitialAd = null;
                                }
                            }
        );
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        setAccessToken(intent.getStringExtra("ACCESS_TOKEN"));
        characterId = intent.getIntExtra("CHARACTER_ID", -1);
        setToolbarHeight(binding.toolbar);
    }

    public void moveToMyProfile() {
        Intent intent = new Intent(MakeCharacterActivity.this, ProfileActivity.class);
        intent.putExtra("ACCESS_TOKEN", getAccessToken());
        intent.putExtra("INIT_FRAGMENT", "Character");
        startActivity(intent);
    }

    public void showAds(){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(MakeCharacterActivity.this);
        } else {
            Log.d("www", "광고 객체 생성안됨");
        }
    }

    public int getCharacterId() {
        return characterId;
    }
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

}