package com.meta.emogi.base;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;
import com.meta.emogi.views.toolbar.ToolbarViewModel;
public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {

    protected V binding;
    private String accessToken;
    private ToolbarViewModel toolbarViewModel;
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    protected abstract @LayoutRes int layoutId();

    protected abstract void setToolbar(ToolbarView.ToolbarRequest toolbarRequest);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, layoutId());
        binding.setLifecycleOwner(this);

        ViewModelFactory factory = new ViewModelFactory(getApplication());
        toolbarViewModel = new ViewModelProvider(this, factory).get(ToolbarViewModel.class);

        toolbarViewModel.toolbar2Profile().observe(this, unused -> {
            toolbar2Profile(accessToken);
        });
    }

    public void toolbar2Profile(String accessToken){
        Intent intent = new Intent(BaseActivity.this, ProfileActivity.class);
        intent.putExtra("ACCESS_TOKEN", accessToken);
        startActivity(intent);
    }
}
