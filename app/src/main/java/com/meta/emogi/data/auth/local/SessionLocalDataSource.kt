package com.meta.emogi.data.auth.local

interface SessionLocalDataSource {
    fun isLoggedIn(): Boolean
    fun getToken(): String?
    fun saveToken(token: String?)
    fun clearSession()
}