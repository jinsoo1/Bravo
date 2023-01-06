package com.bravo.android.bravo.data.common.model

data class User(
    val userToken: String,
    val email: String,
    val name: String,
    val userType: String,
    val profileImg : String?,
    val regionToken : Int,
    val gender : Int,
    val year : Int
)