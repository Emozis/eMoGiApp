package com.meta.emogi.feature.sync.domain.usecase

import com.meta.emogi.feature.sync.domain.repo.ISessionRepository
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.SimpleUseCase
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val repo: ISessionRepository) : SimpleUseCase<String?>() {
    override suspend fun execute(): AppResult<String?> = repo.getToken()
}