package com.meta.emogi.feature.sync.domain.repo

import com.meta.emogi.feature.base.domain.AppResult

interface ISessionRepository {
    suspend fun checkLoginState(): AppResult<Boolean>
    suspend fun getToken(): AppResult<String?>
    suspend fun saveToken(token:String?): AppResult<Unit>
    suspend fun clearSession(): AppResult<Unit>

}