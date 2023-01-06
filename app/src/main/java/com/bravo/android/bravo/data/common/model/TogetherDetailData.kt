package com.bravo.android.bravo.data.common.model

data class TogetherDetailData(
    val togetherToken : String,
    val profileImg : String?,
    val name : String,
    val year : String,
    val gender : Boolean,
    val sido : String,
    val sigungu : String,
    val createdAt : String,
    val title : String,
    val content : String,
    val peopleNum : Int,
    val location : Boolean,
    val locationDetail : String?,
    val togetherGender : String,
    val ageState : Boolean,
    val firstAge : Int,
    val secondAge : Int,
    val activityState : Boolean,
    val date : String,
    val category : String,
    val likeCount : Int,
    val peopleCount : Int,
    val isAuth : Boolean
)
