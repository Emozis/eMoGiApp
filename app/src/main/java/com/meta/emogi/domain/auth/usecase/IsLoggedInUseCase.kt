package com.meta.emogi.domain.auth.usecase

import com.meta.emogi.domain.auth.entity.LoginState
import com.meta.emogi.domain.auth.repo.SessionRepository
import com.meta.emogi.domain.common.AppResult
import com.meta.emogi.domain.common.SimpleUseCase

class IsLoggedInUseCase(
    private val repo: SessionRepository
) : SimpleUseCase<Boolean>(){
    override suspend fun execute(): AppResult<Boolean> {
        when(val r = repo.readLoginState()){
            is AppResult.Success -> {
                return AppResult.Success(r.value is LoginState.LoggedIn)
            }
            is AppResult.Failure -> {
                return AppResult.Failure(r.message, r.cause)
            }
        }
    }
}