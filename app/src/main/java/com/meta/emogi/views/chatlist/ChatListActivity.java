package com.meta.emogi.views.chatlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityChatListBinding;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.views.characterdetail.CharacterDetailActivity;
import com.meta.emogi.views.chatroom.ChatRoomActivity;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.List;

public class ChatListActivity extends BaseActivity<ActivityChatListBinding> {

    public List<ChatListModel> chatList;

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
        String data = intent.getStringExtra("ACCESS_TOKEN");
        setAccessToken(data);
        //        Log.d(TAG, data);
    }


    public void moveToChatRoom(int chatId,String chatUrl,String characterName){
        Intent intent = new Intent(ChatListActivity.this, ChatRoomActivity.class);
        intent.putExtra("ACCESS_TOKEN", getAccessToken());
        intent.putExtra("CHAT_ID", chatId);
        intent.putExtra("CHAT_URL", chatUrl);
        intent.putExtra("CHARACTER_NAME", characterName);

        startActivity(intent);
    }

    public void moveToMyProfile(){
        Intent intent = new Intent(ChatListActivity.this, ProfileActivity.class);
        intent.putExtra("ACCESS_TOKEN", getAccessToken());
        intent.putExtra("INIT_FRAGMENT", "Character");
        startActivity(intent);
    }

    public List<ChatListModel> getChatList() {
        return chatList;
    }
    public void setChatList(List<ChatListModel> chatList) {
        this.chatList = chatList;
    }
}