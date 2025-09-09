package com.meta.emogi.domain.auth.usecase

import com.meta.emogi.domain.auth.entity.StartDestination
import com.meta.emogi.domain.common.AppResult
import com.meta.emogi.domain.common.SimpleUseCase
import javax.inject.Inject

class DecideStartUIUseCase @Inject constructor(private val getToken: GetTokenUseCase, private val checkLoggedInUseCase: CheckLoggedInUseCase) : SimpleUseCase<StartDestination>() {
    override suspend fun execute(): AppResult<StartDestination> = try {
        when (val tokenResult = getToken()) {
            is AppResult.Success -> {
                val token = tokenResult.value
                if (token.isNullOrBlank()) {
                    AppResult.Success(StartDestination.Go2Login)
                } else {
                    when (val loginResult = checkLoggedInUseCase()) {
                        is AppResult.Success -> {
                            AppResult.Success(if (loginResult.value) StartDestination.Go2Main else StartDestination.Go2Login)
                        }

                        is AppResult.Failure -> AppResult.Success(StartDestination.Go2Login)
                    }
                }
            }

            is AppResult.Failure -> AppResult.Success(StartDestination.Go2Login)
        }
    } catch (e: Exception) {
        AppResult.Failure("DecideStartUIUseCase Error", e)
    }
}