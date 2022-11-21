package com.bravo.android.bravo.data.common.model

data class User(
    val userType: String,
    val userToken: String,
    val name: String,
    val email: String,
    val agree : Int

)