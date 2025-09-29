package com.meta.emogi.feature.sync.domain.usecase

import com.meta.emogi.feature.sync.domain.repo.ISessionRepository
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.SimpleUseCase
import javax.inject.Inject

class CheckLoggedInUseCase @Inject constructor(
    private val repo: ISessionRepository
) : SimpleUseCase<Boolean>(){
    override suspend fun execute(): AppResult<Boolean> = repo.checkLoginState()
}