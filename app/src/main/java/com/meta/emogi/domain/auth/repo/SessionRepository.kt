package com.meta.emogi.domain.auth.repo

import com.meta.emogi.domain.auth.entity.LoginState
import com.meta.emogi.domain.common.AppResult

interface SessionRepository {
    suspend fun readLoginState(): AppResult<LoginState>
}