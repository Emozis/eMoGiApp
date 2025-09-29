package com.meta.emogi.feature.sync.domain.usecase

import com.meta.emogi.feature.sync.domain.repo.ISessionRepository
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.SimpleUseCase
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repo: ISessionRepository) : SimpleUseCase<Unit>() {
    override suspend fun execute(): AppResult<Unit> = repo.clearSession()
}