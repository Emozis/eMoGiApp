package com.meta.emogi.domain.common

sealed class AppResult<out T> {
    data class Success<T>(val value:T):AppResult<T>()
    data class Failure(val message:String?=null, val cause:Throwable?=null):AppResult<Nothing>()
}