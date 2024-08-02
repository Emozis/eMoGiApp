package com.meta.emogi.views.makecharacter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityMakeCharacterBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

public class MakeCharacterActivity extends BaseActivity<ActivityMakeCharacterBinding> {

    private String accessToken;
    @Override
    protected int layoutId() {
        return R.layout.activity_make_character;
    }

    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
        binding.toolbar.settingView(toolbarRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String data = intent.getStringExtra("ACCESS_TOKEN");
        setAccessToken(data);
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}