package com.bravo.android.bravo.data.remote.source

import com.bravo.android.bravo.data.remote.api.HealingApi
import com.bravo.android.bravo.data.remote.model.VoidResponse
import com.bravo.android.bravo.data.remote.model.response.HealingCommentListResponse
import com.bravo.android.bravo.data.remote.model.response.HealingDetailResponse
import com.bravo.android.bravo.data.remote.model.response.HealingListResponse
import com.bravo.android.bravo.data.remote.model.response.HomeHealingResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class HealingDataSource(
    private val healingApi: HealingApi
) {

    fun getHomeHealingData() : Single<List<HomeHealingResponse>>{
        return healingApi.getHomeHealingData()
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDetailHealingData(
        infoToken : String
    ) : Single<HealingDetailResponse>{
        return healingApi.getDetailHealingData(infoToken)
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getHealingListData(
        category : String
    ) : Single<List<HealingListResponse>>{
        return healingApi.getHomeHealingData(category)
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun postCommentToHealing(
        infoToken: RequestBody,
        comment : RequestBody,
        commentImg : MultipartBody.Part?
    ) : Single<VoidResponse>{
        return healingApi.postCommentToHealing(infoToken, comment, commentImg)
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getHealingCommentData(
        infoToken: String
    ) : Single<List<HealingCommentListResponse>>{
        return healingApi.getHealingCommentData(infoToken)
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun likeHealingInfo(
        infoToken: String
    ) : Single<VoidResponse>{
        return healingApi.likeHealingInfo(infoToken)
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun unlikeHealingInfo(
        infoToken: String
    ) : Single<VoidResponse>{
        return healingApi.unlikeHealingInfo(infoToken)
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }
}