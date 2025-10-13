package com.meta.emogi.feature.login.data

import com.meta.emogi.data.network.api.ApiCallBack
import com.meta.emogi.data.network.model.LoginRequest
import com.meta.emogi.data.network.model.LoginResponse
import com.meta.emogi.data.repository.ApiRepository
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.RetryNeededException
import com.meta.emogi.feature.login.domain.repo.IAuthRepository
import com.meta.emogi.feature.sync.domain.repo.ISessionRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRepository @Inject constructor(
    private val apiRepository: ApiRepository,
    private val sessionRepo: ISessionRepository
) :IAuthRepository{


    override suspend fun createAccessTokenGoogle(accessToken: String): AppResult<LoginResponse> =
        suspendCancellableCoroutine { cont ->
            val request = LoginRequest(accessToken)

            apiRepository.createAccessTokenGoogle(request,  object : ApiCallBack.ApiResultHandler<LoginResponse>{
                override fun onSuccess(data: LoginResponse) {
                    if (cont.isActive) cont.resume(AppResult.Success(data))
                }

                override fun onFailed(t: Throwable) {
                    if (cont.isActive) cont.resume(AppResult.Failure("google 로그인으로 토큰 발급 실패", t))
                }

                override fun onRetry() {
                    if (cont.isActive) cont.resumeWithException(RetryNeededException())
                }
            })
        }

    override suspend fun createAccessTokenKakao(accessToken: String): AppResult<LoginResponse> =
        suspendCancellableCoroutine { cont ->
            val request = LoginRequest(accessToken)

            apiRepository.createAccessTokenKakao(request,  object : ApiCallBack.ApiResultHandler<LoginResponse>{
                override fun onSuccess(data: LoginResponse) {
                    if (cont.isActive) cont.resume(AppResult.Success(data))
                }

                override fun onFailed(t: Throwable) {
                    if (cont.isActive) cont.resume(AppResult.Failure("kakao 로그인으로 토큰 발급 실패", t))
                }

                override fun onRetry() {
                    if (cont.isActive) cont.resumeWithException(RetryNeededException())
                }
            })
        }


    override suspend fun saveSessionToken(token: String): AppResult<Unit> = sessionRepo.saveServerAccessToken(token)
}
