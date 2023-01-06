package com.bravo.android.bravo.data.remote.api

import com.bravo.android.bravo.data.remote.model.DataResponse
import com.bravo.android.bravo.data.remote.model.VoidResponse
import com.bravo.android.bravo.data.remote.model.response.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StoryApi {

    @GET("/story/main")
    fun getStoryListData(
        @Query("category") category : String
    ) : Single<DataResponse<List<StoryListResponse>>>

    @Multipart
    @POST("/story/create")
    @JvmSuppressWildcards
    fun storyUpload(
        @Part("content") content: RequestBody,
        @Part("category") category: RequestBody,
        @Part("uploadImg[]") uploadImg: List<RequestBody>,
        @Part photo: List<MultipartBody.Part>
    ): Single<VoidResponse>

    @GET("/story/{feedToken}/detail")
    fun getDetailHealingData(
        @Path("feedToken") feedToken: String
    ) : Single<DataResponse<StoryDetailResponse>>

    @Multipart
    @POST("/story/comment")
    fun postCommentToHealing(
        @Part("feedToken") feedToken: RequestBody,
        @Part("comment") comment: RequestBody,
        @Part commentImg : MultipartBody.Part?
    ): Single<VoidResponse>

    @GET("/story/{feedToken}/comment")
    fun getStoryCommentData(
        @Path("feedToken") infoToken: String,
    ) : Single<DataResponse<List<StoryCommentListResponse>>>

    @POST("/story/{feedToken}/like")
    fun likeStory(
        @Path("feedToken") feedToken: String
    ) : Single<VoidResponse>

    @HTTP(method = "DELETE", path = "/story/{feedToken}/unlike", hasBody = true)
    fun unlikeStory(
        @Path("feedToken") feedToken: String
    ): Single<VoidResponse>
}