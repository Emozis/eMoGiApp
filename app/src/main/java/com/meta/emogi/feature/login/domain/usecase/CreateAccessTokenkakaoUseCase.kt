package com.meta.emogi.feature.login.domain.usecase

import android.util.Log
import com.meta.emogi.data.network.model.LoginResponse
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.UseCase
import com.meta.emogi.feature.login.domain.repo.IAuthRepository
import javax.inject.Inject

class CreateAccessTokenkakaoUseCase @Inject constructor(private val repo: IAuthRepository) : UseCase<String, LoginResponse>() {
    override suspend fun execute(idToken: String): AppResult<LoginResponse> {
        val result = repo.createAccessTokenKakao(idToken)
        Log.d("www", "카카오 로그인 결과:++ $result")
        if (result is AppResult.Success) {
            val token = result.value.data.accessToken ?: ""
            repo.saveSessionToken(token)
            Log.d("www", "토큰저장 성공" + token)
        }else{
            Log.d("www", "토큰저장 실패")
        }
        return result
    }
}