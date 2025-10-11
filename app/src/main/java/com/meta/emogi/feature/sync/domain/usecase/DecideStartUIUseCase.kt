package com.meta.emogi.feature.sync.domain.usecase

import com.meta.emogi.feature.sync.domain.entity.StartRoute
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.SimpleUseCase
import javax.inject.Inject

class DecideStartUIUseCase @Inject constructor(private val getToken: GetTokenUseCase, private val checkLoggedInUseCase: CheckLoggedInUseCase) : SimpleUseCase<StartRoute>() {
    override suspend fun execute(): AppResult<StartRoute> = try {
        when (val tokenResult = getToken()) {
            is AppResult.Success -> {
                val token = tokenResult.value
                if (token.isNullOrBlank()) {
                    AppResult.Success(StartRoute.LOGIN)
                } else {
                    when (val loginResult = checkLoggedInUseCase()) {
                        is AppResult.Success -> {
                            AppResult.Success(if (loginResult.value) StartRoute.HOME else StartRoute.LOGIN)
                        }

                        else -> AppResult.Success(StartRoute.LOGIN)
                    }
                }
            }
            else -> AppResult.Success(StartRoute.LOGIN)
        }
    } catch (e: Exception) {
        AppResult.Failure("DecideStartUIUseCase Error", e)
    }
}