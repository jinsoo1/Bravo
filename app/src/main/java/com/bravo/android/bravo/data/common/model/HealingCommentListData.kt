package com.bravo.android.bravo.data.common.model

data class HealingCommentListData(
    val infoToken : Int,
    val comment : String,
    val photo : String?,
    val userToken : String,
    val profileImg : String?,
    val name : String,
    val year : String,
    val gender : Boolean,
    val sido : String,
    val sigungu : String,
    val createdAt : String,
    val isBlocked : Boolean
)
