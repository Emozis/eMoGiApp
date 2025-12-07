package com.meta.emogi;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.kakao.sdk.common.KakaoSdk;

import dagger.hilt.android.HiltAndroidApp;
import com.meta.emogi.util.ads.AdManager;

@HiltAndroidApp
public class EmogiApp extends Application {

    private static EmogiApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        getDeviceSize();
        AdManager.init(this);
        instance = this;
        KakaoSdk.init(this, "d692ff90ee5d05d30288f303aa3d4313");
    }

    public synchronized static EmogiApp getInstance(){
        return instance;
    }


    public Context getAppContext(){
        return instance.getApplicationContext();
    }

    private void getDeviceSize(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        Log.w("www", "getDeviceSize:  ");
        deviceHeightPx = displayMetrics.heightPixels;
        deviceWidthPx = displayMetrics.widthPixels;
    }

    private static int deviceHeightPx;
    private static int deviceWidthPx;

    public static int getDeviceHeightPx() {
        return deviceHeightPx;
    }

    public static int getDeviceWidthPx() {
        return deviceWidthPx;
    }
}