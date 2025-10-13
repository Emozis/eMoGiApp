package com.meta.emogi.util.ads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdManager {

    private static InterstitialAd interstitialAd;
    private static boolean isLoading = false;

    // 배포용
    private static final String AD_UNIT_ID = "ca-app-pub-2352851052199103/1924810392";
    // 테스트용
    // private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";

    // 앱 시작 시 한 번만 호출
    public static void init(Application app) {
        MobileAds.initialize(app, initializationStatus -> {
            // 초기화 완료 후 필요한 작업 수행 가능
        });
        loadAd(app); // 첫 광고 미리 로드
    }

    // 광고 로드가 필요할 때 호출
    public static void loadAd(Context context) {
        if (isLoading || interstitialAd != null) {
            return;
        }
        isLoading = true;
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd ad) {
                interstitialAd = ad;
                isLoading = false;
                Log.d("AdManager", "Ad was loaded.");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError error) {
                interstitialAd = null;
                isLoading = false;
                Log.d("AdManager", "Ad failed to load: " + error.getMessage());
            }
        });
    }

    public static void showAdIfAvailable(Activity activity, Runnable onDismissed) {
        if (interstitialAd != null && !activity.isFinishing()) {
            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // 광고가 닫히면 참조를 null로 만들고, 다음 광고를 미리 로드
                    interstitialAd = null;
                    loadAd(activity.getApplicationContext());
                    onDismissed.run();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    // 광고 표시에 실패해도 다음 광고를 로드
                    interstitialAd = null;
                    loadAd(activity.getApplicationContext());
                    onDismissed.run();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    // 광고가 성공적으로 표시됨
                    Log.d("AdManager", "Ad showed fullscreen content.");
                }
            });
            interstitialAd.show(activity);
        } else {
            Log.d("AdManager", "Ad was not ready to be shown.");
            onDismissed.run();
        }
    }

    public static boolean isAdAvailable() {
        return interstitialAd != null;
    }
}
