package com.meta.emogi.views.login.loginapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.meta.emogi.BuildConfig
import com.meta.emogi.EmogiApp
import com.meta.emogi.R
import com.meta.emogi.base.BaseFragment
import com.meta.emogi.data.network.model.LoginResponse
import com.meta.emogi.databinding.FragmentLoginApiBinding
import com.meta.emogi.views.login.LoginActivity
import com.meta.emogi.views.toolbar.ToolbarView
import com.meta.emogi.views.toolbar.ToolbarView.ToolbarRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginApiFragment : BaseFragment<FragmentLoginApiBinding, LoginApiViewModel>() {

    companion object {
        private const val TAG = "LoginApiFragment"
    }

    private var mGoogleSignInClient: GoogleSignInClient? = null

    private val appContext by lazy { EmogiApp.getInstance().appContext }
    private val activity by lazy { requireActivity() as LoginActivity }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                viewModel.getAccessToken(appContext, account)
            } else {
                Log.e(TAG, "account가 null")
            }
        } catch (e: ApiException) {
            Log.e(TAG, "=== 구글 로그인 실패 ===")
            activity.makeToast("구글 로그인에 실패했습니다.")
        } catch (e: Exception) {
            Log.e(TAG, "일반 Exception: " + e.message, e)
            activity.makeToast("구글 로그인 중 오류가 발생했습니다.")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        // Hilt로 viewModel 주입
        viewModel = viewModels<LoginApiViewModel>().value
        super.onCreate(savedInstanceState)
        activity.setOuthClientId(viewModel.oauthClientId)

        viewModel.setAppVersion(BuildConfig.VERSION_NAME)

        viewModel.accessToken.observe(this) { accessToken: LoginResponse? ->
            if (accessToken != null && accessToken.data != null && accessToken.data.accessToken != null) {
                activity.moveToMainActivity()
            } else {
                Log.e(TAG, "Login response, data, or access token is null")
                activity.makeToast("로그인에 실패했습니다.")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAppKeyHash(appContext)
        val task = activity.getGoogleSignTask()
        if (task != null) {
            handleSignInResult(task)
        }
        binding.googleLoginButton.setOnClickListener { v -> activity.googleSignIn() }
        binding.kakaoLoginButton.setOnClickListener { v -> kakaoSignIn() }
    }

    private fun kakaoSignIn() { // 카카오 로그인 콜백
        val callback: Function2<OAuthToken, Throwable, Unit> = { token: OAuthToken?, error: Throwable? ->
            if (error != null) {
                activity.makeToast("카카오 로그인에 실패하였습니다.")
            } else if (token != null) {
                Log.d("www", "kakaoSignIn: $token")
                viewModel.handleKakaoAccessToken(token.accessToken)
            }
        }
        activity.kakaoSignIn(callback)
    }


    override fun toolbarCallback(): ToolbarView.ToolbarRequest {
        return ToolbarRequest("로그인 페이지")
    }

    override fun layoutId(): Int {
        return R.layout.fragment_login_api
    }

    override fun viewModelClass(): Class<LoginApiViewModel> {
        return LoginApiViewModel::class.java
    }

    override fun registerObservers() {

    }

}