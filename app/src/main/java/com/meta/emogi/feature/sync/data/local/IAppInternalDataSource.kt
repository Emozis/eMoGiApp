package com.meta.emogi.feature.sync.data.local

interface IAppInternalDataSource {
    fun isLoggedIn(): Boolean
    fun getServerAccessToken(): String?
    fun saveServerAccessToken(token: String?)
    fun getServerRefreshToken(): String?
    fun saveServerRefreshToken(token: String?)
    fun clearSession()
}