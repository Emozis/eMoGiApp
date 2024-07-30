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

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private static final String TAG = "LoginActivity";
    private String jwtToken="";
    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    public void setJwtToken(String jwtToken){
        this.jwtToken=jwtToken;
    }

    public void moveActivity(){
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.putExtra("ACCESS_TOKEN", jwtToken);
        startActivity(intent);
    }

}