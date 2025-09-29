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
                Log.w(TAG, "===== 로그인 시도 =====");
                try {
                    if (result.getResultCode() == Activity.RESULT_OK) {  // -1
                        Log.d(TAG, "로그인 성공");
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
            Log.d(TAG, "handleSignInResult 시작");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                if (account.getIdToken() != null) {
                    Log.d(TAG, "ID Token 존재: " + (account.getIdToken() != null));
                    TokenModel requestToken = new TokenModel(account.getIdToken());
                    viewModel.createAccessToken(requestToken);
                } else {
                    Log.e(TAG, "ID Token이 null");
                }
            } else {
                Log.e(TAG, "account가 null");
            }
        } catch (ApiException e) {
            Log.e(TAG, "=== 구글 로그인 실패 ===");
            Log.e(TAG, "상태 코드: " + e.getStatusCode());
            Log.e(TAG, "상태 메시지: " + e.getStatus());
            Log.e(TAG, "에러 메시지: " + e.getMessage());
            Log.e(TAG, "로컬라이즈된 메시지: " + e.getLocalizedMessage());
            // 주요 상태 코드:
            // 10: DEVELOPER_ERROR (설정 문제)
            // 12500: SIGN_IN_CANCELLED
            // 12501: SIGN_IN_FAILED
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


        ConfigUtil configUtil = new ConfigUtil(this);

        // 서버 OAuth 클라이언트 ID 가져오기
        String oAuthClientId = configUtil.getProperty("OAUTH_CLIENT_ID");
        Log.d(TAG, "OAUTH_CLIENT_ID: " + oAuthClientId);
        Log.d(TAG, "패키지명: " + this.getPackageName());

        try {
            // GoogleSignInOptions 초기화
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(oAuthClientId).requestEmail().requestProfile().build();
            // GoogleSignInClient 생성
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
        binding.loginButton.setOnClickListener(v -> signIn());

        // FIXME: 2025. 9. 11. 없애야함
        viewModel.loadingSuccess();
    }

    private void signIn() {
        Log.d(TAG, "=== signIn 시작 ===");
        try {
            // 로그인 전에 항상 로그아웃 (계정 선택 화면 강제)
            mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
                Log.d(TAG, "signOut 완료 - 성공: " + task.isSuccessful());

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                Log.d(TAG, "signInIntent 생성 - null 여부: " + (signInIntent == null));

                if (signInIntent != null) {
                    Log.d(TAG, "signInLauncher.launch 실행");
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