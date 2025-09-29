package com.meta.emogi.feature.sync.domain.repo

import com.meta.emogi.feature.base.domain.AppResult

interface IAppUpdateChecker {
    suspend fun isMandatory(): AppResult<Boolean>
}