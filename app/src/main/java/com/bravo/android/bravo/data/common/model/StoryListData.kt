package com.bravo.android.bravo.data.common.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt

class StoryListData(
    val token : String,
    val profileImg : String?,
    val name : String,
    val year : Int,
    val gender : Int,
    val sido : String,
    val sigungu : String,
    val createdAt : String,
    var content : String,
    val Photo : List<String>?,
    val LikeCount : Int,
    val Count2 : Int,
    val Count3 : Int,
    isLiked : Boolean,
    val state : Boolean,
    val title : String?,
    val peopleNum : Int?,
    val ageState : Boolean?,
    val firstAge : Int?,
    val secondAge : Int?,
    val togetherGender : String?,
    val activityState : Boolean?,
    val date : String?,
    val category : String?
){
    val isLike: ObservableBoolean = ObservableBoolean(isLiked)
    val isLikeEnabled: ObservableBoolean = ObservableBoolean(true)
    val isLikeCount : ObservableInt = ObservableInt(LikeCount)
}