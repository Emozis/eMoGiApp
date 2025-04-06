package com.meta.emogi.views.chatroom;

import static com.meta.emogi.MyApplication.getDeviceHeightPx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityChatRoomBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

public class ChatRoomActivity extends BaseActivity<ActivityChatRoomBinding> {

    private int chatId;
    private String chatUrl;
    private String characterName;
    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
        binding.toolbar.settingView(toolbarRequest);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_chat_room;
    }

    public int getChatId() {
        return chatId;
    }
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
    public String getChatUrl() {
        return chatUrl;
    }
    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }
    public String getCharacterName() {
        return characterName;
    }
    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        chatId = intent.getIntExtra("CHAT_ID", 0);
        chatUrl = intent.getStringExtra("CHAT_URL");
        characterName = intent.getStringExtra("CHARACTER_NAME");
        setChatId(chatId);
        setChatUrl(chatUrl);
        setCharacterName(characterName);
        setToolbarHeight(binding.toolbar);
    }

    @Override
    protected void onBackPressedAction() {
        finish();
    }
}