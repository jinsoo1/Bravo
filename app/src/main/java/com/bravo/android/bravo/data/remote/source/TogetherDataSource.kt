package com.bravo.android.bravo.data.remote.source

import com.bravo.android.bravo.data.remote.api.TogetherApi
import com.bravo.android.bravo.data.remote.model.VoidResponse
import com.bravo.android.bravo.data.remote.model.response.TogetherDetailResponse
import com.bravo.android.bravo.data.remote.model.response.TogetherUserListResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync

class TogetherDataSource(
    private val togetherApi: TogetherApi
) {

    fun saveTogetherData(
        title : RequestBody,
        content : RequestBody,
        uploadImg: List<RequestBody>,
        peopleNum : RequestBody,
        location : RequestBody,
        regionToken : RequestBody,
        gender : RequestBody,
        ageState : RequestBody,
        firstAge : RequestBody,
        secondAge : RequestBody,
        activityState : RequestBody,
        date : RequestBody,
        category : RequestBody,
        photo: List<MultipartBody.Part>,
        ) : Single<VoidResponse>{
        return togetherApi.saveTogetherData(title, content, uploadImg, peopleNum, location, regionToken, gender, ageState, firstAge, secondAge, activityState, date, category, photo)
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTogetherDetail(
        togetherToken : String
    ) : Single<TogetherDetailResponse>{
        return togetherApi.getDetailHealingData(togetherToken)
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getUserListData(
        togetherToken : String
    ) : Single<List<TogetherUserListResponse>>{
        return togetherApi.getUserListData(togetherToken)
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .observeOn(AndroidSchedulers.mainThread())
    }


}