package com.meta.emogi.base;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.meta.emogi.views.toolbar.ToolbarView;
public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {

    protected V binding;
    private String accessToken;
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
    }
}
