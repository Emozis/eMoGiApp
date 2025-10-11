package com.meta.emogi.views.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.meta.emogi.BuildConfig;
import com.meta.emogi.R;
import com.meta.emogi.data.network.model.TokenModel;
import com.meta.emogi.databinding.ActivityLoginBinding;
import com.meta.emogi.domain.TokenManager;
import com.meta.emogi.util.ConfigUtil;
import com.meta.emogi.views.menu.MenuActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private static final int UPDATE_REQUEST_CODE = 1234;

    private ActivityLoginBinding binding;

    private LoginViewModel viewModel;

    private GoogleSignInClient mGoogleSignInClient;

    // use case 로그인 시도 결과 반환
    private ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                try {
                    if (result.getResultCode() == Activity.RESULT_OK) {  // -1
                        Intent data = result.getData();
                        if (data != null) {
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            handleSignInResult(task);
                        } else {
                            Log.e(TAG, "Intent 데이터가 null");
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {  // 0
                        Toast.makeText(
                                this,
                                "구글 서버와 연결이 되지 않았습니다. 나중에 시도해주세요",
                                Toast.LENGTH_LONG
                        ).show();
                    } else {
                        Log.w(TAG, "알 수 없는 결과 코드: " + result.getResultCode());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "ActivityResultLauncher 처리 중 예외 발생: " + e.getMessage(), e);
                }
            }
    );

    //토큰 반환 api 호출 usecase
    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                if (account.getIdToken() != null) {
                    viewModel.handleGoogleIdToken(account.getIdToken());
                } else {
                    Log.e(TAG, "ID Token이 null");
                }
            } else {
                Log.e(TAG, "account가 null");
            }
        } catch (ApiException e) {
            Log.e(TAG, "=== 구글 로그인 실패 ===");
        } catch (Exception e) {
            Log.e(TAG, "일반 Exception: " + e.getMessage(), e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        String oAuthClientId = viewModel.getOauthClientId();
        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(oAuthClientId).requestEmail().requestProfile().build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        } catch (Exception e) {
            Log.e(TAG, "Google Sign-In 초기화 실패: " + e.getMessage(), e);
        }

        viewModel.setAppVersion(BuildConfig.VERSION_NAME);

        viewModel.getAccessToken().observe(this, accessToken -> {
            String accessedToken = accessToken.getAccessToken();
            TokenManager.getInstance().setToken(accessedToken);
            onLoginSuccess();
        });

        binding.loading.setViewModel(viewModel);
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
    @Override
    protected void onResume() {
        super.onResume();
        binding.googleLoginButton.setOnClickListener(v -> signIn());
    }

    private void signIn() {
        try {
            // 로그인 전에 항상 로그아웃 (계정 선택 화면 강제)
            mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                if (signInIntent != null) {
                    signInLauncher.launch(signInIntent);
                } else {
                    Log.e(TAG, "signInIntent가 null - GoogleSignInClient 설정 문제");
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "signIn 메서드 예외 발생: " + e.getMessage(), e);
        }
    }

    private static final String TAG = "LoginActivity";

    public void onLoginSuccess() {
        String token = TokenManager.getInstance().getToken();

        // 정보저장 플로우 만들기
//        userPreferenceManager.saveLoginInfo(token);
        moveToMainActivity();
    }


    public void moveToMainActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}