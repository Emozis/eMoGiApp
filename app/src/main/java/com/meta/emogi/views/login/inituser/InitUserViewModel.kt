package com.meta.emogi.views.login.inituser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.meta.emogi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InitUserViewModel @Inject constructor() : BaseViewModel() {

    val userName = MutableLiveData<String>("")
    val birthYear = MutableLiveData<String>("")
    val birthMonth = MutableLiveData<String>("")
    val birthDay = MutableLiveData<String>("")
    
    // true: Male, false: Female, null: Not selected
    private val _isMale = MutableLiveData<Boolean?>(null)
    val isMale: LiveData<Boolean?> get() = _isMale

    val isFormValid = MediatorLiveData<Boolean>().apply {
        addSource(userName) { validateForm() }
        addSource(birthYear) { validateForm() }
        addSource(birthMonth) { validateForm() }
        addSource(birthDay) { validateForm() }
        addSource(_isMale) { validateForm() }
    }

    fun setGender(isMale: Boolean) {
        _isMale.value = isMale
    }

    private fun validateForm() {
        val name = userName.value ?: ""
        val year = birthYear.value ?: ""
        val month = birthMonth.value ?: ""
        val day = birthDay.value ?: ""
        val gender = _isMale.value // null 체크

        val isValid = name.isNotBlank() && 
                      year.length == 4 && 
                      month.isNotBlank() && 
                      day.isNotBlank() && 
                      gender != null
        
        isFormValid.value = isValid
    }

    private val _navigateToMain = com.meta.emogi.base.SingleLiveEvent<Boolean>()
    val navigateToMain: LiveData<Boolean> get() = _navigateToMain

    fun onCompleteClick() {
        android.util.Log.d("InitUserViewModel", "onCompleteClick: Complete button clicked")
        // TODO: 서버로 데이터 전송 로직이 있다면 여기에 추가
        _navigateToMain.setValue(true)
    }
}