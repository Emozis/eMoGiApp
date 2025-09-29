package com.meta.emogi.views.splash;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.meta.emogi.R;
import com.meta.emogi.views.login.LoginActivity;
import com.meta.emogi.views.menu.MenuActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

    private static final int UPDATE_REQUEST_CODE = 1234;
    private SplashViewModel viewModel;
    AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        appUpdateManager = AppUpdateManagerFactory.create(this);

        viewModel.getDestination().observe(this, dest -> {
            switch (dest){
                case LOGIN:
                    goToLoginActivity();
                    break;
                case HOME:
                    goToMainActivity();
                    break;
                case UPDATE:
                    startImmediateUpdate();
                    break;
                default:
                    finish();
                    break;
            }
        });

        viewModel.decideNext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(info -> {
            if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            info, AppUpdateType.IMMEDIATE, this, UPDATE_REQUEST_CODE
                    );
                } catch (Exception ignored) {}
            }
        });
    }
    private void goToMainActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                finish();
            }
        }
    }

    private void startImmediateUpdate(){
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(info -> {
            try{
                appUpdateManager.startUpdateFlowForResult(
                        info,
                        AppUpdateType.IMMEDIATE,
                        this,
                        UPDATE_REQUEST_CODE);
            }catch (Exception e){
                e.printStackTrace();
                finish();
            }
        }) .addOnFailureListener(e -> {
            // 정보 조회 실패 → 강제 정책: 진입 차단
            e.printStackTrace();
            finish();
        });

    }
}