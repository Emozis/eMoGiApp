package com.meta.emogi.base;
import static com.meta.emogi.MyApplication.getDeviceHeightPx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.meta.emogi.R;
import com.meta.emogi.data.internal.UserPreferenceManager;
import com.meta.emogi.views.chatlist.ChatListActivity;
import com.meta.emogi.views.login.LoginActivity;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;
import com.meta.emogi.views.toolbar.ToolbarViewModel;
public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    protected V binding;
    private ToolbarViewModel toolbarViewModel;
    protected UserPreferenceManager userPreferenceManager;
    private boolean backStatus = false;

    protected abstract @LayoutRes int layoutId();

    protected BottomNavigationView bottomNavigation;
    protected abstract boolean isMainActivity();
    protected abstract boolean hasBottomNavigation();
    protected int currentNavigationId = -1;

    protected abstract void setToolbar(ToolbarView.ToolbarRequest toolbarRequest);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layoutId());
        binding.setLifecycleOwner(this);

        userPreferenceManager = new UserPreferenceManager(this);
        toolbarViewModel = new ViewModelFactory(this).get(ToolbarViewModel.class);

        toolbarViewModel.back().observe(this, unused -> {

            if (backStatus) {
                Log.d("www", "프레그먼트 뒤로가기눌림");
                getOnBackPressedDispatcher().onBackPressed();
                backStatus = false;
            } else {
                Log.d("www", "액티비티 뒤로가기눌림");
                onBackPressedAction();
            }

        });

        //        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
        //            @Override
        //            public void handleOnBackPressed() {
        //                Log.w("www", "onBackPressedDispatcher에서 호출됨");
        //                onBackPressedAction();
        //            }
        //        });

        setStatusBarColor();
    }
    protected void logout() {
        if (userPreferenceManager != null) {
            userPreferenceManager.logout();
        }

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 네비게이션이 있고 유효한 ID가 설정되어 있으면 업데이트
        if (hasBottomNavigation() && bottomNavigation != null && currentNavigationId != -1) {
            bottomNavigation.setSelectedItemId(currentNavigationId);
        }
    }

    protected void setupBottomNavigation(BottomNavigationView bottomNav, int currentItemId) {
        this.bottomNavigation = bottomNav;
        this.currentNavigationId = currentItemId; // ID 저장

        bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == currentItemId) {
                return true;
            }

            Intent intent = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                intent = new Intent(this, MenuActivity.class);
            } else if (itemId == R.id.nav_chat_list) {
                intent = new Intent(this, ChatListActivity.class);
            } else if (itemId == R.id.nav_profile) {
                intent = new Intent(this, ProfileActivity.class);
            }

            if (intent != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            return true;
        });

        bottomNavigation.setSelectedItemId(currentItemId);
    }

    public void changeBackStatus() {
        backStatus = true;
    }

    private void setStatusBarColor() {
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.main_black));
    }

    protected void onBackPressedAction() {
        if (isMainActivity()) {
            finishAffinity();
        } else {
            super.onBackPressed();
        }
    }

    protected void setToolbarHeight(ToolbarView toolbar) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) toolbar.getLayoutParams();

        int heightInPx = (int) (getDeviceHeightPx() * 0.1);

        layoutParams.height = heightInPx;
        toolbar.setLayoutParams(layoutParams);
    }

}
