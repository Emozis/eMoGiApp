package com.meta.emogi.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.meta.emogi.MyApplication;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.data.internal.UserPreferenceManager;
import com.meta.emogi.databinding.ActivityLoginBinding;
import com.meta.emogi.domain.TokenManager;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.views.splash.SplashActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private static final int UPDATE_REQUEST_CODE = 1234;
    private AppUpdateManager appUpdateManager;
    private UserPreferenceManager userPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        userPreferenceManager = new UserPreferenceManager(this);

        // AppUpdateManager 초기화
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // 업데이트 상태 확인
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                // 즉시 업데이트 요청
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            UPDATE_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected boolean isMainActivity() {
        return false;
    }

    @Override
    protected boolean hasBottomNavigation() {
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_REQUEST_CODE && resultCode != RESULT_OK) {
            // 업데이트 실패 시 처리
            Toast.makeText(this, "업데이트가 필요합니다.", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }
    }


    private static final String TAG = "LoginActivity";

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }
    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
    }

    public void onLoginSuccess() {
        String token = TokenManager.getInstance().getToken();
        userPreferenceManager.saveLoginInfo(token);
        moveToMainActivity();
    }


    public void moveToMainActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}