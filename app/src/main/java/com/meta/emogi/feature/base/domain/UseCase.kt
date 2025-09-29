package com.meta.emogi.feature.base.domain

abstract class UseCase<in P, R> {
    suspend operator fun invoke(params: P): AppResult<R> {
        return execute(params)
    }
    protected abstract suspend fun execute(params: P): AppResult<R>
}

abstract class SimpleUseCase<R> {
    suspend operator fun invoke(): AppResult<R> {
        return execute()
    }
    protected abstract suspend fun execute(): AppResult<R>
}