package com.bravo.android.bravo.data.remote.model.response

data class StoryDetailResponse(
    val profileImg : String?,
    val name : String,
    val year : Int,
    val sido : String,
    val sigungu : String,
    val createdAt : String,
    val content : String,
    val feedToken : String,
    val likeCount : Int,
    val commentCount : Int,
    val shareCount : Int,
    val beforeS : BeforeS,
    val afterS : AfterS,
    private val isLiked: Int
){
    fun isLiked(): Boolean {
        return isLiked == 1
    }
}

data class BeforeS(
    val feedToken : String?,
    val content : String?
)

data class AfterS(
    val feedToken : String?,
    val content : String?
)
