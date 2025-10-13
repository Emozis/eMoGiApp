package com.meta.emogi.feature.login.domain.repo

import com.meta.emogi.data.network.model.LoginResponse
import com.meta.emogi.feature.base.domain.AppResult

interface IAuthRepository {
    suspend fun createAccessTokenGoogle(idToken:String): AppResult<LoginResponse>
    suspend fun createAccessTokenKakao(idToken:String): AppResult<LoginResponse>
    suspend fun saveSessionToken(token: String): AppResult<Unit>
}