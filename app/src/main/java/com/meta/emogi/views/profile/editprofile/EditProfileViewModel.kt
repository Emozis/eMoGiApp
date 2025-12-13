package com.meta.emogi.views.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.meta.emogi.base.BaseViewModel
import com.meta.emogi.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
) : BaseViewModel() {

    private val _currentTab = MutableLiveData<Int>(0) // 0: Social, 1: Chat
    val currentTab: LiveData<Int> = _currentTab

    fun setTab(tabIndex: Int) {
        _currentTab.value = tabIndex
    }
}
