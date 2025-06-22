package com.meta.emogi.views.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.meta.emogi.BuildConfig;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentLoginBinding;
import com.meta.emogi.domain.TokenManager;
import com.meta.emogi.data.network.model.TokenModel;
import com.meta.emogi.util.ConfigUtil;
import com.meta.emogi.views.toolbar.ToolbarView;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {

    private static final String TAG = "LoginFragment";
    private GoogleSignInClient mGoogleSignInClient;
    private LoginActivity activity;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return null;
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_login;
    }
    @Override
    protected Class<LoginViewModel> viewModelClass() {
        return LoginViewModel.class;
    }
    @Override
    protected void registerObservers() {
        viewModel.accessToken().observe(this, accessToken -> {
            String accessedToken = accessToken.getAccessToken();
            TokenManager.getInstance().setToken(accessedToken);
            activity.onLoginSuccess();
        });
    }

    private ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.w(TAG, "===== 로그인 결과 =====");
                Log.w(TAG, "결과 코드: " + result.getResultCode());
                Log.w(TAG, "RESULT_OK: " + Activity.RESULT_OK );
                Log.w(TAG, "RESULT_CANCELED: " + Activity.RESULT_CANCELED);
                try {
                    if (result.getResultCode() == Activity.RESULT_OK) {  // -1
                        Log.d(TAG, "로그인 성공");
                        Intent data = result.getData();
                        if (data != null) {
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(
                                    data);
                            handleSignInResult(task);
                        } else {
                            Log.e(TAG, "Intent 데이터가 null");
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {  // 0
                        Toast.makeText(
                                getContext(),
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

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            Log.d(TAG, "handleSignInResult 시작");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                Log.d(TAG, "계정 정보 성공 - 이메일: " + account.getEmail());
                Log.d(TAG, "ID Token 존재: " + (account.getIdToken() != null));

                if (account.getIdToken() != null) {
                    TokenModel requestToken = new TokenModel(account.getIdToken());
                    viewModel.createAccessToken(requestToken);
                    Log.d(TAG, "viewModel.createAccessToken 호출");
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "=== 구글 로그인 설정 확인 ===1");

        ConfigUtil configUtil = new ConfigUtil(requireContext());

        String serverClientId = configUtil.getProperty("SERVER_CLIENT_ID");
        Log.d(TAG, "SERVER_CLIENT_ID: " + serverClientId);
        Log.d(TAG, "패키지명: " + requireContext().getPackageName());

        try {
            // GoogleSignInOptions 초기화
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                    serverClientId).requestEmail().requestProfile().build();

            // GoogleSignInClient 생성
            mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

            Log.d(TAG, "=== ID Token 포함 설정으로 테스트 ===");

        } catch (Exception e) {
            Log.e(TAG, "Google Sign-In 초기화 실패: " + e.getMessage(), e);
        }

        viewModel.setAppVersion(BuildConfig.VERSION_NAME);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (LoginActivity) getActivity();

        binding.loginButton.setOnClickListener(v -> signIn());
    }

    private void signIn() {
        Log.d(TAG, "=== signIn 시작 ===");
        try {
            // 로그인 전에 항상 로그아웃 (계정 선택 화면 강제)
            mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
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

}