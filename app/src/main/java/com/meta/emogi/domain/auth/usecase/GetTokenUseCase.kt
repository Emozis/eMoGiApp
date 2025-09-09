package com.meta.emogi.domain.auth.usecase

import com.meta.emogi.domain.auth.repo.SessionRepository
import com.meta.emogi.domain.common.AppResult
import com.meta.emogi.domain.common.SimpleUseCase

class GetTokenUseCase(private val repo: SessionRepository) : SimpleUseCase<String?>() {
    override suspend fun execute(): AppResult<String?> = repo.getToken()
}