package com.bravo.android.bravo.data.remote.model.response

data class HealingCommentListResponse(
    val infoToken : Int,
    val comment : String,
    val photo : String?,
    val userToken : String,
    val profileImg : String?,
    val name : String,
    val year : Int,
    val gender : Int,
    val sido : String,
    val sigungu : String,
    val createdAt : String,
    val isBlocked : Int
)