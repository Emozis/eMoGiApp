package com.meta.emogi.feature.sync.domain.repo

import com.meta.emogi.feature.base.domain.AppResult

interface ISessionRepository {
    suspend fun checkLoginState(): AppResult<Boolean>
    suspend fun getServerAccessToken(): AppResult<String?>
    suspend fun saveServerAccessToken(token:String?): AppResult<Unit>
    suspend fun clearSession(): AppResult<Unit>

}