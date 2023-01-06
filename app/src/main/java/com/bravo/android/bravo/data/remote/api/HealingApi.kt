package com.bravo.android.bravo.data.remote.api

import com.bravo.android.bravo.data.remote.model.DataResponse
import com.bravo.android.bravo.data.remote.model.VoidResponse
import com.bravo.android.bravo.data.remote.model.response.HealingCommentListResponse
import com.bravo.android.bravo.data.remote.model.response.HealingDetailResponse
import com.bravo.android.bravo.data.remote.model.response.HealingListResponse
import com.bravo.android.bravo.data.remote.model.response.HomeHealingResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface HealingApi {

    @GET("/healing/home")
    fun getHomeHealingData() : Single<DataResponse<List<HomeHealingResponse>>>

    @GET("/healing/{infoToken}/detail")
    fun getDetailHealingData(
        @Path("infoToken") infoToken: String
    ) : Single<DataResponse<HealingDetailResponse>>

    @GET("/healing/healing")
    fun getHomeHealingData(
        @Query("category") category : String
    ) : Single<DataResponse<List<HealingListResponse>>>

    @Multipart
    @POST("/healing/comment")
    fun postCommentToHealing(
        @Part("infoToken") infoToken: RequestBody,
        @Part("comment") comment: RequestBody,
        @Part commentImg : MultipartBody.Part?
    ): Single<VoidResponse>

    @GET("/healing/{infoToken}/comment")
    fun getHealingCommentData(
        @Path("infoToken") infoToken: String,
    ) : Single<DataResponse<List<HealingCommentListResponse>>>

    @POST("/healing/{infoToken}/like")
    fun likeHealingInfo(
        @Path("infoToken") infoToken: String
    ) : Single<VoidResponse>

    @HTTP(method = "DELETE", path = "/healing/{infoToken}/unlike", hasBody = true)
    fun unlikeHealingInfo(
        @Path("infoToken") infoToken: String
    ): Single<VoidResponse>

}