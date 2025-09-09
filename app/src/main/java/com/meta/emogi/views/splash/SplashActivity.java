package com.meta.emogi.views.splash;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.meta.emogi.R;
import com.meta.emogi.domain.auth.entity.StartDestination;
import com.meta.emogi.views.login.LoginActivity;
import com.meta.emogi.views.menu.MenuActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {
    private SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        viewModel.getDestination().observe(this, dest -> {
            if(dest == StartDestination.Go2Login.INSTANCE){
                goToLoginActivity();
            }else if(dest == StartDestination.Go2Main.INSTANCE){
                goToMainActivity();
            }else{
                finish();
            }
        });

        viewModel.decideNext();
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
}