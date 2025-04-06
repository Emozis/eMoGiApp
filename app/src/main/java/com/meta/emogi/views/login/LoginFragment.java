package com.meta.emogi.views.login;

import static com.meta.emogi.MyApplication.getDeviceHeightPx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
import com.meta.emogi.network.TokenManager;
import com.meta.emogi.network.datamodels.TokenModel;
import com.meta.emogi.util.ConfigUtil;
import com.meta.emogi.views.toolbar.ToolbarView;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {

    private static final String TAG = "www";
    private GoogleSignInClient mGoogleSignInClient;
    private LoginActivity activity;

    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

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
            TokenManager.getInstance().setTotken(accessedToken);
            activity.moveActivity();
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "LoginFragment - onCreate 시작");
        Log.i(TAG, "222 LoginFragment - onCreate 시작");
        ConfigUtil configUtil = new ConfigUtil(requireContext());

        String serverClientId = configUtil.getProperty("SERVER_CLIENT_ID");
        Log.w("www", "check id: "+serverClientId);



        try {
            // GoogleSignInOptions 초기화
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(serverClientId)
                    .requestEmail()
                    .requestProfile()
                    .build();
            Log.w(TAG, "GoogleSignInOptions 생성 성공");

            // GoogleSignInClient 생성
            mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
            Log.w(TAG, "GoogleSignInClient 생성 성공");

            // 기존 로그인 상태 확인
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireActivity());
            if (account != null) {
                Log.w(TAG, "이미 로그인된 계정 존재 - 이메일: " + account.getEmail());
            } else {
                Log.w(TAG, "기존 로그인된 계정 없음");
            }

        } catch (Exception e) {
            Log.e(TAG, "Google Sign-In 초기화 실패: " + e.getMessage(), e);
        }

        viewModel.setAppVersion(String.valueOf(BuildConfig.VERSION_NAME));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (LoginActivity) getActivity();

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(); // Google 로그인을 시작하는 메서드 호출
            }
        });
    }

    private ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.w(TAG, "ActivityResultLauncher 호출됨 - 결과 코드: " + result.getResultCode());
                try {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Log.w(TAG, "로그인 성공");
                        Intent data = result.getData();
                        if (data != null) {
                            Log.w(TAG, "로그인 Intent 데이터 존재 - Extras: " + data.getExtras());
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            handleSignInResult(task);
                        } else {
                            Log.w(TAG, "로그인 Intent 데이터 없음");
                        }
                    } else if (result.getResultCode() == getActivity().RESULT_CANCELED) {
                        Log.w(TAG, "RESULT_CANCELED - 로그인 취소 또는 실패");
                    } else {
                        Log.w(TAG, "알 수 없는 결과 코드: " + result.getResultCode());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "ActivityResultLauncher 처리 중 예외 발생: " + e.getMessage(), e);
                }
            }
    );

    private void signIn() {
        Log.w(TAG, "로그인 시도 - signIn 메서드 호출");
        try {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            if (signInIntent == null) {
                Log.e(TAG, "SignInIntent 생성 실패 - Intent가 null");
                return;
            }
            Log.w(TAG, "SignInIntent 생성 성공 - Extras: " + signInIntent.getExtras());
            signInLauncher.launch(signInIntent);
        } catch (Exception e) {
            Log.e(TAG, "signIn 메서드 예외 발생: " + e.getMessage(), e);
        }
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        Log.w(TAG, "handleSignInResult 호출됨");
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                Log.w(TAG, "Google Sign-In 성공");
                Log.w(TAG, "계정 정보 - 이메일: " + account.getEmail() +
                        ", ID Token: " + account.getIdToken() +
                        ", Display Name: " + account.getDisplayName());

                TokenModel requestToken = new TokenModel(account.getIdToken());
                Log.w(TAG, "TokenModel 생성 성공 - 토큰 요청 준비 완료");
                viewModel.createAccessToken(requestToken);
            } else {
                Log.w(TAG, "GoogleSignInAccount 객체가 null입니다.");
            }
        } catch (ApiException e) {
            Log.e(TAG, "Google Sign-In 실패 - 상태 코드: " + e.getStatusCode() +
                    ", 메시지: " + e.getMessage(), e);
        } catch (Exception e) {
            Log.e(TAG, "handleSignInResult 처리 중 예외 발생: " + e.getMessage(), e);
        }
    }

}