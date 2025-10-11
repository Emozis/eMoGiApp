package com.meta.emogi.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.meta.emogi.base.BaseViewModel
import com.meta.emogi.data.network.api.ApiCallBack
import com.meta.emogi.data.network.model.TokenModel
import com.meta.emogi.data.repository.ApiRepository
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.RetryNeededException
import com.meta.emogi.feature.login.domain.usecase.CreateAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    @Named("appVersion") appVersion:String,
    @Named("oauthClientId") val oauthClientId:String,
    private val createAccessToken: CreateAccessTokenUseCase,
    private val apiRepository: ApiRepository
) : BaseViewModel() {


    companion object{
        private const val TAG = "LoginViewModel"
    }
    private val _accessToken = MutableLiveData<TokenModel>()
    private val _appVersion = MutableLiveData<String>()


    val accessToken: LiveData<TokenModel> = _accessToken
    val appVersion: LiveData<String> = _appVersion

    fun setAccessToken(token: TokenModel){
        _accessToken.value = token
    }

    fun setAppVersion(version: String){
        _appVersion.value = version
    }


    fun handleGoogleIdToken(idToken: String) {
        loading() // 로딩 상태를 먼저 표시합니다.

        viewModelScope.launch {
            try {
                // createAccessToken은 AppResult를 반환하거나, RetryNeededException을 던질 수 있습니다.
                when (val result = createAccessToken(idToken)) {
                    is AppResult.Success -> {
                        // 호출이 성공하면 LiveData를 업데이트하고 성공 상태로 변경합니다.
                        _accessToken.value = result.value
                        loadingSuccess()
                    }
                    is AppResult.Failure -> {
                        // 호출이 실패하면 실패 상태로 변경합니다.
                        loadingFailed("로그인 처리 중 오류가 발생했습니다.")
                    }
                }
            } catch (e: RetryNeededException) {
                // 재시도 예외가 발생한 경우 재시도 UI를 표시합니다.
                loadingRetry()
            }
        }
    }

}