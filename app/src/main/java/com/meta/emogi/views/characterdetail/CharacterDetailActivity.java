package com.meta.emogi.views.characterdetail;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityCharacterDetailBinding;
import com.meta.emogi.databinding.ActivityChatListBinding;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.toolbar.ToolbarView;
import com.meta.emogi.views.toolbar.ToolbarViewModel;

public class CharacterDetailActivity extends BaseActivity<ActivityCharacterDetailBinding> {

    private String accessToken;
    private ToolbarViewModel toolbarViewModel;

    @Override
    protected int layoutId() {
        return R.layout.activity_character_detail;
    }
    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
        binding.toolbar.settingView(toolbarRequest);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory factory = new ViewModelFactory(getApplication());
        toolbarViewModel = new ViewModelProvider(this, factory).get(ToolbarViewModel.class);
    }
}