package com.meta.emogi.feature.sync.domain.usecase

import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.base.domain.SimpleUseCase
import com.meta.emogi.feature.sync.domain.repo.IAppUpdateChecker
import javax.inject.Inject

class CheckMandatoryUpdateUseCase @Inject constructor(private val checker: IAppUpdateChecker):SimpleUseCase<Boolean>() {
    override suspend fun execute(): AppResult<Boolean> = checker.isMandatory()
}