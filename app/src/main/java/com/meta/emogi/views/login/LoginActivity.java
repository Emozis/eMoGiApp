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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityLoginBinding;
import com.meta.emogi.views.menu.MenuActivity;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.meta.emogi.views.toolbar.ToolbarView;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private static final int UPDATE_REQUEST_CODE = 1234;

    private ActivityLoginBinding binding;

    private LoginViewModel viewModel;

    private GoogleSignInClient mGoogleSignInClient;

    private Task<GoogleSignInAccount> task;

    // use case 로그인 시도 결과 반환
    private ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                                                                                      result -> {
                                                                                          try {
                                                                                              if (result.getResultCode() == Activity.RESULT_OK) {  // -1
                                                                                                  Intent data = result.getData();
                                                                                                  if (data != null) {
                                                                                                      task = GoogleSignIn.getSignedInAccountFromIntent(
                                                                                                              data);
                                                                                                  } else {
                                                                                                      Log.e(
                                                                                                              TAG,
                                                                                                              "Intent 데이터가 null"
                                                                                                      );
                                                                                                  }
                                                                                              } else if (result.getResultCode() == Activity.RESULT_CANCELED) {  // 0
                                                                                                  Toast.makeText(
                                                                                                          this,
                                                                                                          "구글 로그인에 실패했습니다.",
                                                                                                          Toast.LENGTH_LONG
                                                                                                  ).show();
                                                                                              } else {
                                                                                                  Log.w(
                                                                                                          TAG,
                                                                                                          "알 수 없는 결과 코드: " + result.getResultCode()
                                                                                                  );
                                                                                              }
                                                                                          } catch (
                                                                                                  Exception e) {
                                                                                              Log.e(
                                                                                                      TAG,
                                                                                                      "ActivityResultLauncher 처리 중 예외 발생: " + e.getMessage(),
                                                                                                      e
                                                                                              );
                                                                                          }
                                                                                      }
    );

    public Task<GoogleSignInAccount> getGoogleSignTask() {
        return task;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
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
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {

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

    String oAuthClientId = "";

    public void setOuthClientId(String clientId) {
        this.oAuthClientId = clientId;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(oAuthClientId).requestEmail().requestProfile().build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        } catch (Exception e) {
            Log.e(TAG, "Google Sign-In 초기화 실패: " + e.getMessage());
        }
    }
    public void googleSignIn() {
        try {
            // 로그인 전에 항상 로그아웃 (계정 선택 화면 강제)
            // FIXME: 2025. 12. 7. 여기서  mGoogleSignInClient
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

    public void kakaoSignIn(Function2<OAuthToken, Throwable, Unit> callback) {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(this)) {
            UserApiClient.getInstance().loginWithKakaoTalk(this, callback);
        } else {
            UserApiClient.getInstance().loginWithKakaoAccount(this, callback);
        }
    }

    private static final String TAG = "LoginActivity";

    public void moveToMainActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void makeToast(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}