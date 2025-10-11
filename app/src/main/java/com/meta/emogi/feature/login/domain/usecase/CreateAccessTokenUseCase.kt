package com.meta.emogi.feature.login.domain.usecase

import com.meta.emogi.data.network.model.TokenModel
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.SimpleUseCase
import com.meta.emogi.feature.base.domain.UseCase
import com.meta.emogi.feature.login.domain.repo.IAuthRepository
import javax.inject.Inject

class CreateAccessTokenUseCase @Inject constructor(private val repo: IAuthRepository) : UseCase<String, TokenModel>() {
    override suspend fun execute(idToken: String): AppResult<TokenModel> {
        val result = repo.createAccessToken(idToken)
        if (result is AppResult.Success) {
            repo.saveSessionToken(result.value.accessToken ?: "")
        }
        return result
    }
}