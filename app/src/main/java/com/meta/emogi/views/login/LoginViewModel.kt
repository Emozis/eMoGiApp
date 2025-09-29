package com.meta.emogi.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.meta.emogi.base.BaseViewModel
import com.meta.emogi.data.network.api.ApiCallBack
import com.meta.emogi.data.network.model.TokenModel
import com.meta.emogi.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(@Named("appVersion") appVersion:String,
//    private val apiRepository: ApiRepository
) : BaseViewModel() {


    companion object{
        private const val TAG = "LoginViewModel"
    }
    private val _accessToken = MutableLiveData<TokenModel>()
    private val _appVersion = MutableLiveData<String>()


    val accessToken: LiveData<TokenModel> = _accessToken
    val appVersion: LiveData<String> = _appVersion

    fun setAccessToken(token: TokenModel){
        _accessToken.value = token
    }

    fun setAppVersion(version: String){
        _appVersion.value = version
    }

//    fun start(){
//        viewModelScope.launch {
//            when(val result = )
//        }
//    }


    fun createAccessToken(requestToken: TokenModel){
        loading()
        _accessToken.value = requestToken

    // FIXME: apirepo 인터페이스로 변경 250910

//        apiRepository.createAccessToken(requestToken,  object : ApiCallBack.ApiResultHandler<TokenModel>{
//            override fun onSuccess(result: TokenModel) {
//                loadingSuccess()
//                _accessToken.value = result
//            }
//
//            override fun onFailed(t: Throwable) {
//                loadingFailed("로그인 작업")
//            }
//
//            override fun onRetry() {
//                loadingRetry()
//            }
//
//        })
    }

}