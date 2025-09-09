package com.meta.emogi.domain.auth.entity

sealed class LoginState {
    data object LoggedIn : LoginState()
    data object LoggedOut : LoginState()
}