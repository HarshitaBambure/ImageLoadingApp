package com.example.imageloadingapp.data.api

sealed class BaseResponse<out T> {
    data class Success<out T>(val data: T? = null) : BaseResponse<T>()
    data class Loading(val nothing: Nothing?=null) : BaseResponse<Nothing>()
    data class Failed(val msg: String?) : BaseResponse<Nothing>()
}