package com.meta.emogi.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.meta.emogi.R;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.IdTokenRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String SERVER_URL = "http://122.128.54.136:7070/";
    private ApiService apiService;

    private LoginViewModel mViewModel;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Google Sign-In options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.server_client_id)).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder().baseUrl(SERVER_URL).addConverterFactory(GsonConverterFactory.create()).build();

        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Set the sign-in button click listener
        view.findViewById(R.id.login_button).setOnClickListener(v -> signIn());

        view.findViewById(R.id.network1).setOnClickListener(v -> network_fun1());

        view.findViewById(R.id.network2).setOnClickListener(v -> network_fun2());

        return view;
    }
    private void network_fun2() {}
    private void network_fun1() {}


    // ActivityResultLauncher for sign-in result handling
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
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d(TAG, "login success :" + account.getIdToken());
            sendIdTokenToServer(account.getIdToken());
        } catch (ApiException e) {
            Log.w(TAG, "signInResult: failed code=" + e.getStatusCode(), e);
        }
    }

    public void sendIdTokenToServer(String idToken) {
        IdTokenRequest request = new IdTokenRequest(idToken);

        Call<Void> call = apiService.sendIdToken(request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.e("Error", "Code: " + response.code());
                    return;
                }

                Log.d(TAG, "Token sent successfully.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}