package com.bravo.android.bravo.data.remote.api

import com.bravo.android.bravo.data.remote.model.DataResponse
import com.bravo.android.bravo.data.remote.model.VoidResponse
import com.bravo.android.bravo.data.remote.model.response.UserResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @GET("/auth/validate-access-token")
    fun validateAccessToken(): Single<VoidResponse>


    @POST("/auth/login/kakao")
    @FormUrlEncoded
    fun loginByKaKao(
        @Field("userToken") userToken: String,
    ): Single<DataResponse<UserResponse>>

    @POST("/auth/login")
    @FormUrlEncoded
    fun login(
        @Field("userToken")userToken : String,
        @Field("email")email : String,
        @Field("type")type : String,
        @Field("name")name : String,
        @Field("region")region : Int,
        @Field("gender")gender : Int,
        @Field("year")year : Int
    ): Single<DataResponse<UserResponse>>

    @POST("/auth/compare")
    @FormUrlEncoded
    fun compareVersion(
        @Field("version") version : Double
    ) : Single<VoidResponse>
}