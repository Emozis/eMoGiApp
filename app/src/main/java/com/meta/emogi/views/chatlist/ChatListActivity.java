package com.meta.emogi.views.chatlist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityChatListBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

public class ChatListActivity extends BaseActivity<ActivityChatListBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_chat_list;
    }
    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {

    }
}