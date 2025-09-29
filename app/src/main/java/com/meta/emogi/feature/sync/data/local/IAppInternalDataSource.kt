package com.meta.emogi.feature.sync.data.local

interface IAppInternalDataSource {
    fun isLoggedIn(): Boolean
    fun getToken(): String?
    fun saveToken(token: String?)
    fun clearSession()
}