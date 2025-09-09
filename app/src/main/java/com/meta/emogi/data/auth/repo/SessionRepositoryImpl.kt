package com.meta.emogi.data.auth.repo

import com.meta.emogi.data.auth.local.SessionLocalDataSource
import com.meta.emogi.domain.auth.repo.SessionRepository
import com.meta.emogi.domain.common.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionRepositoryImpl(private val local: SessionLocalDataSource) : SessionRepository {
    override suspend fun checkLoginState(): AppResult<Boolean> =
        try {
                AppResult.Success(local.isLoggedIn())
            } catch (t: Throwable) {
                AppResult.Failure("세션 읽기 실패", t)
            }


    override suspend fun getToken(): AppResult<String?> =
        try{
            AppResult.Success(local.getToken())
        }catch (t: Throwable){
           AppResult.Failure("토큰 읽기 실패", t)
        }


    override suspend fun saveToken(token: String?): AppResult<Unit> =
        try {
            local.saveToken(token)
            AppResult.Success(Unit)
        } catch (t: Throwable) {
            AppResult.Failure("토큰 저장 실패", t)
        }

    override suspend fun clearSession(): AppResult<Unit> =
        try {
            local.clearSession()
            AppResult.Success(Unit)
        } catch (t: Throwable) {
            AppResult.Failure("세션 종료 실패", t)
        }
}
