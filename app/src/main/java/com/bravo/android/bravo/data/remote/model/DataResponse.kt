package com.bravo.android.bravo.data.remote.model

data class DataResponse<T>(
    val data: T,
    val success: Boolean = false,
    val message: String = ""
)