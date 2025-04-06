package com.meta.emogi.views.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityMenuBinding;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.characterdetail.CharacterDetailActivity;
import com.meta.emogi.views.characterdetail.CharacterDetailViewModel;
import com.meta.emogi.views.chatlist.ChatListActivity;
import com.meta.emogi.views.chatroom.ChatRoomActivity;
import com.meta.emogi.views.login.LoginActivity;
import com.meta.emogi.views.makecharacter.MakeCharacterActivity;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;
import com.meta.emogi.views.toolbar.ToolbarViewModel;

public class MenuActivity extends BaseActivity<ActivityMenuBinding> {

    private static final String TAG = "MenuActivity";

    @Override
    protected int layoutId() {
        return R.layout.activity_menu;
    }
//    @Override
//    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
//        binding.toolbar.settingView(toolbarRequest);
//    }

    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
    }


    public void moveToChatList(){
        Intent intent = new Intent(MenuActivity.this, ChatListActivity.class);
        startActivity(intent);
    }

    public void moveToMakeCharacter(){
        Intent intent = new Intent(MenuActivity.this, MakeCharacterActivity.class);
        startActivity(intent);
    }

    public void moveToCharacterDetail(int characterId){
        Intent intent = new Intent(MenuActivity.this, CharacterDetailActivity.class);
        intent.putExtra("CHARACTER_ID", characterId);
        startActivity(intent);
    }

    public void moveToManageProfile(){
        Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
        intent.putExtra("INIT_FRAGMENT", "Character");
        startActivity(intent);
    }

    public void moveToMyPageProfile(){
        Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

}