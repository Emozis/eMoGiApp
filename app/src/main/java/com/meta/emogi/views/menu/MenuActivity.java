package com.meta.emogi.views.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityMenuBinding;
import com.meta.emogi.views.chatlist.ChatListActivity;
import com.meta.emogi.views.chatroom.ChatRoomActivity;
import com.meta.emogi.views.login.LoginActivity;
import com.meta.emogi.views.makecharacter.MakeCharacterActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

public class MenuActivity extends BaseActivity<ActivityMenuBinding> {

    private String accessToken;
    private static final String TAG = "MenuActivity";
    
    @Override
    protected int layoutId() {
        return R.layout.activity_menu;
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
//        Log.d(TAG, data);
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void moveToChatList(){
        Intent intent = new Intent(MenuActivity.this, ChatListActivity.class);
        intent.putExtra("ACCESS_TOKEN", accessToken);
        startActivity(intent);
    }

    public void moveToMakeCharacter(){
        Intent intent = new Intent(MenuActivity.this, MakeCharacterActivity.class);
        intent.putExtra("ACCESS_TOKEN", accessToken);
        startActivity(intent);
    }



}