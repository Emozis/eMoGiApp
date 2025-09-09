package com.meta.emogi.views.splash;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.meta.emogi.R;
import com.meta.emogi.data.auth.local.UserPreferenceLocalDataSource;
import com.meta.emogi.data.auth.repo.SessionRepositoryImpl;
import com.meta.emogi.domain.auth.repo.SessionRepository;
import com.meta.emogi.domain.auth.usecase.IsLoggedInUseCase;
import com.meta.emogi.views.login.LoginActivity;
import com.meta.emogi.views.menu.MenuActivity;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    private SplashViewModel viewModel;

    public SplashActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        UserPreferenceLocalDataSource local = new UserPreferenceLocalDataSource(this);
        SessionRepository repo = new SessionRepositoryImpl(local);
        IsLoggedInUseCase useCase = new IsLoggedInUseCase(repo);

        viewModel = new ViewModelProvider(this,new ViewModelProvider.Factory(){
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
                return (T) new SplashViewModel(useCase);
            }
        }).get(SplashViewModel.class);


        viewModel.getGoMain().observe(this, flag -> {
            if (Boolean.TRUE.equals(flag)) {
                goToMainActivity();
            } else if (Boolean.FALSE.equals(flag)) {
                goToLoginActivity();
            }
            finish();
        });

        viewModel.decide();
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