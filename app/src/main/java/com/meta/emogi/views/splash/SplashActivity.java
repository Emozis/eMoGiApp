package com.meta.emogi.views.splash;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.meta.emogi.BuildConfig;
import com.meta.emogi.R;
import com.meta.emogi.views.login.LoginActivity;
import com.meta.emogi.views.menu.MenuActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    private static final int UPDATE_REQUEST_CODE = 1234;
    private SplashViewModel viewModel;
    AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        appUpdateManager = AppUpdateManagerFactory.create(this);


        /// TODO: 2025. 10. 14. 디버그 모드 설정
        if (BuildConfig.DEBUG) { // 디버그 모드일 때만 실행
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }

        // 첫 페이지 설정
        viewModel.getDestination().observe(this, dest -> {
            switch (dest){
                case LOGIN:
                    Log.d(TAG, "goToLoginActivity");
                    goToLoginActivity();
                    break;
                case HOME:
                    Log.d(TAG, "goToMainActivity");
                    goToMainActivity();
                    break;
                case UPDATE:
                    Log.d(TAG, "startImmediateUpdate");
                    startImmediateUpdate();
                    break;
                default:
                    Log.d(TAG, "finish");
                    finish();
                    break;
            }
        });
        viewModel.decideNext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //앱 업데이트 필요 확인
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

    private void goToDebugActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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
            e.printStackTrace();
            finish();
        });

    }
}