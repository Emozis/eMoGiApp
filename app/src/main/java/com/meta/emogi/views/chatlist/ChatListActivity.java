package com.meta.emogi.views.chatlist;

import android.content.Intent;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityChatListBinding;
import com.meta.emogi.data.network.model.ChatResponse;
import com.meta.emogi.views.chatroom.ChatRoomActivity;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.List;

public class ChatListActivity extends BaseActivity<ActivityChatListBinding> {

    public List<ChatResponse> chatList;

    @Override
    protected int layoutId() {
        return R.layout.activity_chat_list;
    }
    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
        binding.toolbar.settingView(toolbarRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        setToolbarHeight(binding.toolbar);
    }


    public void moveToChatRoom(int chatId,String chatUrl,String characterName){
        Intent intent = new Intent(ChatListActivity.this, ChatRoomActivity.class);
        intent.putExtra("CHAT_ID", chatId);
        intent.putExtra("CHAT_URL", chatUrl);
        intent.putExtra("CHARACTER_NAME", characterName);

        startActivity(intent);
    }

    public void moveToMyProfile(){
        Intent intent = new Intent(ChatListActivity.this, ProfileActivity.class);
        intent.putExtra("INIT_FRAGMENT", "Character");
        startActivity(intent);
    }

    public List<ChatResponse> getChatList() {
        return chatList;
    }
    public void setChatList(List<ChatResponse> chatList) {
        this.chatList = chatList;
    }
}