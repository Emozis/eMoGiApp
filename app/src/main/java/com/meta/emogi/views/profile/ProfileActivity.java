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
import androidx.navigation.NavGraph;
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

        NavController navController = Navigation.findNavController(this, R.id.profileChildFrag);
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.profile_nav);

        if (Objects.equals(initFragment, "Character")) {
            navGraph.setStartDestination(R.id.characterManageFragment);
        } else {
            navGraph.setStartDestination(R.id.myPageFragment);
        }
        navController.setGraph(navGraph);
    }

    public void moveToDetail(int characterId){
        Intent intent = new Intent(ProfileActivity.this, CharacterDetailActivity.class);
        intent.putExtra("ACCESS_TOKEN", getAccessToken());
        intent.putExtra("CHARACTER_ID", characterId);
        startActivity(intent);
    }

    @Override
    public void toolbar2Profile(String accessToken){
    }

    @Override
    public void onBackPressed() {
        onBackPressedAction();
        super.onBackPressed();
    }

}