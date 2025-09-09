package com.meta.emogi.views.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meta.emogi.domain.auth.usecase.CheckLoggedInUseCase
import com.meta.emogi.domain.auth.usecase.GetTokenUseCase
import com.meta.emogi.domain.common.AppResult
import kotlinx.coroutines.launch

class SplashViewModel(private val checkLoggedInUseCase: CheckLoggedInUseCase, private val getTokenUseCase: GetTokenUseCase) : ViewModel() {
    private val _goMain = MutableLiveData<Boolean>()
    val goMain: LiveData<Boolean> = _goMain

    companion object {
        private const val TAG = "SplashViewModel"
    }

    fun start() {
        viewModelScope.launch {
            decideNext()
        }
    }

    suspend fun checkLoggedIn() {
        when (val result = checkLoggedInUseCase()) {
            is AppResult.Success -> {
                if (result.value) {
                    _goMain.value = true
                } else {
                    Log.w(TAG, "Warm isLoggedIn: Not logged in")
                    _goMain.value = false
                }
            }
            is AppResult.Failure -> {
                Log.e(TAG, "Error isLoggedIn: ${result.message}")
                _goMain.value = false
            }
        }
    }

    suspend fun decideNext() {
        when (val result = getTokenUseCase()) {
            is AppResult.Success -> {
                val token = result.value
                if (!token.isNullOrBlank()) {
                    checkLoggedIn()
                }else{
                    _goMain.value = false
                }
            }

            is AppResult.Failure -> {
                Log.e(TAG, "Error getToken: ${result.message}")
                _goMain.value = false
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