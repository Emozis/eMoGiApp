package com.meta.emogi.domain.auth.usecase

import com.meta.emogi.domain.auth.repo.SessionRepository
import com.meta.emogi.domain.common.AppResult
import com.meta.emogi.domain.common.SimpleUseCase

class CheckLoggedInUseCase(
    private val repo: SessionRepository
) : SimpleUseCase<Boolean>(){
    override suspend fun execute(): AppResult<Boolean> = repo.checkLoginState()
}