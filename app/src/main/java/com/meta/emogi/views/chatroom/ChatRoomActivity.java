package com.meta.emogi.views.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setAccessToken(intent.getStringExtra("ACCESS_TOKEN"));
        chatId = intent.getIntExtra("CHAT_ID",0);
        chatUrl = intent.getStringExtra("CHAT_URL");
        setChatId(chatId);
        setChatUrl(chatUrl);
    }

    @Override
    protected void onBackPressedAction() {
        finish();
    }

}