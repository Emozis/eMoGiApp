package com.meta.emogi;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        getDeviceSize();
        instance = this;
    }

    public synchronized static MyApplication getInstance(){
        return instance;
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