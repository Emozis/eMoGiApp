package com.meta.emogi.feature.login.domain.usecase

import com.meta.emogi.data.network.model.LoginResponse
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.UseCase
import com.meta.emogi.feature.login.domain.repo.IAuthRepository
import javax.inject.Inject

class CreateAccessTokenkakaoUseCase @Inject constructor(private val repo: IAuthRepository) : UseCase<String, LoginResponse>() {
    override suspend fun execute(idToken: String): AppResult<LoginResponse> {
        val result = repo.createAccessTokenKakao(idToken)
        if (result is AppResult.Success) {
            repo.saveSessionToken(result.value.data.accessToken ?: "")
        }
        return result
    }
}