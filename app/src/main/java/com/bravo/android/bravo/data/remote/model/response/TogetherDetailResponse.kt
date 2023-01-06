package com.bravo.android.bravo.data.remote.model.response

data class TogetherDetailResponse(
    val togetherToken : String,
    val profileImg : String?,
    val name : String,
    val year : Int,
    val gender : Int,
    val sido : String,
    val sigungu : String,
    val createdAt : String,
    val title : String,
    val content : String,
    val peopleNum : Int,
    val location : Int,
    val locationDetail : String?,
    val togetherGender : String,
    val ageState : Int,
    val firstAge : Int,
    val secondAge : Int,
    val activityState : Int,
    val date : String,
    val category : String,
    val likeCount : Int,
    val peopleCount : Int,
    val isAuth : Int
)