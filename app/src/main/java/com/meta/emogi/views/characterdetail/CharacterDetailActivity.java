package com.meta.emogi.views.characterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

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
    protected boolean isMainActivity() {
        return false;
    }

    @Override
    protected boolean hasBottomNavigation() {
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        characterId = intent.getIntExtra("CHARACTER_ID", -1);
        setToolbarHeight(binding.toolbar);
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }


    public void moveToChatRoom(int chatId,String chatUrl,String characterName){
        Intent intent = new Intent(CharacterDetailActivity.this, ChatRoomActivity.class);
        intent.putExtra("CHAT_ID", chatId);
        intent.putExtra("CHAT_URL", chatUrl);
        intent.putExtra("CHARACTER_NAME", characterName);
        startActivity(intent);
    }

    @Override
    protected void onBackPressedAction() {
        finish();
    }
}