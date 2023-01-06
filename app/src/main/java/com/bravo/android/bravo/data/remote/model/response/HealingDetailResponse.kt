package com.bravo.android.bravo.data.remote.model.response

data class HealingDetailResponse(
    val infoToken : String,
    val title : String,
    val content : String,
    val img : String,
    val category : String,
    val createdAt : String,
    val beforeHi: BeforeHi,
    val afterHi: AfterHi,
    val comment : Int,
    val shareCount : Int,
    val likes : Int,
    private val isLiked: Int
){
    fun isLiked(): Boolean {
        return isLiked == 1
    }
}

data class BeforeHi(
    val infoToken : String?,
    val title : String?
)

data class AfterHi(
    val infoToken : String?,
    val title : String?
)
