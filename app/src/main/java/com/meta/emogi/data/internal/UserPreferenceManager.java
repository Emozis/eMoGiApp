package com.meta.emogi.data.internal;

import android.content.Context;
import android.content.SharedPreferences;
public class UserPreferenceManager {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_TOKEN = "user_token";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // 로그인 정보 저장
    public void saveLoginInfo(String token) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_TOKEN, token);
        editor.apply();
    }

    // 로그인 상태 확인
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // 저장된 토큰 가져오기
    public String getUserToken() {
        return sharedPreferences.getString(KEY_USER_TOKEN, null);
    }

    // 로그아웃 시 정보 삭제
    public void logout() {
        editor.clear();
        editor.apply();
    }
}