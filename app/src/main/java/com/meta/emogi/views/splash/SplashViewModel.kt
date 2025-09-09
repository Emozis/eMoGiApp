package com.meta.emogi.views.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meta.emogi.domain.auth.entity.StartDestination
import com.meta.emogi.domain.auth.entity.StartDestination.*
import com.meta.emogi.domain.auth.usecase.DecideStartUIUseCase
import com.meta.emogi.domain.common.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val decideStartUIUseCase: DecideStartUIUseCase) : ViewModel() {

    private val _destination = MutableLiveData<StartDestination>()
    val destination: LiveData<StartDestination> = _destination

    companion object {
        private const val TAG = "SplashViewModel"
    }

    fun decideNext() {
        viewModelScope.launch {
            when (val result = decideStartUIUseCase()) {
                is AppResult.Success -> {
                    _destination.value = result.value
                }

                is AppResult.Failure -> {
                    Log.e(TAG, "Error deciding start UI: ${result.message}: ${result.cause}")
                    _destination.value = Go2Login
                }
            }
        }
    }

    //    fun setToken(token: String?) {
    //        viewModelScope.launch {
    //            when (val result = saveTokenUseCase(token)) {
    //                is AppResult.Success -> {
    //                    Log.d(TAG, "Token saved successfully")
    //                }
    //                is AppResult.Failure -> {
    //                    Log.d(TAG, "Error saving token: ${result.message}")
    //                }
    //            })
    //        }
    //    }
    //
    //    fun logOut(){
    //        viewModelScope.launch {
    //            when (val r = logoutUseCase()) {
    //                is AppResult.Success -> { /* 화면 이동/상태 초기화 */ }
    //                is AppResult.Failure -> { /* 에러 표시 */ }
    //            }
    //        }
    //    }


}