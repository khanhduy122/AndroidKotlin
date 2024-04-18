package com.khanhduy.movieappandroid.repository

sealed class AppResponse<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T?) : AppResponse<T>(data)
    class Error<T>(message: String?) : AppResponse<T>(message = message)
}