package com.meta.emogi.domain.auth.usecase

import com.meta.emogi.domain.auth.repo.SessionRepository
import com.meta.emogi.domain.common.AppResult
import com.meta.emogi.domain.common.SimpleUseCase

class LogoutUseCase(private val repo: SessionRepository) : SimpleUseCase<Unit>() {
    override suspend fun execute(): AppResult<Unit> = repo.clearSession()
}