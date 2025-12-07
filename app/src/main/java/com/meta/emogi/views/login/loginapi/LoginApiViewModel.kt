package com.meta.emogi.views.login.loginapi

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.kakao.sdk.common.util.Utility
import com.meta.emogi.base.BaseViewModel
import com.meta.emogi.data.network.model.LoginResponse
import com.meta.emogi.data.repository.ApiRepository
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.RetryNeededException
import com.meta.emogi.feature.login.domain.usecase.CreateAccessTokenGoogleUseCase
import com.meta.emogi.feature.login.domain.usecase.CreateAccessTokenkakaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginApiViewModel @Inject constructor(@Named("oauthClientId") val oauthClientId: String, private val createAccessTokenGoogle: CreateAccessTokenGoogleUseCase, private val createAccessTokenKakao: CreateAccessTokenkakaoUseCase, private val apiRepository: ApiRepository) : BaseViewModel() {

    companion object {
        private const val TAG = "LoginApiViewModel"
    }

    private val _accessToken = MutableLiveData<LoginResponse>()
    private val _appVersion = MutableLiveData<String>()


    val accessToken: LiveData<LoginResponse> = _accessToken
    val appVersion: LiveData<String> = _appVersion

    fun setAppVersion(version: String) {
        _appVersion.value = version
    }


    fun handleGoogleIdToken(accessToken: String) {
        loading()
        viewModelScope.launch {
            try {
                when (val result = createAccessTokenGoogle(accessToken)) {
                    is AppResult.Success -> {
                        _accessToken.value = result.value
                        loadingSuccess()
                    }

                    is AppResult.Failure -> {
                        loadingFailed("로그인 처리 중 오류가 발생했습니다.")
                    }
                }
            } catch (e: RetryNeededException) {
                loadingRetry()
            }
        }
    }


    fun getAppKeyHash(context: Context) {
        val keyHash = Utility.getKeyHash(context)
        Log.d("www", "keyhash 값: " + keyHash)
    }

    fun handleKakaoAccessToken(accessToken: String?) {
        loading()

        if (accessToken == null) {
            loadingFailed("카카오 로그인 처리 중 오류(null)가 발생했습니다.")
            return
        }

        viewModelScope.launch {
            try {
                Log.d("www", "handleKakaoAccessToken: 토큰: $accessToken")
                when (val result = createAccessTokenKakao(accessToken)) {
                    is AppResult.Success -> {
                        _accessToken.value = result.value
                        loadingSuccess()
                    }

                    is AppResult.Failure -> {
                        loadingFailed("로그인 처리 중 오류가 발생했습니다.")
                    }
                }
            } catch (e: RetryNeededException) {
                loadingRetry()
            }
        }
    }

    fun getAccessToken(context: Context, account: GoogleSignInAccount): Boolean {
        var isSuccess = false
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val scope = "oauth2:profile email"
                val accessToken = GoogleAuthUtil.getToken(context, account.account!!, scope)

                Log.d("ACCESS_TOKEN_CHECK", "Google ACCESS Token: $accessToken")

                handleGoogleIdToken(accessToken) // 이름은 그대로 두지만 실제로는 Access Token
                isSuccess = true
            } catch (e: GoogleAuthException) {
                Log.e(TAG, "Google Auth Exception: ${e.message}", e)
            } catch (e: Exception) {
                Log.e(TAG, "구글 인증 실패 - Exception: ${e.javaClass.simpleName}, Message: ${e.message}", e)
            }
        }
        return isSuccess
    }

}