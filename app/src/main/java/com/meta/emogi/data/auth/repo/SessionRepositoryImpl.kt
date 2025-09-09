package com.meta.emogi.data.auth.repo

import com.meta.emogi.data.auth.local.SessionLocalDataSource
import com.meta.emogi.domain.auth.entity.LoginState
import com.meta.emogi.domain.auth.repo.SessionRepository
import com.meta.emogi.domain.common.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionRepositoryImpl(private val local: SessionLocalDataSource) : SessionRepository {
    override suspend fun readLoginState(): AppResult<LoginState> = withContext(Dispatchers.IO) {
        try {
            if (local.isLoggedIn()) {
                val token = local.getToken()
                if (!token.isNullOrBlank()) {
                    AppResult.Success(LoginState.LoggedIn)
                } else {
                    AppResult.Success(LoginState.LoggedOut)
                }
            } else {
                AppResult.Success(LoginState.LoggedOut)
            }
        } catch (t: Throwable) {
            AppResult.Failure("세션 읽기 실패", t)
        }
    }
}
