package com.meta.emogi.views.chatroom;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityChatRoomBinding;

public class ChatRoomActivity extends BaseActivity<ActivityChatRoomBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_chat_room;
    }
}