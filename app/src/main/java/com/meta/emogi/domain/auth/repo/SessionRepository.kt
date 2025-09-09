package com.meta.emogi.domain.auth.repo

import com.meta.emogi.domain.common.AppResult

interface SessionRepository {
    suspend fun checkLoginState(): AppResult<Boolean>
    suspend fun getToken(): AppResult<String?>
    suspend fun saveToken(token:String?): AppResult<Unit>
    suspend fun clearSession(): AppResult<Unit>

}