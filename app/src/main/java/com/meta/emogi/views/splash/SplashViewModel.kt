package com.meta.emogi.views.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meta.emogi.feature.sync.domain.entity.StartRoute
import com.meta.emogi.feature.sync.domain.usecase.DecideStartUIUseCase
import com.meta.emogi.feature.base.domain.AppResult
import com.meta.emogi.feature.sync.domain.usecase.CheckMandatoryUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val decideStartUIUseCase: DecideStartUIUseCase,
    private val checkMandatoryUpdateUseCase: CheckMandatoryUpdateUseCase) : ViewModel() {

    private val _destination = MutableLiveData<StartRoute>()
    val destination: LiveData<StartRoute> = _destination

    companion object {
        private const val TAG = "SplashViewModel"
    }

    fun decideNext() {
        viewModelScope.launch {

            val mustUpdate = runCatching { checkMandatoryUpdateUseCase() }
                .getOrElse {
                    AppResult.Success(true)
                }

            if(mustUpdate is AppResult.Success){
                _destination.value = StartRoute.UPDATE
                return@launch
            }

            when (val result = decideStartUIUseCase()) {
                is AppResult.Success -> {
                    _destination.value = result.value
                }

                is AppResult.Failure -> {
                    Log.e(TAG, "Error deciding start UI: ${result.message}: ${result.cause}")
                    _destination.value = StartRoute.LOGIN
                }
                else -> {
                    _destination.value = StartRoute.LOGIN
                }
            }
        }
    }
}