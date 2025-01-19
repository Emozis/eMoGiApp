package com.meta.emogi.views.login;

import static com.meta.emogi.MyApplication.getDeviceHeightPx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.meta.emogi.MyApplication;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentLoginBinding;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.TokenModel;
import com.meta.emogi.util.ConfigUtil;
import com.meta.emogi.views.toolbar.ToolbarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {

    private static final String TAG = "LoginFragment";
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
            activity.setAccessToken(accessedToken);
            activity.moveActivity();
            Log.d("www", "Login 성공 / 토큰 : " + activity.getAccessToken());
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.server_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
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

    private ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == getActivity().RESULT_OK) {
            Intent data = result.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    });

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        signInLauncher.launch(signInIntent);
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            Log.d("www", "앱 실행체크");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("www", "Google Sign-In 성공: " + account.getIdToken());
            TokenModel requestToken = new TokenModel(account.getIdToken());
            viewModel.createAccessToken(requestToken);
        } catch (ApiException e) {
            Log.e("Www", "Google Sign-In 실패: 상태 코드 = " + e.getStatusCode(), e);
        }
    }

}