package com.example.htmlwords.utils

import kotlin.Exception

sealed class DataState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : DataState<T>(data)
    class NoData<T>(data: T) : DataState<T>(data)
    class Loading<T> : DataState<T>()
    class Error<T>() : DataState<T>()
}