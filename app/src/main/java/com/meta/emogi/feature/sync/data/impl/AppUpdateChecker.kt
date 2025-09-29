package com.meta.emogi.feature.sync.data.impl

import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.sync.domain.repo.IAppUpdateChecker
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AppUpdateChecker @Inject constructor(private val context: Context) : IAppUpdateChecker {
    override suspend fun isMandatory(): AppResult<Boolean> = suspendCancellableCoroutine { cont ->
        val appUpdateManager = AppUpdateManagerFactory.create(context)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                val mandatory = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                cont.resume(AppResult.Success(mandatory))
            }.addOnFailureListener {
                cont.resume(AppResult.Failure("앱 업데이트 체크 에러"))
            }
    }
}