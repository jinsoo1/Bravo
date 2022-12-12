package com.bravo.android.bravo.data.remote.model.response

import com.bravo.android.bravo.data.common.model.User

data class UserResponse(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)
