package com.meta.emogi.base;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.meta.emogi.views.login.LoginActivity;
import com.meta.emogi.views.menu.MenuActivity;
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

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onBackPressedAction(); // 커스터마이징 가능한 메서드 호출
            }
        });
    }

    protected void onBackPressedAction() {
        Intent intent = new Intent(BaseActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish(); // 현재 액티비티를 종료하여 뒤로 가기 스택에서 제거
    }
}
