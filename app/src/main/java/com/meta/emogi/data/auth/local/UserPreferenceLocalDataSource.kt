package com.meta.emogi.data.auth.local

import android.content.Context
import android.content.SharedPreferences

class UserPreferenceLocalDataSource(private val context: Context) : SessionLocalDataSource {

    companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val PREF_NAME = "user_prefs"
        const val KEY_USER_TOKEN = "user_token"
    }

    var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

    override fun isLoggedIn(): Boolean = pref.getBoolean(KEY_IS_LOGGED_IN, false);

    override fun getToken(): String? = pref.getString(KEY_USER_TOKEN, null);

    override fun setToken(token: String?) {
        val editor = pref.edit()
        if (token.isNullOrBlank()) {
            editor.clear()
            clearSession()
        } else {
            editor.putBoolean(KEY_IS_LOGGED_IN, true)
            editor.putString(KEY_USER_TOKEN, token)
        }
        editor.apply()
    }

    override fun clearSession() {
        pref.edit().clear().apply()
    }

}