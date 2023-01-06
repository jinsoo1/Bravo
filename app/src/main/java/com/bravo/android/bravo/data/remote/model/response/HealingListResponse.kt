package com.bravo.android.bravo.data.remote.model.response

data class HealingListResponse(
    val infoToken : String,
    val img : String,
    val title : String,
    val comment : Int,
    val shareCount : Int,
    val likes : Int,
    private val isLiked: Int
){
    fun isLiked(): Boolean {
        return isLiked == 1
    }
}