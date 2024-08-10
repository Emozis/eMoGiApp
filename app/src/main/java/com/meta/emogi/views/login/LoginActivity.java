package com.meta.emogi.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityLoginBinding;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.views.splash.SplashActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private static final String TAG = "LoginActivity";


    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }
    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {

    }

    public void moveActivity(){
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.putExtra("ACCESS_TOKEN", getAccessToken());
        startActivity(intent);
    }

}