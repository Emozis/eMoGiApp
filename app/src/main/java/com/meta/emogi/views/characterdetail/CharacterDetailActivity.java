package com.meta.emogi.views.characterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityCharacterDetailBinding;
import com.meta.emogi.databinding.ActivityChatListBinding;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.chatroom.ChatRoomActivity;
import com.meta.emogi.views.makecharacter.MakeCharacterActivity;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.views.toolbar.ToolbarView;
import com.meta.emogi.views.toolbar.ToolbarViewModel;

public class CharacterDetailActivity extends BaseActivity<ActivityCharacterDetailBinding> {

    private String accessToken;
    private ToolbarViewModel toolbarViewModel;
    private int characterId;

    @Override
    protected int layoutId() {
        return R.layout.activity_character_detail;
    }
    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
        binding.toolbar.settingView(toolbarRequest);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory factory = new ViewModelFactory(getApplication());
        toolbarViewModel = new ViewModelProvider(this, factory).get(ToolbarViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String data = intent.getStringExtra("ACCESS_TOKEN");
        characterId = intent.getIntExtra("CHARACTER_ID", -1);
        setAccessToken(data);
    }

    public int getCharacterId() {
        return characterId;
    }
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public void moveToChatRoom(int Roomid){
        Intent intent = new Intent(CharacterDetailActivity.this, ChatRoomActivity.class);
        Log.d("토큰확인", accessToken);
        intent.putExtra("ACCESS_TOKEN", accessToken);
        intent.putExtra("CHAT_ID", Roomid);
        startActivity(intent);
    }
}