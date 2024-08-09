package com.meta.emogi.views.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityProfileBinding;
import com.meta.emogi.views.characterdetail.CharacterDetailActivity;
import com.meta.emogi.views.chatroom.ChatRoomActivity;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.views.profile.characterMangage.CharacterManageFragment;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.Objects;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding> {

    private String accessToken;

    @Override
    protected int layoutId() {
        return R.layout.activity_profile;
    }
    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
        binding.toolbar.settingView(toolbarRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFragment();
    }


    private void initFragment(){
        Intent intent = getIntent();
        String data = intent.getStringExtra("ACCESS_TOKEN");
        String initFragment = intent.getStringExtra("INIT_FRAGMENT");

        setAccessToken(data);
        if(Objects.equals(initFragment, "Character")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView4, new CharacterManageFragment());
            transaction.commit();
        }
    }

    public void moveToDetail(int characterId){
        Intent intent = new Intent(ProfileActivity.this, CharacterDetailActivity.class);
        Log.d("토큰확인", accessToken);
        intent.putExtra("ACCESS_TOKEN", accessToken);
        intent.putExtra("CHARACTER_ID", characterId);
        startActivity(intent);
    }


    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}