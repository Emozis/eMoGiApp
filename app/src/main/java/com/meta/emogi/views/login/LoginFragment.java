package com.meta.emogi.views.login;

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
                Log.w(TAG, "ActivityResultLauncher 호출됨 - 결과 코드: " + result.getResultCode());
                try {
                    if (result.getResultCode() == getActivity().RESULT_OK) {  // -1
                        Log.d(TAG, "로그인 성공");
                        Intent data = result.getData();
                        if (data != null) {
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            handleSignInResult(task);
                        }
                    } else if (result.getResultCode() == getActivity().RESULT_CANCELED) {  // 0
                        Log.d(TAG, "로그인 취소됨");
                        Toast.makeText(getContext(), "구글 서버와 연결이 되지 않았습니다. 나중에 시도해주세요", Toast.LENGTH_LONG).show();
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
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                Log.w(TAG, "계정 정보 - 이메일: " + account.getEmail() +
                        ", ID Token: " + account.getIdToken() +
                        ", Display Name: " + account.getDisplayName());

                TokenModel requestToken = new TokenModel(account.getIdToken());
                viewModel.createAccessToken(requestToken);
            }
        } catch (ApiException e) {
            Log.e(TAG, "Google Sign-In 실패 - 상태 코드: " + e.getStatusCode() +
                    ", 메시지: " + e.getMessage(), e);
        } catch (Exception e) {
            Log.e(TAG, "handleSignInResult 처리 중 예외 발생: " + e.getMessage(), e);
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConfigUtil configUtil = new ConfigUtil(requireContext());

        String serverClientId = configUtil.getProperty("SERVER_CLIENT_ID");

        try {
            // GoogleSignInOptions 초기화
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(serverClientId)
                    .requestEmail()
                    .requestProfile()
                    .build();

            // GoogleSignInClient 생성
            mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        } catch (Exception e) {
            Log.e(TAG, "Google Sign-In 초기화 실패: " + e.getMessage(), e);
        }

        viewModel.setAppVersion(BuildConfig.VERSION_NAME);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (LoginActivity) getActivity();

        binding.loginButton.setOnClickListener (v->signIn());
    }

    private void signIn() {
        try {
            // 로그인 전에 항상 로그아웃 (계정 선택 화면 강제)
            mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                if (signInIntent != null) {
                    signInLauncher.launch(signInIntent);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "signIn 메서드 예외 발생: " + e.getMessage(), e);
        }
    }


}