package com.meta.emogi.views.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meta.emogi.domain.auth.usecase.IsLoggedInUseCase
import com.meta.emogi.domain.common.AppResult
import kotlinx.coroutines.launch

class SplashViewModel(private val isLoggedInUseCase: IsLoggedInUseCase) : ViewModel(){
    private val _goMain = MutableLiveData<Boolean>()
    val goMain: LiveData<Boolean> = _goMain

    fun decide(){
        viewModelScope.launch{
            when(val r = isLoggedInUseCase()){
                is AppResult.Success -> _goMain.value = r.value
                is AppResult.Failure -> _goMain.value = false
            }

        }
    }
}