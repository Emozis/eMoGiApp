package com.meta.emogi.views.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.data.internal.UserPreferenceManager;
import com.meta.emogi.databinding.ActivitySplashBinding;
import com.meta.emogi.domain.TokenManager;
import com.meta.emogi.views.login.LoginActivity;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_splash;
    }
    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
    }
    private static final String TAG = "SplashActivity";

    private UserPreferenceManager userPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userPreferenceManager = new UserPreferenceManager(this);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            checkLoginStatus();
        }, 2000);
    }

    private void checkLoginStatus() {
        if (userPreferenceManager.isLoggedIn()) {
            String token = userPreferenceManager.getUserToken();
            if (isTokenValid(token)) {
                TokenManager.getInstance().setToken(token);
                goToMainActivity();
                Log.d(TAG, "토큰있음 "+token);
            } else {
                goToLoginActivity();
                Log.d(TAG, "토큰없음 ");
            }
        } else {
            goToLoginActivity();
        }
    }

    private boolean isTokenValid(String token) {
        // TODO: v2에서는 서버에 토큰 유효성 검사 요청
        return token != null && !token.isEmpty();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected boolean isMainActivity() {
        return false;
    }

    @Override
    protected boolean hasBottomNavigation() {
        return false;
    }
}