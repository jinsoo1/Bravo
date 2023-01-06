package com.bravo.android.bravo.data.common.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt

class HealingListData(
    val infoToken : String,
    val img : String,
    val title : String,
    val comment : Int,
    val shareCount : Int,
    var likes : Int,
    isLiked : Boolean
){
    val isLike: ObservableBoolean = ObservableBoolean(isLiked)
    val isLikeEnabled: ObservableBoolean = ObservableBoolean(true)
    val isLikeCount : ObservableInt = ObservableInt(likes)
}
