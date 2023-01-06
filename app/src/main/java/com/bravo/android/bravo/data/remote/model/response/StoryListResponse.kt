package com.bravo.android.bravo.data.remote.model.response

data class StoryListResponse(
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
    private val isLiked: Int,
    val state : Int,
    val title : String?,
    val peopleNum : Int?,
    val ageState : Int?,
    val firstAge : Int?,
    val secondAge : Int?,
    val togetherGender : String?,
    val activityState : Int?,
    val date : String?,
    val category : String?
){
    fun isLiked(): Boolean {
        return isLiked == 1
    }
}
