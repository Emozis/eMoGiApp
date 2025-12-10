package com.meta.emogi.views.login.serviceterms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.meta.emogi.base.BaseViewModel
import com.meta.emogi.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServiceTermsViewModel @Inject constructor() : BaseViewModel() {

    val isAllAgreed = MutableLiveData(false)
    val isTerm1Agreed = MutableLiveData(false)
    val isTerm2Agreed = MutableLiveData(false)
    val isTerm3Agreed = MutableLiveData(false)

    private val _isMandatoryAgreed = MutableLiveData(false)
    val isMandatoryAgreed: LiveData<Boolean> get() = _isMandatoryAgreed

    private val _navigateToInitUser = SingleLiveEvent<Unit>()
    val navigateToInitUser: LiveData<Unit> get() = _navigateToInitUser

    fun onAllAgreeChecked(checked: Boolean) {
        isTerm1Agreed.value = checked
        isTerm2Agreed.value = checked
        isTerm3Agreed.value = checked
        checkMandatory()
    }

    fun checkMandatory() {
        val term1 = isTerm1Agreed.value ?: false
        val term2 = isTerm2Agreed.value ?: false
        _isMandatoryAgreed.value = term1 && term2
        
        // 전체 동의 체크박스 상태 동기화 (선택사항 포함 모든 약관이 동의되었을 때만 true)
        val term3 = isTerm3Agreed.value ?: false
        if (term1 && term2 && term3) {
             if (isAllAgreed.value != true) isAllAgreed.value = true
        } else {
             if (isAllAgreed.value != false) isAllAgreed.value = false
        }
    }

    fun onAgreeClick() {
        if (isMandatoryAgreed.value == true) {
            _navigateToInitUser.call()
        }
    }
}