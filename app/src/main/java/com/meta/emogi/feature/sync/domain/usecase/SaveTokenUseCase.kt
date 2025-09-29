package com.meta.emogi.feature.sync.domain.usecase

import com.meta.emogi.feature.sync.domain.repo.ISessionRepository
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.UseCase
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val repo: ISessionRepository) : UseCase<String?, Unit>() {
    override suspend fun execute(params:String?): AppResult<Unit> = repo.saveToken(params)
}