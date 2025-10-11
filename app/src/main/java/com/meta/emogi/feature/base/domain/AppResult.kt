package com.meta.emogi.feature.base.domain

sealed class AppResult<out T> {
    data class Success<T>(val value:T): AppResult<T>()
    data class Failure(val message:String?=null, val cause:Throwable?=null, val data:Any?=null): AppResult<Nothing>()
}