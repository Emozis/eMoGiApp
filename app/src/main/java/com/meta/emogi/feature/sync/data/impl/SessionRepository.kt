package com.meta.emogi.feature.sync.data.impl

import com.meta.emogi.feature.sync.data.local.IAppInternalDataSource
import com.meta.emogi.feature.sync.domain.repo.ISessionRepository
import com.meta.emogi.feature.base.domain.AppResult
import javax.inject.Inject

class SessionRepository @Inject constructor(private val local: IAppInternalDataSource) :
    ISessionRepository {
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
