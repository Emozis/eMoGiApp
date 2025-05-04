package com.meta.emogi.util.ads;
import android.app.Activity;
import android.app.Application;
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
    //테스트용
//    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";

    public static void init(Application app) {
        if (interstitialAd == null && !isLoading) {

            isLoading = true;
            MobileAds.initialize(app);
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(app, AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd ad) {
                    interstitialAd = ad;
                    isLoading = false;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError error) {
                    interstitialAd = null;
                    isLoading = false;
                }
            });
        }
    }

    public static void showAdIfAvailable(Activity activity, Runnable onDismissed) {
        if (interstitialAd != null) {
            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    interstitialAd = null;
                    init(activity.getApplication());
                    onDismissed.run();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    interstitialAd = null;
                    init(activity.getApplication());
                    onDismissed.run();
                }
            });
            interstitialAd.show(activity);
        } else {
            onDismissed.run();
        }
    }

    public static boolean isAdAvailable() {
        return interstitialAd != null;
    }
}