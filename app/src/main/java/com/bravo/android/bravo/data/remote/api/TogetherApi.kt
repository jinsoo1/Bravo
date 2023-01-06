package com.bravo.android.bravo.data.remote.api

import com.bravo.android.bravo.data.remote.model.DataResponse
import com.bravo.android.bravo.data.remote.model.VoidResponse
import com.bravo.android.bravo.data.remote.model.response.StoryDetailResponse
import com.bravo.android.bravo.data.remote.model.response.TogetherDetailResponse
import com.bravo.android.bravo.data.remote.model.response.TogetherUserListResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface TogetherApi {

    @Multipart
    @POST("/together/create")
    @JvmSuppressWildcards
    fun saveTogetherData(
        @Part("title") title : RequestBody,
        @Part("content") content : RequestBody,
        @Part("uploadImg[]") uploadImg: List<RequestBody>,
        @Part("peopleNum") peopleNum : RequestBody,
        @Part("location") location : RequestBody,
        @Part("regionToken") regionToken : RequestBody,
        @Part("gender") gender : RequestBody,
        @Part("ageState") ageState : RequestBody,
        @Part("firstAge") firstAge : RequestBody,
        @Part("secondAge") secondAge : RequestBody,
        @Part("activityState") activityState : RequestBody,
        @Part("date") date : RequestBody,
        @Part("category") category : RequestBody,
        @Part photo: List<MultipartBody.Part>
    ): Single<VoidResponse>



    @GET("/together/{togetherToken}/detail")
    fun getDetailHealingData(
        @Path("togetherToken") togetherToken: String
    ) : Single<DataResponse<TogetherDetailResponse>>

    @GET("/together/{togetherToken}/peopleNum")
    fun getUserListData(
        @Path("togetherToken") togetherToken: String
    ) : Single<DataResponse<List<TogetherUserListResponse>>>
}