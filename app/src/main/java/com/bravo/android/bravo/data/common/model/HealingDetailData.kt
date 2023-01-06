package com.bravo.android.bravo.data.common.model

import androidx.databinding.ObservableBoolean
import com.bravo.android.bravo.data.remote.model.response.AfterHi
import com.bravo.android.bravo.data.remote.model.response.BeforeHi

class HealingDetailData(
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
    isLiked : Boolean
){
    val isLike: ObservableBoolean = ObservableBoolean(isLiked)
    val isLikeEnabled: ObservableBoolean = ObservableBoolean(true)
}
