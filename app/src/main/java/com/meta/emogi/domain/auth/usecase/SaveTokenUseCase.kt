package com.meta.emogi.domain.auth.usecase

import com.meta.emogi.domain.auth.repo.SessionRepository
import com.meta.emogi.domain.common.AppResult
import com.meta.emogi.domain.common.UseCase

class SaveTokenUseCase(private val repo: SessionRepository) : UseCase<String?, Unit>() {
    override suspend fun execute(params:String?): AppResult<Unit> = repo.saveToken(params)
}