package com.bravo.android.bravo.data.common.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.bravo.android.bravo.data.remote.model.response.AfterS
import com.bravo.android.bravo.data.remote.model.response.BeforeS

class StoryDetailData(
    val profileImg : String?,
    val name : String,
    val year : String,
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
    isLiked : Boolean
){
    val isLike: ObservableBoolean = ObservableBoolean(isLiked)
    val isLikeEnabled: ObservableBoolean = ObservableBoolean(true)
    val isLikeCount : ObservableInt = ObservableInt(likeCount)
}
