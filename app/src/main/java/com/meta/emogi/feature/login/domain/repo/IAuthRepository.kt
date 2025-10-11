package com.meta.emogi.feature.login.domain.repo

import com.meta.emogi.data.network.model.TokenModel
import com.meta.emogi.feature.base.domain.AppResult

interface IAuthRepository {
    suspend fun createAccessToken(idToken:String): AppResult<TokenModel>
    suspend fun saveSessionToken(token: String): AppResult<Unit>
}